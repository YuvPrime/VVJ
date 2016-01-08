package com.example.yuvaraj.myapplication.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.yuvaraj.myapplication.Constant;
import com.example.yuvaraj.myapplication.MainActivity;
import com.example.yuvaraj.myapplication.R;
import com.example.yuvaraj.myapplication.adapter.RecyclerPhotoAdapter;
import com.example.yuvaraj.myapplication.decorator.GridSpacingItemDecorator;
import com.example.yuvaraj.myapplication.model.Model;
import com.example.yuvaraj.myapplication.volley.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PhotosFragment extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerPhotoAdapter adapter;
    ArrayList<Model> galleryArrayList;
    private static final long NOW = new Date().getTime();
    String TAG = MainActivity.class.getSimpleName();
    SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        galleryArrayList = new ArrayList<>();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                Constant.gallery_url, (String)null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {

                            for (int i = 0; i < response.length(); i++){

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

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                        progressDialog.hide();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                VolleyLog.d(TAG, "error msg" + error.networkResponse);
                Log.d("track", "Error");

                if(error.networkResponse == null){
                    Log.d("track","Time out");
                }
                progressDialog.hide();

            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjReq, "tag_json_obj");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.photos);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        int spanCount = 2;
        int spacing = 30;
        boolean includeEdge = true;
        mRecyclerView.addItemDecoration(new GridSpacingItemDecorator(spanCount, spacing, includeEdge));


        adapter = new RecyclerPhotoAdapter(getActivity(), galleryArrayList);
        mRecyclerView.setAdapter(adapter);

        return view;
    }
}
