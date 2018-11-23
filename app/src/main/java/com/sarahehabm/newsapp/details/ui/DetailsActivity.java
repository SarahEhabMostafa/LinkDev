package com.sarahehabm.newsapp.details.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sarahehabm.newsapp.R;
import com.sarahehabm.newsapp.Utilities;
import com.sarahehabm.newsapp.list.model.Article;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity {
    public static final String KEY_ARTICLE = "article";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.textView_title)
    TextView textView_title;

    @BindView(R.id.textView_author)
    TextView textView_author;

    @BindView(R.id.textView_description)
    TextView textView_description;

    @BindView(R.id.textView_date)
    TextView textView_date;

    @BindView(R.id.button_open)
    Button button_open;

    @BindView(R.id.imageView)
    ImageView imageView;

    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent().getExtras() != null) {
            article = getIntent().getExtras().getParcelable(KEY_ARTICLE);
        }

        initializeViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void initializeViews() {
        Glide.with(this)
                .load(article.getUrlToImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder))
                .into(imageView);

        String formattedDate = Utilities.formatDate(article.getPublishedAt());
        textView_date.setText(formattedDate);

        textView_title.setText(article.getTitle());

        String author = article.getAuthor() == null || article.getAuthor().isEmpty()?
                getString(R.string.n_a) : article.getAuthor(),
                by_author = getString(R.string.by_author, author);
        textView_author.setText(by_author);

        textView_description.setText(article.getDescription());
        textView_description.setMovementMethod(new ScrollingMovementMethod());
    }

    @OnClick(R.id.button_open)
    public void onOpenClick() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(article.getUrl()));
        startActivity(intent);
    }
}
