package com.vinaymaneti.codernyt.adapter;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.vinaymaneti.codernyt.R;
import com.vinaymaneti.codernyt.activity.SearchActivity;
import com.vinaymaneti.codernyt.model.Article;
import com.vinaymaneti.codernyt.model.Multimedia;
import com.vinaymaneti.codernyt.util.NetworkUtils;
import com.vinaymaneti.codernyt.util.UiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vinay on 22/10/16.
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TEXT_IMAGE_TYPE = 0;
    public static final int NO_IMAGE_TYPE = 1;
    private SearchActivity mContext = null;

    private List<Article> mArticles;
    private LoadMoreListener mMoreListener;
    private int requestCode = 100;


    public interface LoadMoreListener {
        void onLoadMore(boolean hasMore);
    }

    public ArticleAdapter(SearchActivity searchActivity) {
        this.mContext = searchActivity;
        mArticles = new ArrayList<>();
    }

    public void setMoreListener(LoadMoreListener moreListener) {
        mMoreListener = moreListener;
    }

    public void setArticles(List<Article> articles) {
        mArticles.clear();
        mArticles.addAll(articles);
        notifyDataSetChanged();
    }

    public void addArticles(List<Article> articles) {
        int startPosition = mArticles.size();
        mArticles.addAll(articles);
        notifyItemRangeInserted(startPosition, articles.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case TEXT_IMAGE_TYPE:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_article, parent, false);
                return new TextImageViewHolder(itemView);
            default:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_article_no_image, parent, false);
                return new NoImageViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Article article = mArticles.get(position);
        if (holder instanceof NoImageViewHolder) {
            int randomAndroidColor = getRandomAndroidColor();
            bindNoImage(article, (NoImageViewHolder) holder, randomAndroidColor);
        } else {
            bindTextImage(article, (TextImageViewHolder) holder);
        }
        if (mMoreListener != null && position == mArticles.size() - 1) {
            if (NetworkUtils.isNetworkAvailable(mContext)) {
                if (mArticles != null)
                    mMoreListener.onLoadMore(true);
                else
                    mMoreListener.onLoadMore(false);
            } else {
                showAlertDialog();
            }
        }

    }

    private void showAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You are offline please check your internet connection");
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(dialogIntent);
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mContext.finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private int getRandomAndroidColor() {
        int[] androidColors = mContext.getResources().getIntArray(R.array.androidcolors);
        return androidColors[new Random().nextInt(androidColors.length)];
    }

    private void bindTextImage(final Article article, final TextImageViewHolder holder) {
        Multimedia multimedia = article.getMultimedia().get(0);
        ViewGroup.LayoutParams layoutParams = holder.coverImageView.getLayoutParams();
        layoutParams.height = (int) UiUtils.convertDpTOPixel(multimedia.getHeight(), holder.itemView.getContext());
        holder.coverImageView.setLayoutParams(layoutParams);
        Glide.with(holder.itemView.getContext())
                .load(multimedia.getUrl())
                .into(holder.coverImageView);
        holder.textSnippet.setText(article.getSnippet());
        holder.shareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareArticle(article);
            }
        });
        holder.exploreIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exploreArticleInWebView(article);
            }
        });
//        holder.frameLayout.setOnClickListener(this);
    }

    private void shareArticle(Article article) {
        String webUrl = article.getWebUrl();
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "A message from CoderNYT");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, webUrl);
        mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void exploreArticleInWebView(Article article) {
        String webUrl = article.getWebUrl();
        if (webUrl != null) {
            // Use a CustomTabsIntent.Builder to configure CustomTabsIntent
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            //set toolbar color
            builder.setToolbarColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            //add share action to menu list
            builder.addDefaultShareMenuItem();
            //share article
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, webUrl + "--> CodeNYT App");
            //calling pendingIntent
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_share);
            Bitmap bitmap = drawableToBitmap(drawable);
            builder.setActionButton(bitmap, "Share Link", pendingIntent, true);
            // set toolbar color and/or setting custom actions before invoking build()
            // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
            CustomTabsIntent customTabsIntent = builder.build();
            //and launch the desired url with CustomTabsIntent.launchUrl()
            customTabsIntent.launchUrl(mContext, Uri.parse(webUrl));
        }
    }

    private void bindNoImage(final Article article, NoImageViewHolder holder, int randomAndroidColor) {
        holder.onlyTextSnippet.setBackgroundColor(randomAndroidColor);
        holder.onlyTextSnippet.setText(article.getSnippet());
        holder.shareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareArticle(article);
            }
        });
        holder.exploreIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exploreArticleInWebView(article);
            }
        });
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    @Override
    public int getItemViewType(int position) {
        Article article = mArticles.get(position);
        return article.getMultimedia() != null && !article.getMultimedia().isEmpty() ? TEXT_IMAGE_TYPE : NO_IMAGE_TYPE;
    }

    public static class NoImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.onlyTextSnippet)
        AppCompatTextView onlyTextSnippet;

        @BindView(R.id.share)
        AppCompatImageView shareIv;

        @BindView(R.id.explore)
        AppCompatImageView exploreIv;


        public NoImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class TextImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.coverImageView)
        AppCompatImageView coverImageView;

        @BindView(R.id.textSnippet)
        AppCompatTextView textSnippet;

        @BindView(R.id.share)
        AppCompatImageView shareIv;

        @BindView(R.id.explore)
        AppCompatImageView exploreIv;

        @BindView(R.id.frameLayout)
        FrameLayout frameLayout;

        public TextImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
