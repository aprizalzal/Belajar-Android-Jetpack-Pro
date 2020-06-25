package com.application.academy.ui.academy;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.application.academy.data.CourseEntity;
import com.application.academy.data.source.AcademyRepository;
import com.application.academy.utils.DataDummy;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AcademyViewModelTest {

    private AcademyViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private AcademyRepository academyRepository;

    @Mock
    private Observer<List<CourseEntity>> observer;

    @Before
    public void setUp(){
        viewModel = new AcademyViewModel(academyRepository);
    }

    @Test
    public void getAcademy() {
        List<CourseEntity> dummyCourses = DataDummy.generateDummyCourses();
        MutableLiveData<List<CourseEntity>> courses = new MutableLiveData<>();
        courses.setValue(dummyCourses);

        when(academyRepository.getAllCourse()).thenReturn(courses);
        List<CourseEntity> courseEntities = viewModel.getCourse().getValue();
        verify(academyRepository).getAllCourse();
        assertNotNull(courseEntities);
        assertEquals(5, courseEntities.size());

        viewModel.getCourse().observeForever(observer);
        verify(observer).onChanged(dummyCourses);
    }
}