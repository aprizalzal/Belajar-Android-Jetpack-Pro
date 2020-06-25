package com.application.academy.data.source;

import androidx.lifecycle.LiveData;

import com.application.academy.data.CourseEntity;
import com.application.academy.data.ModuleEntity;

import java.util.List;

public interface AcademyDataSource {
    LiveData<List<CourseEntity>> getAllCourse();

    LiveData<CourseEntity> getCourseWithModule(String courseId);

    LiveData<List<ModuleEntity>> getAllModuleByCourse (String courseId);

    LiveData<List<CourseEntity>> getBookmarkedCourse();

    LiveData<ModuleEntity> getContent(String courseId, String moduleId);
}
