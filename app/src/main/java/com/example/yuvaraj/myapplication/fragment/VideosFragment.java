package com.example.yuvaraj.myapplication.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.yuvaraj.myapplication.Constant;
import com.example.yuvaraj.myapplication.NavDrawerInterface;
import com.example.yuvaraj.myapplication.R;
import com.example.yuvaraj.myapplication.adapter.RecyclerUpdateAdapter;
import com.example.yuvaraj.myapplication.adapter.RecyclerVideoAdapter;
import com.example.yuvaraj.myapplication.decorator.VerticalSpaceItemDecoration;
import com.example.yuvaraj.myapplication.helpers.VideoHelpter;
import com.example.yuvaraj.myapplication.helpers.VolleyHelper;
import com.example.yuvaraj.myapplication.model.Model;
import com.example.yuvaraj.myapplication.model.VideoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideosFragment extends Fragment {

    // Declarations
    RecyclerView mRecyclerView;
    RecyclerVideoAdapter adapter;
    ArrayList<VideoModel> videosList;
    SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ProgressDialog progressDialog;
    boolean loading;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    boolean userScrolled = false;
    int page = 1;
    String youtubeThumbnail;
    String videoId, url = null;
    NavDrawerInterface listener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize array list
        videosList = new ArrayList<>();

        // Show progress dialog loading
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        // make json array request
        VolleyHelper.makeJsonArrayRequest(getActivity(), Constant.videos_url, new VolleyHelper.VolleyArrayResponseListener() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        VideoModel model = new VideoModel();
                        JSONObject object = response.getJSONObject(i);
                        model.setTitle(object.getString("title"));
                        Date date = null;
                        long timeInMillisSinceEpoch = 0;
                        try {
                            date = sdf.parse(object.getString("created"));
                            timeInMillisSinceEpoch = date.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        model.setPosted_date(timeInMillisSinceEpoch);
                         url = object.getString("url");
                        model.setUrl(url);

                        videoId = VideoHelpter.getVideoId(url);

                        if (videoId == null)
                        {
                            youtubeThumbnail = "";

                        }
                        else
                        {
                            youtubeThumbnail = "http://img.youtube.com/vi/" +videoId+"/sddefault.jpg";
                        }
                        model.setThumbnail(youtubeThumbnail);
                        videosList.add(model);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                progressDialog.hide();
            }

            @Override
            public void onError(VolleyError error) {
                if (error.networkResponse == null) {
                    Log.d("track", "Time out");
                    Toast.makeText(getActivity(), "Timed out. Try again!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.hide();
            }

        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (NavDrawerInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NavDrawer");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.videos);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(30));
        adapter = new RecyclerVideoAdapter(getActivity(), videosList);
        mRecyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        activity.setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.showNavDrawer();
            }
        });
        
        return view;
    }
    
}
