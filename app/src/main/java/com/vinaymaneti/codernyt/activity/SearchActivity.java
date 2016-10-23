package com.vinaymaneti.codernyt.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.vinaymaneti.codernyt.R;
import com.vinaymaneti.codernyt.adapter.ArticleAdapter;
import com.vinaymaneti.codernyt.api.ArticleApi;
import com.vinaymaneti.codernyt.fragment.FilterArticleFragment;
import com.vinaymaneti.codernyt.model.SearchRequest;
import com.vinaymaneti.codernyt.model.SearchResult;
import com.vinaymaneti.codernyt.util.EndlessRecyclerViewScrollListener;
import com.vinaymaneti.codernyt.util.NetworkUtils;
import com.vinaymaneti.codernyt.util.RetrofitUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements FilterArticleFragment.OnFilterSearchListener {
    private static final String TAG = SearchActivity.class.getSimpleName();
    private SearchRequest mSearchRequest;
    private ArticleApi mArticleApi;
    private ArticleAdapter mArticleAdapter;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBarLoading)
    RelativeLayout progressBarLoading;

    @BindView(R.id.pbLoadMore)
    ProgressBar pbLoadMore;

    private SearchView mSearchView;

    @Override
    public void onFilterComplete(final String order, final boolean hasArts, final boolean hasFashionAndStyle, final boolean hasSports, final String beginDate, String endDate) {
        String order1 = order;
        boolean hasArts1 = hasArts;
        boolean hasFashionAndStyle1 = hasFashionAndStyle;
        boolean hasSports1 = hasSports;
        String beginDate1 = beginDate;
        String endDate1 = endDate;
        mSearchRequest.setOrder(order);
        mSearchRequest.setHasArt(hasArts);
        mSearchRequest.setHasFashionAndStyle(hasFashionAndStyle);
        mSearchRequest.setHasSports(hasSports);
        mSearchRequest.setBeginDate(beginDate.replace("-", "").trim());
        fetchArticles(new Listener() {
            @Override
            public void onResult(SearchResult searchResult) {
                mArticleAdapter.setArticles(searchResult.getArticles());
                recyclerView.scrollToPosition(0);
            }
        });

    }

    private interface Listener {
        void onResult(SearchResult searchResult);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setUpApi();
        setUpView();
        search();
    }

    private void setUpView() {
        mArticleAdapter = new ArticleAdapter(this);
        mArticleAdapter.setMoreListener(new ArticleAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore(boolean hasMore) {
                if (hasMore) {
                    if (NetworkUtils.isNetworkAvailable(SearchActivity.this)) {
                        searchMore();
                    } else {
                        callNetworkAlertDialog();
                    }
                } else
                    pbLoadMore.setVisibility(View.GONE);
            }
        });

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
//        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setAdapter(mArticleAdapter);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

//        EndlessRecyclerView(staggeredGridLayoutManager);

    }

    private void EndlessRecyclerView(StaggeredGridLayoutManager staggeredGridLayoutManager) {
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fetchArticles(new Listener() {
                    @Override
                    public void onResult(SearchResult searchResult) {
                        mArticleAdapter.addArticles(searchResult.getArticles());
                    }
                });
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }

    private void setUpApi() {
        mSearchRequest = new SearchRequest();
        mArticleApi = RetrofitUtils.get().create(ArticleApi.class);
    }

    private void search() {
        mSearchRequest.resetPage();
        progressBarLoading.setVisibility(View.VISIBLE);
        if (NetworkUtils.isNetworkAvailable(SearchActivity.this)) {
            fetchArticles(new Listener() {
                @Override
                public void onResult(SearchResult searchResult) {
                    mArticleAdapter.setArticles(searchResult.getArticles());
                    recyclerView.scrollToPosition(0);
                }
            });
        } else {
            callNetworkAlertDialog();
        }
    }

    private void searchMore() {
        mSearchRequest.nextPage();
        pbLoadMore.setVisibility(View.VISIBLE);
        if (NetworkUtils.isNetworkAvailable(SearchActivity.this)) {
            fetchArticles(new Listener() {
                @Override
                public void onResult(SearchResult searchResult) {
                    mArticleAdapter.addArticles(searchResult.getArticles());
                }
            });
        } else {
            callNetworkAlertDialog();
        }
    }

    private void callNetworkAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You are offline please check your internet connection");
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dialogIntent);
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void fetchArticles(final Listener listener) {
        mArticleApi.search(mSearchRequest.toQueryMap()).enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (response.body() != null)
                    listener.onResult(response.body());
                handleComplete();
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Log.d("Error Response:-", t.getMessage());
            }
        });
    }

    private void handleComplete() {
        progressBarLoading.setVisibility(View.GONE);
        pbLoadMore.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mSearchRequest.setQuery(null);
                return true;
            }
        });
        setUpSearchView(menuItem);
        return super.onCreateOptionsMenu(menu);
    }

    private void setUpSearchView(MenuItem menuItem) {
        mSearchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.clearFocus();
                mSearchRequest.setQuery(query);
                search();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                showFilterDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFilterDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FilterArticleFragment filterArticleFragment = new FilterArticleFragment();
        filterArticleFragment.show(fragmentManager, "filter_article");
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
