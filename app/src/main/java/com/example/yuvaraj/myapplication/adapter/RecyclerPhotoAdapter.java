package com.example.yuvaraj.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuvaraj.myapplication.Constant;
import com.example.yuvaraj.myapplication.R;
import com.example.yuvaraj.myapplication.model.PhotoModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerPhotoAdapter extends RecyclerView.Adapter<RecyclerPhotoAdapter.ViewHolder> {

    private Context mContext;
    ArrayList<PhotoModel> photosList = new ArrayList<>();
    GridLayoutManager gridLayoutManager;


    public RecyclerPhotoAdapter(Context context, ArrayList<PhotoModel> photosList, GridLayoutManager gridLayoutManager) {
        this.photosList = photosList;
        this.mContext = context;
        this.gridLayoutManager = gridLayoutManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_photos, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String image = photosList.get(position).getImage();
        int gallery_id = photosList.get(position).getGalleryId();

        Picasso.with(mContext)
                .load(Constant.site_url + image)
                .placeholder(R.color.grey)
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;


        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.image);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
