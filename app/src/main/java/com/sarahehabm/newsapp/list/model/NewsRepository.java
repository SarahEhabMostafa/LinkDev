package com.sarahehabm.newsapp.list.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.sarahehabm.newsapp.APIs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsRepository {
    public static final String SOURCE_1 = "the-next-web";
    public static final String SOURCE_2 = "associated-press";

    private NewsRepositoryListener listener;

    public NewsRepository(@NonNull NewsRepositoryListener listener) {
        this.listener = listener;
    }

    public void getItems(String source) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(APIs.BASE_URL).build();

        APIs apis = retrofit.create(APIs.class);
        Call<News> call = apis.listRepos(source, APIs.API_KEY);
        Callback<News> callback = new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call, Response<News> response) {
                Log.d("RETROFIT", "onResponse");

                if(!response.isSuccessful() || response.body() == null) {
                    listener.onFailure();
                } else {
                    listener.onSuccess(response.body().getSource(), response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {
                Log.d("RETROFIT", "onFailure");

                listener.onFailure();
            }
        };

        call.enqueue(callback);
    }
}
