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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.yuvaraj.myapplication.Constant;
import com.example.yuvaraj.myapplication.NavDrawerInterface;
import com.example.yuvaraj.myapplication.R;
import com.example.yuvaraj.myapplication.adapter.RecyclerUpdateAdapter;
import com.example.yuvaraj.myapplication.decorator.VerticalSpaceItemDecoration;
import com.example.yuvaraj.myapplication.helpers.VolleyHelper;
import com.example.yuvaraj.myapplication.model.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpdatesFragment extends Fragment {

    // Declarations
    RecyclerView mRecyclerView;
    RecyclerUpdateAdapter adapter;
    ArrayList<Model> updateArrayList;
    SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ProgressDialog progressDialog;
    boolean loading;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    boolean userScrolled = false;
    NavDrawerInterface listener;

    int page = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize array list
        updateArrayList = new ArrayList<>();

        // Show progress dialog loading
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        // make json array request
        VolleyHelper.makeJsonArrayRequest(getActivity(), Constant.updates_url, new VolleyHelper.VolleyArrayResponseListener() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        Model updateModel = new Model();
                        JSONObject object = response.getJSONObject(i);
                        updateModel.setContent(object.getString("content"));
                        updateModel.setImage(object.getString("image"));
                        Date date = null;
                        long timeInMillisSinceEpoch = 0;
                        try {
                            date = sdf.parse(object.getString("created"));
                            timeInMillisSinceEpoch = date.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        updateModel.setDatePosted(timeInMillisSinceEpoch);
                        updateArrayList.add(updateModel);
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
                }
                progressDialog.hide();
            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_updates, container, false);

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



        mRecyclerView = (RecyclerView)view.findViewById(R.id.updates);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        adapter = new RecyclerUpdateAdapter(getActivity(), updateArrayList);
        mRecyclerView.setAdapter(adapter);




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
                            updateArrayList.add(null);
                            adapter.notifyItemInserted(updateArrayList.size());
                            loading = true; // set loading to true to prevent concurrent too many load

                            // make json array request

                            VolleyHelper.makeJsonArrayRequest(getActivity(), Constant.updates_url + "?page=" + (++page), new VolleyHelper.VolleyArrayResponseListener() {

                                @Override
                                public void onResponse(JSONArray response) {
                                    try {

                                        // remove the progress loader which is in last at updateArrayList
                                        updateArrayList.remove(updateArrayList.size() - 1);
                                        adapter.notifyItemRemoved(updateArrayList.size());

                                        if (response.length() == 0) {
                                            Toast.makeText(getActivity(), "You reached the end!", Toast.LENGTH_LONG).show();
                                        } else {
                                            for (int i = 0; i < response.length(); i++) {
                                                Model updateModel = new Model();
                                                JSONObject object = response.getJSONObject(i);
                                                updateModel.setContent(object.getString("content"));
                                                updateModel.setImage(object.getString("image"));
                                                Date date = null;
                                                long timeInMillisSinceEpoch = 0;
                                                try {
                                                    date = sdf.parse(object.getString("created"));
                                                    timeInMillisSinceEpoch = date.getTime();
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                updateModel.setDatePosted(timeInMillisSinceEpoch);
                                                updateArrayList.add(updateModel);
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
    public boolean onOptionsItemSelected(MenuItem item) {

//        switch (item.getItemId())
//        {
//            case android.R.id.home:
//                getActivity().;
//        }

        return super.onOptionsItemSelected(item);
    }
}
