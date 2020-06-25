package com.application.academy.data.source;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.application.academy.data.ContentEntity;
import com.application.academy.data.CourseEntity;
import com.application.academy.data.ModuleEntity;
import com.application.academy.data.source.remote.RemoteDataSource;
import com.application.academy.data.source.remote.response.CourseResponse;
import com.application.academy.data.source.remote.response.ModuleResponse;

import java.util.ArrayList;
import java.util.List;

public class FakeAcademyRepository implements AcademyDataSource {
    //private volatile static FakeAcademyRepository INSTANCE = null;

    private final RemoteDataSource remoteDataSource;

    //private
    FakeAcademyRepository(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }
    /*
    public static FakeAcademyRepository getInstance(RemoteDataSource remoteData){
        if (INSTANCE == null){
            synchronized (FakeAcademyRepository.class) {
                if (INSTANCE == null){
                    INSTANCE = new FakeAcademyRepository(remoteData);
                }
            }
        }
        return INSTANCE;
    }
    */
    @Override
    public LiveData<List<CourseEntity>> getAllCourse() {
        MutableLiveData<List<CourseEntity>> courseResults = new MutableLiveData<>();
        remoteDataSource.getAllCourse(courseResponses -> {
            ArrayList<CourseEntity> courseList = new ArrayList<>();
            for (CourseResponse response : courseResponses) {
                CourseEntity course = new CourseEntity(response.getId(),
                        response.getTitle(),
                        response.getDescription(),
                        response.getDate(),
                        false,
                        response.getImagePath());

                courseList.add(course);
            }
            courseResults.postValue(courseList);
        });
        return courseResults;
    }

    @Override
    public LiveData<CourseEntity> getCourseWithModule(final String courseId) {
        MutableLiveData<CourseEntity> courseResults = new MutableLiveData<>();
        remoteDataSource.getAllCourse(courseResponses -> {
            CourseEntity course = null;
            for (CourseResponse response : courseResponses) {
                if (response.getId().equals(courseId)) {
                    course = new CourseEntity(response.getId(),
                            response.getTitle(),
                            response.getDescription(),
                            response.getDate(),
                            false,
                            response.getImagePath());
                }

            }
            courseResults.postValue(course);
        });
        return courseResults;
    }

    @Override
    public LiveData<List<ModuleEntity>> getAllModuleByCourse(String courseId) {
        MutableLiveData<List<ModuleEntity>> modulesResults = new MutableLiveData<>();
        remoteDataSource.getModules(courseId, moduleResponses -> {
            ArrayList<ModuleEntity> moduleList = new ArrayList<>();
            for (ModuleResponse response : moduleResponses) {
                ModuleEntity course = new ModuleEntity(response.getModuleId(),
                        response.getCourseId(),
                        response.getTitle(),
                        response.getPosition(),
                        false);
                moduleList.add(course);
            }
            modulesResults.postValue(moduleList);
        });
        return modulesResults;
    }

    @Override
    public LiveData<List<CourseEntity>> getBookmarkedCourse() {
        MutableLiveData<List<CourseEntity>> courseResults = new MutableLiveData<>();
        remoteDataSource.getAllCourse(courseResponses -> {
            ArrayList<CourseEntity> courseList = new ArrayList<>();
            for (CourseResponse response : courseResponses) {
                CourseEntity course = new CourseEntity(response.getId(),
                        response.getTitle(),
                        response.getDescription(),
                        response.getDate(),
                        false,
                        response.getImagePath());

                courseList.add(course);
            }
            courseResults.postValue(courseList);
        });
        return courseResults;
    }

    @Override
    public LiveData<ModuleEntity> getContent(String courseId, String moduleId) {
        MutableLiveData<ModuleEntity> moduleResults = new MutableLiveData<>();
        remoteDataSource.getModules(courseId, moduleResponses -> {
            ModuleEntity module;
            for (ModuleResponse response : moduleResponses) {
                if (response.getModuleId().equals(moduleId)) {
                    module = new ModuleEntity(response.getModuleId(),
                            response.getCourseId(),
                            response.getTitle(),
                            response.getPosition(),
                            false);
                    remoteDataSource.getContent(moduleId, contentResponse -> {
                        module.contentEntity = new ContentEntity(contentResponse.getContent());
                        moduleResults.postValue(module);
                    });
                    break;
                }
            }
        });
        return moduleResults;
    }
}