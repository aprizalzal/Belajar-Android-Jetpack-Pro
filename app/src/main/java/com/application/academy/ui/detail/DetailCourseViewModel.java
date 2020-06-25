package com.application.academy.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.application.academy.data.CourseEntity;
import com.application.academy.data.ModuleEntity;
import com.application.academy.data.source.AcademyRepository;

import java.util.List;

public class DetailCourseViewModel extends ViewModel {
    private String courseId;
    private AcademyRepository academyRepository;

    public DetailCourseViewModel(AcademyRepository academyRepository) {
        this.academyRepository = academyRepository;
    }

    public void setSelectedCourse(String courseId) {
        this.courseId = courseId;
    }

    public LiveData<CourseEntity> getCourse(){
        return academyRepository.getCourseWithModule(courseId);
    }
    public LiveData<List<ModuleEntity>> getModules(){
        return academyRepository.getAllModuleByCourse(courseId);
    }
}
