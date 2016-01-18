package com.example.yuvaraj.myapplication.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.yuvaraj.myapplication.R;

public class ArticleActivity extends AppCompatActivity {

    String title_b, content_b, source_b;
    Bundle bundle;
    Toolbar toolbar;
    TextView title, content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_activity);

        title = (TextView)findViewById(R.id.title);
         content = (TextView)findViewById(R.id.content);


        bundle = getIntent().getExtras();
        title_b = bundle.getString("title");
        content_b = bundle.getString("content");
        source_b = bundle.getString("source");

        title.setText(title_b);
        content.setText(content_b);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Article");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");

    }
}
