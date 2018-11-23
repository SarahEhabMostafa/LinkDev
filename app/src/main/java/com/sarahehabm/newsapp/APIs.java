package com.sarahehabm.newsapp;

import com.sarahehabm.newsapp.list.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIs {
    public static final String BASE_URL = "https://newsapi.org/";
    public static final String API_KEY = "533af958594143758318137469b41ba9";

    //https://newsapi.org/v1/articles?source=the-next-web&apiKey=533af958594143758318137469b41ba9
    //https://newsapi.org/v1/articles?source=associated-press&apiKey=533af958594143758318137469b41ba9

    @GET("v1/articles")
    Call<News> listRepos(@Query("source") String source, @Query("apiKey") String apiKey);
}
