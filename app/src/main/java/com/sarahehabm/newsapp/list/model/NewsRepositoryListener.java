package com.sarahehabm.newsapp.list.model;

public interface NewsRepositoryListener {
    void onSuccess(String source, News news);

    void onFailure();
}
