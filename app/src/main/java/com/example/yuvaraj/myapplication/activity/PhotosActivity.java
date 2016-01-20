package com.example.yuvaraj.myapplication.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.yuvaraj.myapplication.Constant;
import com.example.yuvaraj.myapplication.MainActivity;
import com.example.yuvaraj.myapplication.R;
import com.example.yuvaraj.myapplication.adapter.RecyclerAlbumAdapter;
import com.example.yuvaraj.myapplication.adapter.RecyclerPhotoAdapter;
import com.example.yuvaraj.myapplication.decorator.GridSpacingItemDecorator;
import com.example.yuvaraj.myapplication.helpers.VolleyHelper;
import com.example.yuvaraj.myapplication.model.Model;
import com.example.yuvaraj.myapplication.model.PhotoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PhotosActivity extends AppCompatActivity{

    RecyclerView mRecyclerView;
    RecyclerPhotoAdapter adapter;
    ArrayList<PhotoModel> photosList;
    private static final long NOW = new Date().getTime();
    String TAG = PhotosActivity.class.getSimpleName();
    SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ProgressDialog progressDialog;
    boolean includeEdge;
    boolean userScrolled = false;
    GridLayoutManager mLayoutManager;
    boolean loading;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    Toolbar toolbar;
    int page = 1;
    int gallery_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos_activity);
        photosList = new ArrayList<>();

        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView = (RecyclerView)findViewById(R.id.photos);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Photos");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int spanCount = 2;
        int spacing = 20;
        includeEdge = true;
        mRecyclerView.addItemDecoration(new GridSpacingItemDecorator(spanCount, spacing, includeEdge));

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        gallery_id = getIntent().getExtras().getInt("gallery_id");

        loadPhotos(this, gallery_id);




    }

    private void loadPhotos(PhotosActivity photosActivity, int gallery_id) {

        // make json array request
        VolleyHelper.makeJsonArrayRequest(photosActivity, Constant.photos_url+gallery_id, new VolleyHelper.VolleyArrayResponseListener() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {

                        Log.d("responseoo", String.valueOf(response));

                        PhotoModel photosModel = new PhotoModel();
                        JSONObject object = response.getJSONObject(i);
                        Date date = null;
                        long timeInMillisSinceEpoch = 0;
                        try {
                            date = sdf.parse(object.getString("created"));
                            timeInMillisSinceEpoch = date.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        photosModel.setDescription(object.getString("description"));
                        photosModel.setImage(object.getString("image"));
                        photosModel.setGalleryId(object.getInt("album_id"));
                        photosModel.setDatePosted(timeInMillisSinceEpoch);
                        photosList.add(photosModel);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter = new RecyclerPhotoAdapter(PhotosActivity.this, photosList, mLayoutManager);
                mRecyclerView.setAdapter(adapter);
                progressDialog.hide();

            }

            @Override
            public void onError(VolleyError error) {
                if (error.networkResponse == null) {
                    Log.d("track", "Time out");
                }
                progressDialog.hide();
            }

        });


    }
}
