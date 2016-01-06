package com.example.yuvaraj.myapplication.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuvaraj.myapplication.Constant;
import com.example.yuvaraj.myapplication.R;
import com.example.yuvaraj.myapplication.fragment.PhotosFragment;
import com.example.yuvaraj.myapplication.model.Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerGalleryAdapter extends RecyclerView.Adapter<RecyclerGalleryAdapter.ViewHolder> {

    private Context mContext;
    ArrayList<Model> updates = new ArrayList<>();

    public RecyclerGalleryAdapter(Context context, ArrayList<Model> updates) {
        this.updates = updates;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_photos, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.name.setText(updates.get(position).getContent());
        String image = updates.get(position).getImage();
        int gallery_id = updates.get(position).getGallery_id();
        holder.image.setImageResource(R.drawable.jilla);

//        Picasso.with(mContext)
//                .load(Constant.image_path + "gallery/" + gallery_id + "/" + image)
//                .placeholder(R.drawable.loading)
//                .into(holder.image);

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
            this.name = (TextView)itemView.findViewById(R.id.content);
            image = (ImageView)itemView.findViewById(R.id.image);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Toast.makeText(mContext, "Clicked", Toast.LENGTH_LONG).show();
            PhotosFragment fragment = new PhotosFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container,fragment);
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        }
    }
}
