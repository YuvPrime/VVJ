package com.example.yuvaraj.myapplication.model;

public class VideoModel {

    String thumbnail, title, url;
    long posted_date;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getPosted_date() {
        return posted_date;
    }

    public void setPosted_date(long posted_date) {
        this.posted_date = posted_date;
    }
}
