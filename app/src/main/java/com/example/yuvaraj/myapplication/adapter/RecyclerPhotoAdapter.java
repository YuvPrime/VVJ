package com.example.yuvaraj.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuvaraj.myapplication.Constant;
import com.example.yuvaraj.myapplication.R;
import com.example.yuvaraj.myapplication.model.Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerPhotoAdapter extends RecyclerView.Adapter<RecyclerPhotoAdapter.ViewHolder> {

    private Context mContext;
    ArrayList<Model> updates = new ArrayList<>();

    public RecyclerPhotoAdapter(Context context, ArrayList<Model> updates) {
        this.updates = updates;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_album, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.name.setText(updates.get(position).getContent());
        String image = updates.get(position).getImage();
        int gallery_id = updates.get(position).getGallery_id();

        Picasso.with(mContext)
                .load(Constant.image_path + "gallery/" + gallery_id + "/" + image)
                .placeholder(R.drawable.loading)
                .into(holder.image);

//        Glide.with(mContext)
//                .load(Constant.image_path + "gallery/" + gallery_id + "/" + image)
//                .placeholder(R.drawable.loading)
//                .centerCrop()
//                .crossFade()
//                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return updates.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        ImageView image;


        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView)itemView.findViewById(R.id.title);
            image = (ImageView)itemView.findViewById(R.id.thumbnail);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
