package com.application.academy.ui.academy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.application.academy.data.CourseEntity;
import com.application.academy.data.source.AcademyRepository;

import java.util.List;

public class AcademyViewModel extends ViewModel {
    private AcademyRepository academyRepository;

    public AcademyViewModel(AcademyRepository academyRepository) {
        this.academyRepository = academyRepository;
    }

    public LiveData<List<CourseEntity>> getCourse(){
        return academyRepository.getAllCourse();
    }
}
