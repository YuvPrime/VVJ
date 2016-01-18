package com.example.yuvaraj.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuvaraj.myapplication.R;
import com.example.yuvaraj.myapplication.activity.ArticleActivity;
import com.example.yuvaraj.myapplication.model.ArticleModel;
import com.example.yuvaraj.myapplication.model.VideoModel;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerArticleAdapter extends RecyclerView.Adapter<RecyclerArticleAdapter.ViewHolder> {

    private Context mContext;
    ArrayList<ArticleModel> articleList = new ArrayList<>();

    public RecyclerArticleAdapter(Context context, ArrayList<ArticleModel> articleList) {
        this.articleList = articleList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_article, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.title.setText(articleList.get(position).getTitle());
        holder.posted_date.setReferenceTime(articleList.get(position).getDatePosted());
//        holder.content.setText(articleList.get(position).getContent());
        holder.source.setText(articleList.get(position).getSource());
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, content, source;
        ImageView image;
        RelativeTimeTextView posted_date;


        public ViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView)itemView.findViewById(R.id.title);
//            this.content = (TextView)itemView.findViewById(R.id.content);
            this.source = (TextView)itemView.findViewById(R.id.source);
            this.image = (ImageView)itemView.findViewById(R.id.image);
            posted_date = (RelativeTimeTextView)itemView.findViewById(R.id.postedDate);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            Intent intent = new Intent(mContext, ArticleActivity.class);
            intent.putExtra("title", articleList.get(position).getTitle());
            intent.putExtra("content", articleList.get(position).getContent());
            intent.putExtra("source", articleList.get(position).getSource());
            mContext.startActivity(intent);
        }


    }
}
