package com.application.academy.data;


public class CourseEntity {
    private String courseId;
    private String title;
    private String description;
    private String deadline;
    private boolean bookmarked = false;
    private String imagePath;

    public CourseEntity(String courseId, String title, String description, String deadline, Boolean bookmarked, String imagePath) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        if (bookmarked !=null){
            this.bookmarked = bookmarked;
        }
        this.imagePath = imagePath;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
