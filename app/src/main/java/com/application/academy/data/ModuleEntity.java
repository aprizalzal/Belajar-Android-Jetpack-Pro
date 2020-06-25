package com.application.academy.data;

public class ModuleEntity {
    public ContentEntity contentEntity;
    private String mModuleId;
    private String mCourseId;
    private String mTitle;
    private Integer mPosition;
    private boolean mRead = false;

    public ModuleEntity(String mModuleId, String mCourseId, String mTitle, Integer mPosition, Boolean read) {
        this.mModuleId = mModuleId;
        this.mCourseId = mCourseId;
        this.mTitle = mTitle;
        this.mPosition = mPosition;
        if (read !=null){
            this.mRead = read;
        }
    }

    public String getModuleId() {
        return mModuleId;
    }

    public void setModuleId(String mModuleId) {
        this.mModuleId = mModuleId;
    }

    public String getCourseId() {
        return mCourseId;
    }

    public void setCourseId(String mCourseId) {
        this.mCourseId = mCourseId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Integer getPosition() {
        return mPosition;
    }

    public void setPosition(Integer mPosition) {
        this.mPosition = mPosition;
    }

    public boolean isRead() {
        return mRead;
    }

    public void setRead(boolean read) {
        this.mRead = read;
    }
}
