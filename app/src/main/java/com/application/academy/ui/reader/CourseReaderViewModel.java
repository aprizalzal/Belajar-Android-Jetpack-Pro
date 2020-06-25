package com.application.academy.ui.reader;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.application.academy.data.ModuleEntity;
import com.application.academy.data.source.AcademyRepository;

import java.util.List;

public class CourseReaderViewModel extends ViewModel {
    private String courseId;
    private String moduleId;

    private AcademyRepository academyRepository;

    public CourseReaderViewModel(AcademyRepository academyRepository) {
        this.academyRepository = academyRepository;
    }

    public void setSelectedCourse(String courseId) {
        this.courseId = courseId;
    }

    public void setSelectedModule(String moduleId) {
        this.moduleId = moduleId;
    }

    public LiveData<List<ModuleEntity>> getModules(){
        return academyRepository.getAllModuleByCourse(courseId);
    }

    public LiveData<ModuleEntity> getSelectedModule(){
        return academyRepository.getContent(courseId,moduleId);
    }
}
