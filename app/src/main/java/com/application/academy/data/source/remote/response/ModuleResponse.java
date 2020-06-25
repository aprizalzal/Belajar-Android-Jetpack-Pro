package com.application.academy.data.source.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

public class ModuleResponse implements Parcelable {
    private String moduleId;
    private String courseId;
    private String title;
    private int position;

    public ModuleResponse(String moduleId, String courseId, String title, int position) {
        this.moduleId = moduleId;
        this.courseId = courseId;
        this.title = title;
        this.position = position;
    }

    protected ModuleResponse(Parcel in) {
        moduleId = in.readString();
        courseId = in.readString();
        title = in.readString();
        position = in.readInt();
    }

    public static final Creator<ModuleResponse> CREATOR = new Creator<ModuleResponse>() {
        @Override
        public ModuleResponse createFromParcel(Parcel in) {
            return new ModuleResponse(in);
        }

        @Override
        public ModuleResponse[] newArray(int size) {
            return new ModuleResponse[size];
        }
    };

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(moduleId);
        parcel.writeString(courseId);
        parcel.writeString(title);
        parcel.writeInt(position);
    }
}
