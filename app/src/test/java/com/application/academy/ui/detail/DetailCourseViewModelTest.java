package com.application.academy.ui.detail;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.application.academy.data.CourseEntity;
import com.application.academy.data.ModuleEntity;
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
public class DetailCourseViewModelTest {
    private DetailCourseViewModel viewModel;
    private CourseEntity dummyCourse = DataDummy.generateDummyCourses().get(0);
    private String courseId = dummyCourse.getCourseId();
    private List<ModuleEntity> dummyModules = DataDummy.generateDummyModules(courseId);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private AcademyRepository academyRepository;

    @Mock
    private Observer<CourseEntity> courseObserver;

    @Mock
    private Observer<List<ModuleEntity>> moduleObserver;

    @Before
    public void setUp(){
            viewModel = new DetailCourseViewModel(academyRepository);
            viewModel.setSelectedCourse(courseId);
    }

    @Test
    public void getCourse() {
        MutableLiveData<CourseEntity> course = new MutableLiveData<>();
        course.setValue(dummyCourse);

        when(academyRepository.getCourseWithModule(courseId)).thenReturn(course);
        viewModel.setSelectedCourse(dummyCourse.getCourseId());
        CourseEntity courseEntity = viewModel.getCourse().getValue();
        verify(academyRepository).getCourseWithModule(courseId);
        assertNotNull(courseEntity);
        assertEquals(dummyCourse.getCourseId(),courseEntity.getCourseId());
        assertEquals(dummyCourse.getDeadline(),courseEntity.getDeadline());
        assertEquals(dummyCourse.getDescription(),courseEntity.getDescription());
        assertEquals(dummyCourse.getImagePath(),courseEntity.getImagePath());
        assertEquals(dummyCourse.getTitle(),courseEntity.getTitle());

        viewModel.getCourse().observeForever(courseObserver);
        verify(courseObserver).onChanged(dummyCourse);
    }

    @Test
    public void getModules() {
        MutableLiveData<List<ModuleEntity>> module = new MutableLiveData<>();
        module.setValue(dummyModules);

        when(academyRepository.getAllModuleByCourse(courseId)).thenReturn(module);
        List<ModuleEntity> moduleEntities = viewModel.getModules().getValue();
        verify(academyRepository).getAllModuleByCourse(courseId);
        assertNotNull(moduleEntities);
        assertEquals(7,moduleEntities.size());

        viewModel.getModules().observeForever(moduleObserver);
        verify(moduleObserver).onChanged(dummyModules);
    }
}