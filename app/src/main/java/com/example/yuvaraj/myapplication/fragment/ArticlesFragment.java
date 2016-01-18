package com.example.yuvaraj.myapplication.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.yuvaraj.myapplication.Constant;
import com.example.yuvaraj.myapplication.R;
import com.example.yuvaraj.myapplication.adapter.RecyclerArticleAdapter;
import com.example.yuvaraj.myapplication.adapter.RecyclerVideoAdapter;
import com.example.yuvaraj.myapplication.decorator.VerticalSpaceItemDecoration;
import com.example.yuvaraj.myapplication.helpers.VideoHelpter;
import com.example.yuvaraj.myapplication.helpers.VolleyHelper;
import com.example.yuvaraj.myapplication.model.ArticleModel;
import com.example.yuvaraj.myapplication.model.VideoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ArticlesFragment extends Fragment {

    // Declarations
    RecyclerView mRecyclerView;
    RecyclerArticleAdapter adapter;
    ArrayList<ArticleModel> articleList;
    SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ProgressDialog progressDialog;
    boolean loading;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    boolean userScrolled = false;
    int page = 1;
    String youtubeThumbnail;
    String videoId, url = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize array list
        articleList = new ArrayList<>();

        // Show progress dialog loading
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        // make json array request
        VolleyHelper.makeJsonArrayRequest(getActivity(), Constant.articles_url, new VolleyHelper.VolleyArrayResponseListener() {

            @Override
            public void onResponse(JSONArray response) {
                try {

                    Log.d("trackkk", String.valueOf(response));

                    for (int i = 0; i < response.length(); i++) {
                        ArticleModel model = new ArticleModel();
                        JSONObject object = response.getJSONObject(i);
                        model.setTitle(String.valueOf(Html.fromHtml(object.getString("title"))));
                        Date date = null;
                        long timeInMillisSinceEpoch = 0;
                        try {
                            date = sdf.parse(object.getString("created"));
                            timeInMillisSinceEpoch = date.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        model.setDatePosted(timeInMillisSinceEpoch);
                        model.setSource("IBTimes");
                        model.setContent(String.valueOf(Html.fromHtml(object.getString("content"))));
                        articleList.add(model);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.articles);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(15));
        adapter = new RecyclerArticleAdapter(getActivity(), articleList);
        mRecyclerView.setAdapter(adapter);
        
        return view;
    }
    
}
