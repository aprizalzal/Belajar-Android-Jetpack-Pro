package com.application.academy.data.source.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

public class CourseResponse implements Parcelable {
    public CourseResponse(){

    }
    private String id;
    private String title;
    private String description;
    private String date;
    private String imagePath;

    public CourseResponse(String id, String title, String description, String date, String imagePath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.imagePath = imagePath;
    }

    protected CourseResponse(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        date = in.readString();
        imagePath = in.readString();
    }

    public static final Creator<CourseResponse> CREATOR = new Creator<CourseResponse>() {
        @Override
        public CourseResponse createFromParcel(Parcel in) {
            return new CourseResponse(in);
        }

        @Override
        public CourseResponse[] newArray(int size) {
            return new CourseResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(date);
        parcel.writeString(imagePath);
    }
}
