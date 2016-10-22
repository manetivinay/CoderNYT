package com.vinaymaneti.codernyt.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vinay on 20/10/16.
 */

public class SearchResult {
    @SerializedName("docs")
    List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    //docs ---> I rename as Aricle
}
