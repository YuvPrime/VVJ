package com.example.yuvaraj.myapplication.activity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.yuvaraj.myapplication.Constant;
import com.example.yuvaraj.myapplication.R;
import com.example.yuvaraj.myapplication.adapter.FullScreenImageAdapter;
import com.example.yuvaraj.myapplication.helpers.DataWrapper;
import com.example.yuvaraj.myapplication.model.PhotoModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class FullScreenViewActivity extends AppCompatActivity{

    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;
    int currentPosition = 0;
    ArrayList<PhotoModel> photoList;
    Button setAsWallpaper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);

        Intent i = getIntent();
        photoList = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.pager);

        int position = i.getIntExtra("position", 0);
        photoList = i.getParcelableArrayListExtra("photos");

        adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
                photoList, position);

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(position);

    }
}