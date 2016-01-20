package com.example.yuvaraj.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yuvaraj.myapplication.Constant;
import com.example.yuvaraj.myapplication.R;
import com.example.yuvaraj.myapplication.model.Model;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecyclerUpdateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    ArrayList<Model> updates = new ArrayList<>();

    public RecyclerUpdateAdapter(Context context, ArrayList<Model> updates) {
        this.updates = updates;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder vh = null;
        if (viewType == 0)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_updates_no_image, null);
            vh = new UpdateOnlyHolder(view);
        }
        else if (viewType == 1)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_updates, null);
            vh = new UpdateWithImageHolder(view);
        }
        else if (viewType == 2)
        {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.loading_more, parent, false);

            vh = new ProgressViewHolder(v);
        }

        return vh;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof UpdateOnlyHolder)
        {
            SpannableString hashText = new SpannableString(updates.get(position).getContent());
            Matcher matcher = Pattern.compile("#([A-Za-z0-9_-]+)").matcher(hashText);
            while (matcher.find()) {

                hashText.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3F51B5")), matcher.start(), matcher.end(), 0);
            }
            int updateCount = position;
            String staticUpdateCount = "<font color=\"#FF4081\">Update #" +(++updateCount) + " : " + "</font>";

            ((UpdateOnlyHolder)holder).content.setText(updates.get(position).getContent());
            ((UpdateOnlyHolder)holder).posted_date.setReferenceTime(updates.get(position).getDatePosted());
        }
        else if (holder instanceof UpdateWithImageHolder)
        {
            SpannableString hashText = new SpannableString(updates.get(position).getContent());
            Matcher matcher = Pattern.compile("#([A-Za-z0-9_-]+)").matcher(hashText);
            while (matcher.find()) {

                hashText.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3F51B5")), matcher.start(), matcher.end(), 0);
            }
            int updateCount = position;
            String staticUpdateCount = "<font color=\"#FF4081\">Update #" +(++updateCount) + " : " + "</font>";

            ((UpdateWithImageHolder)holder).content.setText(updates.get(position).getContent());
            ((UpdateWithImageHolder)holder).posted_date.setReferenceTime(updates.get(position).getDatePosted());
            String image = updates.get(position).getImage();
            if (!image.isEmpty())
            {
                Picasso.with(mContext)
                        .load(Constant.image_path + "updates/" + image)
                        .placeholder(R.color.grey)
                        .into( ((UpdateWithImageHolder)holder).image);
            }
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

        if (updates.get(position)==null)
        {
            return 2;
        }
        else
        {
            String image = updates.get(position).getImage();
            if (image.isEmpty())
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }
    }

    public class UpdateOnlyHolder extends RecyclerView.ViewHolder {

        TextView content;
        RelativeTimeTextView posted_date;

        public UpdateOnlyHolder(View itemView) {
            super(itemView);
            this.content = (TextView)itemView.findViewById(R.id.title);
            posted_date = (RelativeTimeTextView)itemView.findViewById(R.id.postedDate);
        }
    }

    public class UpdateWithImageHolder extends RecyclerView.ViewHolder {

        TextView content;
        RelativeTimeTextView posted_date;
        ImageView image;

        public UpdateWithImageHolder(View itemView) {
            super(itemView);
            this.content = (TextView)itemView.findViewById(R.id.title);
            posted_date = (RelativeTimeTextView)itemView.findViewById(R.id.postedDate);
            image = (ImageView)itemView.findViewById(R.id.image);
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
