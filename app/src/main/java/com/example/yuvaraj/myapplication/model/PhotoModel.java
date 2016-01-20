package com.example.yuvaraj.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PhotoModel implements Parcelable  {

    String description, image;
    long datePosted;
    int galleryId;

    public int getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(long datePosted) {
        this.datePosted = datePosted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.description);
        dest.writeString(this.image);
        dest.writeLong(this.datePosted);
        dest.writeInt(this.galleryId);
    }

    public PhotoModel() {
    }

    protected PhotoModel(Parcel in) {
        this.description = in.readString();
        this.image = in.readString();
        this.datePosted = in.readLong();
        this.galleryId = in.readInt();
    }

    public static final Creator<PhotoModel> CREATOR = new Creator<PhotoModel>() {
        public PhotoModel createFromParcel(Parcel source) {
            return new PhotoModel(source);
        }

        public PhotoModel[] newArray(int size) {
            return new PhotoModel[size];
        }
    };
}
