package com.application.academy.ui.bookmark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.application.academy.data.CourseEntity;
import com.application.academy.data.source.AcademyRepository;

import java.util.List;

public class BookmarkViewModel extends ViewModel {
    private AcademyRepository academyRepository;

    public BookmarkViewModel(AcademyRepository academyRepository) {
        this.academyRepository = academyRepository;
    }

    public LiveData<List<CourseEntity>> getCourse(){
        return academyRepository.getBookmarkedCourse();
    }
}
