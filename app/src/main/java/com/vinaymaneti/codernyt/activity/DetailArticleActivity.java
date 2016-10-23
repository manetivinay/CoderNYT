package com.vinaymaneti.codernyt.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.vinaymaneti.codernyt.R;
import com.vinaymaneti.codernyt.model.Article;
import com.vinaymaneti.codernyt.model.Multimedia;
import com.vinaymaneti.codernyt.util.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailArticleActivity extends AppCompatActivity {

    Article article;

    @BindView(R.id.coverImageView)
    AppCompatImageView coverImageView;

    @BindView(R.id.textSnippet)
    AppCompatTextView textSnippet;

    @BindView(R.id.pbloading)
    ProgressBar progressBarLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            article = bundle.getParcelable("article");
            if (article.getMultimedia().size() > 0 && article.getMultimedia() != null) {
                Multimedia multimedia = article.getMultimedia().get(0);
                if (multimedia.getHeight() != null) {
                    ViewGroup.LayoutParams layoutParams = coverImageView.getLayoutParams();
                    layoutParams.height = (int) UiUtils.convertDpTOPixel(multimedia.getHeight(), this);
                    coverImageView.setLayoutParams(layoutParams);
                }
                coverImageView.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(multimedia.getUrl())
                        .into(coverImageView);
            } else {
                coverImageView.setVisibility(View.GONE);
                progressBarLoading.setVisibility(View.GONE);
            }

            textSnippet.setText(article.getSnippet());
        }
    }
}
