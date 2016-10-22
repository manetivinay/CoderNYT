package com.vinaymaneti.codernyt.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.vinaymaneti.codernyt.R;
import com.vinaymaneti.codernyt.model.Article;
import com.vinaymaneti.codernyt.model.Multimedia;
import com.vinaymaneti.codernyt.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vinay on 22/10/16.
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TEXT_IMAGE_TYPE = 0;
    public static final int NO_IMAGE_TYPE = 1;

    private List<Article> mArticles;
    private LoadMoreListener mMoreListener;

    public interface LoadMoreListener {
        void onLoadMore(boolean hasMore);
    }

    public ArticleAdapter() {
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
            bindNoImage(article, (NoImageViewHolder) holder);
        } else {
            bindTextImage(article, (TextImageViewHolder) holder);
        }
        if (mMoreListener != null && position == mArticles.size() - 1) {
            if (mArticles != null)
                mMoreListener.onLoadMore(true);
            else
                mMoreListener.onLoadMore(false);
        }

    }

    private void bindTextImage(Article article, TextImageViewHolder holder) {
        Multimedia multimedia = article.getMultimedia().get(0);
        ViewGroup.LayoutParams layoutParams = holder.coverImageView.getLayoutParams();
        layoutParams.height = (int) UiUtils.convertDpTOPixel(multimedia.getHeight(), holder.itemView.getContext());
        holder.coverImageView.setLayoutParams(layoutParams);
        Glide.with(holder.itemView.getContext())
                .load(multimedia.getUrl())
                .into(holder.coverImageView);
        holder.textSnippet.setText(article.getSnippet());
    }

    private void bindNoImage(Article article, NoImageViewHolder holder) {
        holder.onlyTextSnippet.setText(article.getSnippet());
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

        public TextImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
