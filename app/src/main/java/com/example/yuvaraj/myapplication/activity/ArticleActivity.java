package com.example.yuvaraj.myapplication.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuvaraj.myapplication.Constant;
import com.example.yuvaraj.myapplication.R;
import com.squareup.picasso.Picasso;

public class ArticleActivity extends AppCompatActivity {

    String title_b, content_b, source_b;
    Bundle bundle;
    Toolbar toolbar;
    TextView title, content;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_activity);

        title = (TextView)findViewById(R.id.title);
        content = (TextView)findViewById(R.id.content);
        image = (ImageView)findViewById(R.id.image);

        bundle = getIntent().getExtras();
        title_b = bundle.getString("title");
        content_b = bundle.getString("content");
        source_b = bundle.getString("source");

        title.setText(title_b);
        content.setText(content_b);

        Picasso.with(ArticleActivity.this)
                .load(Constant.image_path + "updates/" + "b.jpg")
                .into(image);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Article");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");

    }
}
