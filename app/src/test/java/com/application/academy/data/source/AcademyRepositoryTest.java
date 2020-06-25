package com.application.academy.data.source;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.application.academy.data.CourseEntity;
import com.application.academy.data.ModuleEntity;
import com.application.academy.data.source.remote.RemoteDataSource;
import com.application.academy.data.source.remote.response.ContentResponse;
import com.application.academy.data.source.remote.response.CourseResponse;
import com.application.academy.data.source.remote.response.ModuleResponse;
import com.application.academy.utils.DataDummy;
import com.application.academy.utils.LiveDataTestUtil;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

public class AcademyRepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private RemoteDataSource remote = Mockito.mock(RemoteDataSource.class);
    private FakeAcademyRepository academyRepository = new FakeAcademyRepository(remote);

    private List<CourseResponse> courseResponses = DataDummy.generateRemoteDummyCourses();
    private String courseId = courseResponses.get(0).getId();
    private List<ModuleResponse> moduleResponses = DataDummy.generateRemoteDummyModules(courseId);
    private String moduleId = moduleResponses.get(0).getModuleId();
    private ContentResponse content = DataDummy.generateRemoteDummyContent(moduleId);


    @Test
    public void getAllCourse() {
        doAnswer(invocation -> {
            ((RemoteDataSource.LoadCoursesCallback) invocation.getArguments()[0])
                    .onAllCoursesReceived(courseResponses);
            return null;
        }).when(remote).getAllCourse(any(RemoteDataSource.LoadCoursesCallback.class));
        List<CourseEntity> courseEntities = LiveDataTestUtil.getValue(academyRepository.getAllCourse());
        verify(remote).getAllCourse(any(RemoteDataSource.LoadCoursesCallback.class));
        assertNotNull(courseEntities);
        assertEquals(courseResponses.size(),courseEntities.size());
    }

    @Test
    public void getCourseWithModule() {
        doAnswer(invocation -> {
            ((RemoteDataSource.LoadCoursesCallback) invocation.getArguments()[0])
                    .onAllCoursesReceived(courseResponses);
            return null;
        }).when(remote).getAllCourse(any(RemoteDataSource.LoadCoursesCallback.class));

        CourseEntity courseEntities = LiveDataTestUtil.getValue(academyRepository.getCourseWithModule(courseId));

        verify(remote).getAllCourse(any(RemoteDataSource.LoadCoursesCallback.class));

        assertNotNull(courseEntities);
        assertNotNull(courseEntities.getTitle());
        assertEquals(courseResponses.get(0).getTitle(), courseEntities.getTitle());
    }

    @Test
    public void getAllModuleByCourse() {
        doAnswer(invocation -> {
            ((RemoteDataSource.LoadModuleCallback) invocation.getArguments()[1])
                    .onAllModuleReceived(moduleResponses);
            return null;
        }).when(remote).getModules(eq(courseId), any(RemoteDataSource.LoadModuleCallback.class));

        List<ModuleEntity> courseEntities = LiveDataTestUtil.getValue(academyRepository.getAllModuleByCourse(courseId));

        verify(remote).getModules(eq(courseId), any(RemoteDataSource.LoadModuleCallback.class));

        assertNotNull(courseEntities);
        assertEquals(moduleResponses.size(), courseEntities.size());
    }

    @Test
    public void getBookmarkedCourse() {
        doAnswer(invocation -> {
            ((RemoteDataSource.LoadCoursesCallback) invocation.getArguments()[0])
                    .onAllCoursesReceived(courseResponses);
            return null;
        }).when(remote).getAllCourse(any(RemoteDataSource.LoadCoursesCallback.class));

        List<CourseEntity> courseEntities = LiveDataTestUtil.getValue(academyRepository.getBookmarkedCourse());

        verify(remote).getAllCourse(any(RemoteDataSource.LoadCoursesCallback.class));

        assertNotNull(courseEntities);
        assertEquals(courseResponses.size(), courseEntities.size());
    }

    @Test
    public void getContent() {
        doAnswer(invocation -> {
            ((RemoteDataSource.LoadModuleCallback) invocation.getArguments()[1])
                    .onAllModuleReceived(moduleResponses);
            return null;
        }).when(remote).getModules(eq(courseId), any(RemoteDataSource.LoadModuleCallback.class));

        doAnswer(invocation -> {
            ((RemoteDataSource.LoadContentCallback) invocation.getArguments()[1])
                    .onAllContentReceived(content);
            return null;
        }).when(remote).getContent(eq(moduleId), any(RemoteDataSource.LoadContentCallback.class));

        ModuleEntity courseEntitiesContent = LiveDataTestUtil.getValue(academyRepository.getContent(courseId, moduleId));

        verify(remote).getModules(eq(courseId), any(RemoteDataSource.LoadModuleCallback.class));

        verify(remote).getContent(eq(moduleId), any(RemoteDataSource.LoadContentCallback.class));

        assertNotNull(courseEntitiesContent);
        assertNotNull(courseEntitiesContent.contentEntity);
        assertNotNull(courseEntitiesContent.contentEntity.getContent());
        assertEquals(content.getContent(), courseEntitiesContent.contentEntity.getContent());
    }
}