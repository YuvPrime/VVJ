package com.example.yuvaraj.myapplication.model;

public class ArticleModel {

    String title, content, source, url, image;
    Long datePosted;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getContent() {

        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Long datePosted) {
        this.datePosted = datePosted;
    }
}
