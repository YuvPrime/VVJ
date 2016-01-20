package com.example.yuvaraj.myapplication.adapter;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yuvaraj.myapplication.Constant;
import com.example.yuvaraj.myapplication.R;
import com.example.yuvaraj.myapplication.model.PhotoModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class FullScreenImageAdapter extends PagerAdapter {

    ArrayList<PhotoModel> photoList = new ArrayList<>();
    Activity activity;
    int _position;
    int itemPosition = 0;

    public FullScreenImageAdapter(Activity activity,
                                  ArrayList<PhotoModel> photoList, int position) {
        this.activity = activity;
        this.photoList = photoList;
        this._position = position;
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgDisplay;
        LayoutInflater inflater;
//        Button setAsWallpaper;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);

        itemPosition = position - 1;

        imgDisplay = (ImageView) viewLayout.findViewById(R.id.image);
//        setAsWallpaper = (Button) viewLayout.findViewById(R.id.setAsWallpaper);
        Picasso.with(activity)
                .load(Constant.site_url + photoList.get(position).getImage())
                .placeholder(R.color.grey)
                .into(imgDisplay);

//        setAsWallpaper.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                WallpaperManager wallpaperManager = WallpaperManager.getInstance(activity);
//                String path = Constant.site_url + photoList.get(itemPosition).getImage();
//                try {
//                    InputStream inputStream = new URL(path).openStream();
//                    wallpaperManager.setStream(inputStream);
//                    Toast.makeText(activity, "Whao! Wall Paper has been set", Toast.LENGTH_SHORT).show();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Toast.makeText(activity, "Oops! Couldn't set wallpaper", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
