package com.sarahehabm.newsapp.list.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sarahehabm.newsapp.R;
import com.sarahehabm.newsapp.list.model.Article;
import com.sarahehabm.newsapp.list.presenter.ListPresenter;
import com.sarahehabm.newsapp.list.presenter.ListPresenterListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListFragment extends Fragment implements ListPresenterListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.textView_empty)
    TextView textView_empty;

    @BindView(R.id.button_retry)
    Button button_retry;

    private OnListFragmentInteractionListener listener;
    private ListPresenter presenter;

    public ListFragment() {
    }

    @SuppressWarnings("unused")
    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new ListPresenter(this);
        presenter.getItems();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new NewsListAdapter(null, listener));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            listener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void displayList(ArrayList<Article> articles) {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(new NewsListAdapter(articles, listener));
    }

    @Override
    public void displayEmpty() {
        recyclerView.setVisibility(View.GONE);
        textView_empty.setVisibility(View.VISIBLE);
        textView_empty.setText(R.string.empty);
    }

    @Override
    public void displayError() {
        displayEmpty();
        textView_empty.setText(R.string.error);
        button_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayLoader() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void hideAllViews() {
        recyclerView.setVisibility(View.GONE);
        textView_empty.setVisibility(View.GONE);
        button_retry.setVisibility(View.GONE);
    }

    @OnClick(R.id.button_retry)
    public void onRetryClick() {
        presenter.getItems();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Article article);
    }
}
