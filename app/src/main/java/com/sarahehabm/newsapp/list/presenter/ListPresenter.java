package com.sarahehabm.newsapp.list.presenter;

import com.sarahehabm.newsapp.list.model.Article;
import com.sarahehabm.newsapp.list.model.News;
import com.sarahehabm.newsapp.list.model.NewsRepository;
import com.sarahehabm.newsapp.list.model.NewsRepositoryListener;

import java.util.ArrayList;

public class ListPresenter implements NewsRepositoryListener {
    private ListPresenterListener listPresenterListener;
    private NewsRepository repository;
    private ArrayList<Article> articles;

    public ListPresenter(ListPresenterListener listPresenterListener) {
        this.listPresenterListener = listPresenterListener;
        repository = new NewsRepository(this);
    }

    public void getItems() {
        listPresenterListener.hideAllViews();
        listPresenterListener.displayLoader();
        repository.getItems(NewsRepository.SOURCE_1);
    }

    private void addArticlesToList(ArrayList<Article> newArticles) {
        if(newArticles == null)
            return;

        if(articles == null) {
            articles = new ArrayList<>();
        }

        articles.addAll(newArticles);
    }

    @Override
    public void onSuccess(String source, News news) {
        switch (source) {
            case NewsRepository.SOURCE_1:
                addArticlesToList(news.getArticles());
                repository.getItems(NewsRepository.SOURCE_2);
                break;

            case NewsRepository.SOURCE_2:
                addArticlesToList(news.getArticles());
                listPresenterListener.hideLoader();
                if(articles == null || articles.isEmpty()) {
                    listPresenterListener.displayEmpty();
                } else {
                    listPresenterListener.displayList(articles);
                }
                break;
        }
    }

    @Override
    public void onFailure() {
        listPresenterListener.hideLoader();
        listPresenterListener.displayError();
    }
}
