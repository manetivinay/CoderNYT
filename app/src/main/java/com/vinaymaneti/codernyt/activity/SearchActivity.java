package com.vinaymaneti.codernyt.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
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
    private String order = null, beginDate = null, endDate = null;
    private boolean hasArts = false;
    private boolean hasFashionAndStyle = false;
    private boolean hasSports = false;

    @Override
    public void onFilterComplete(final String order, final boolean hasArts, final boolean hasFashionAndStyle, final boolean hasSports, final String beginDate, String endDate) {
        this.order = order;
        this.hasArts = hasArts;
        this.hasFashionAndStyle = hasFashionAndStyle;
        this.hasSports = hasSports;
        this.beginDate = beginDate;
        this.endDate = endDate;
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
        mArticleAdapter = new ArticleAdapter();
        mArticleAdapter.setMoreListener(new ArticleAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore(boolean hasMore) {
                if (hasMore)
                    searchMore();
                else
                    pbLoadMore.setVisibility(View.GONE);
            }
        });
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(mArticleAdapter);
    }

    private void setUpApi() {
        mSearchRequest = new SearchRequest();
        mArticleApi = RetrofitUtils.get().create(ArticleApi.class);
    }

    private void search() {
        mSearchRequest.resetPage();
        progressBarLoading.setVisibility(View.VISIBLE);
        fetchArticles(new Listener() {
            @Override
            public void onResult(SearchResult searchResult) {
                mArticleAdapter.setArticles(searchResult.getArticles());
                recyclerView.scrollToPosition(0);
            }
        });
    }

    private void searchMore() {
        mSearchRequest.nextPage();
        pbLoadMore.setVisibility(View.VISIBLE);
        fetchArticles(new Listener() {
            @Override
            public void onResult(SearchResult searchResult) {
                mArticleAdapter.addArticles(searchResult.getArticles());
            }
        });
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
