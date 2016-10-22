package com.vinaymaneti.codernyt.api;

import com.vinaymaneti.codernyt.model.SearchResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by vinay on 21/10/16.
 */

public interface ArticleApi {
    @GET("articlesearch.json")
    Call<SearchResult> search(@QueryMap(encoded = true) Map<String, String> options);
    // here we use  -- > @QueryMap
    //?begin_date=20160112&
    // sort=oldest&
    // fq=news_desk:("Education"%20"Health")&
    // api-key=227c750bb7714fc39ef1559ef1bd8329

    //what ever the parameter we pass it will be added like above

//    Map<String,String> map = new HashMap<>();
//        map.put("sort","oldest");
//        map.put("page", "1");
//        map.put("begin_date","20160112")

    //And here it will be arranged as a url

}
