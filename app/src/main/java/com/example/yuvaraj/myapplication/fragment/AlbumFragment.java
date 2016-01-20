package com.example.yuvaraj.myapplication.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.yuvaraj.myapplication.Constant;
import com.example.yuvaraj.myapplication.MainActivity;
import com.example.yuvaraj.myapplication.R;
import com.example.yuvaraj.myapplication.adapter.RecyclerAlbumAdapter;
import com.example.yuvaraj.myapplication.decorator.GridSpacingItemDecorator;
import com.example.yuvaraj.myapplication.helpers.VolleyHelper;
import com.example.yuvaraj.myapplication.model.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AlbumFragment extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerAlbumAdapter adapter;
    ArrayList<Model> galleryArrayList;
    private static final long NOW = new Date().getTime();
    String TAG = MainActivity.class.getSimpleName();
    SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ProgressDialog progressDialog;
    boolean includeEdge;
    boolean userScrolled = false;
    GridLayoutManager mLayoutManager;
    boolean loading;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    int page = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        galleryArrayList = new ArrayList<>();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();


        // make json array request
        VolleyHelper.makeJsonArrayRequest(getActivity(), Constant.gallery_url, new VolleyHelper.VolleyArrayResponseListener() {

            @Override
            public void onResponse(JSONArray response) {
                try {

                    for (int i = 0; i < response.length(); i++) {

                        Model photosModel = new Model();
                        JSONObject object = response.getJSONObject(i);
                        photosModel.setContent(object.getString("name"));
                        photosModel.setImage(object.getString("image"));
                        photosModel.setGallery_id(object.getInt("id"));
                        photosModel.setTotal(object.getInt("total"));

                        Date date = null;
                        long timeInMillisSinceEpoch = 0;
                        try {
                            date = sdf.parse(object.getString("created"));
                            timeInMillisSinceEpoch = date.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        photosModel.setDatePosted(timeInMillisSinceEpoch);
                        galleryArrayList.add(photosModel);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter = new RecyclerAlbumAdapter(getActivity(), galleryArrayList, mLayoutManager );
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.photos);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        int spanCount = 2;
        int spacing = 20;
        includeEdge = true;
        mRecyclerView.addItemDecoration(new GridSpacingItemDecorator(spanCount, spacing, includeEdge));



        // Scroll listener

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    // proceed if not already loading
                    if (!loading) {

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount && userScrolled) {
                            galleryArrayList.add(null);
                            adapter.notifyItemInserted(galleryArrayList.size());
                            loading = true; // set loading to true to prevent concurrent too many load

                            // make json array request

                            VolleyHelper.makeJsonArrayRequest(getActivity(), Constant.gallery_url + "?page=" + (++page), new VolleyHelper.VolleyArrayResponseListener() {

                                @Override
                                public void onResponse(JSONArray response) {
                                    try {

                                        // remove the progress loader which is in last at galleryArrayList
                                        galleryArrayList.remove(galleryArrayList.size() - 1);
                                        adapter.notifyItemRemoved(galleryArrayList.size());

                                        if (response.length() == 0) {
                                            Toast.makeText(getActivity(), "You reached the end!", Toast.LENGTH_LONG).show();
                                        } else {
                                            for (int i = 0; i < response.length(); i++) {

                                                Model photosModel = new Model();
                                                JSONObject object = response.getJSONObject(i);
                                                photosModel.setContent(object.getString("name"));
                                                photosModel.setImage(object.getString("image"));
                                                photosModel.setGallery_id(object.getInt("id"));

                                                Date date = null;
                                                long timeInMillisSinceEpoch = 0;
                                                try {
                                                    date = sdf.parse(object.getString("created"));
                                                    timeInMillisSinceEpoch = date.getTime();
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                photosModel.setDatePosted(timeInMillisSinceEpoch);
                                                galleryArrayList.add(photosModel);
                                            }
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    adapter.notifyDataSetChanged();
                                    loading = false;
                                }

                                @Override
                                public void onError(VolleyError error) {
                                    if (error.networkResponse == null) {
                                        Log.d("track", "Time out");
                                        page--;
                                    }
                                }
                            });
                        }
                    }
                }
            }
        }); // end of scroll listener


        return view;
    }
}
