package com.sarahehabm.newsapp.list.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sarahehabm.newsapp.R;
import com.sarahehabm.newsapp.Utilities;
import com.sarahehabm.newsapp.list.model.Article;
import com.sarahehabm.newsapp.list.ui.ListFragment.OnListFragmentInteractionListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private final List<Article> articles;
    private final OnListFragmentInteractionListener listener;

    NewsListAdapter(List<Article> items, @NonNull OnListFragmentInteractionListener listener) {
        articles = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Article article = articles.get(position);

        holder.article = article;

        Glide.with(holder.root)
                .load(article.getUrlToImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder))
                .into(holder.imageView);

        holder.textView_title.setText(article.getTitle());

        Context context = holder.textView_author.getContext();
        String author = article.getAuthor() == null || article.getAuthor().isEmpty()?
                context.getString(R.string.n_a) : article.getAuthor(),
                by_author = context.getString(R.string.by_author, author);
        holder.textView_author.setText(by_author);

        String formattedDate = Utilities.formatDate(article.getPublishedAt());
        holder.textView_date.setText(formattedDate);

        holder.root.setOnClickListener(v -> listener.onListFragmentInteraction(
                holder.article, holder.imageView, holder.textView_title));
    }

    @Override
    public int getItemCount() {
        if (articles == null)
            return 0;

        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View root;

        @BindView(R.id.imageView)
        ImageView imageView;

        @BindView(R.id.textView_title)
        TextView textView_title;

        @BindView(R.id.textView_author)
        TextView textView_author;

        @BindView(R.id.textView_date)
        TextView textView_date;

        Article article;

        ViewHolder(View view) {
            super(view);
            root = view;
            ButterKnife.bind(this, view);
        }
    }
}
