package com.example.yuvaraj.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuvaraj.myapplication.R;
import com.example.yuvaraj.myapplication.model.VideoModel;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerVideoAdapter extends RecyclerView.Adapter<RecyclerVideoAdapter.ViewHolder> {

    private Context mContext;
    ArrayList<VideoModel> videoList = new ArrayList<>();

    public RecyclerVideoAdapter(Context context, ArrayList<VideoModel> videoList) {
        this.videoList = videoList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_video, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.title.setText(videoList.get(position).getTitle());
        String image = videoList.get(position).getThumbnail();
        holder.posted_date.setReferenceTime(videoList.get(position).getPosted_date());
        Picasso.with(mContext)
                .load(image)
                .placeholder(R.drawable.loading)
                .into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView thumbnail;
        RelativeTimeTextView posted_date;


        public ViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView)itemView.findViewById(R.id.title);
            thumbnail = (ImageView)itemView.findViewById(R.id.image);
            posted_date = (RelativeTimeTextView)itemView.findViewById(R.id.postedDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = getLayoutPosition();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoList.get(position).getUrl()));
            mContext.startActivity(intent);
        }
    }
}
