package com.sarahehabm.newsapp.list.presenter;

import com.sarahehabm.newsapp.list.model.Article;

import java.util.ArrayList;

public interface ListPresenterListener {
    void displayList(ArrayList<Article> articles);

    void displayEmpty();

    void displayError();

    void displayLoader();

    void hideLoader();

    void hideAllViews();
}
