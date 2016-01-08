package com.example.yuvaraj.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yuvaraj.myapplication.Constant;
import com.example.yuvaraj.myapplication.R;
import com.example.yuvaraj.myapplication.model.Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    ArrayList<Model> updates = new ArrayList<>();
    GridLayoutManager gridLayoutManager;

    public RecyclerAlbumAdapter(Context context, ArrayList<Model> updates, GridLayoutManager mLayoutManager) {
        this.updates = updates;
        this.mContext = context;
        gridLayoutManager = mLayoutManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder vh = null;

        if (viewType == 1)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_album, null);
            vh = new AlbumHolder(view);
        }
        else if (viewType == 2)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_more, parent, false);
            vh = new ProgressViewHolder(v);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof AlbumHolder) {
            ((AlbumHolder)holder).name.setText(updates.get(position).getContent());
            String image = updates.get(position).getImage();
            Picasso.with(mContext)
                    .load(Constant.site_url + image)
                    .placeholder(R.color.grey)
                    .into(((AlbumHolder) holder).image);

        }
        else if (holder instanceof ProgressViewHolder)
        {
            ((ProgressViewHolder)holder).progressBar.setIndeterminate(true);
        }



    }

    @Override
    public int getItemCount() {
        return updates.size();
    }


    @Override
    public int getItemViewType(int position) {

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (updates.get(position) == null)
                {
                    return 2;
                }
                else
                {
                    return 1;
                }
            }
        });


        if (updates.get(position)==null)
        {
           return 2;
        }
        else
        {
           return 1;
        }
    }

    public class AlbumHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        ImageView image;

        public AlbumHolder(View itemView) {
            super(itemView);
            this.name = (TextView)itemView.findViewById(R.id.content);
            image = (ImageView)itemView.findViewById(R.id.image);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // handle click event here

        }
    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        }
    }
}
