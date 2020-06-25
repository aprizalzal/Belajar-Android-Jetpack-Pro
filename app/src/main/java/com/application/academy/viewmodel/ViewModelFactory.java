package com.application.academy.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.application.academy.data.source.AcademyRepository;
import com.application.academy.di.Injection;
import com.application.academy.ui.academy.AcademyViewModel;
import com.application.academy.ui.bookmark.BookmarkViewModel;
import com.application.academy.ui.detail.DetailCourseViewModel;
import com.application.academy.ui.reader.CourseReaderViewModel;
/*
ViewModelFactory
Ketika Anda menghubungkan ViewModel dengan AcademyRepository, ViewModel akan membutuhkan ViewModelFactory.
Perhatikan kode berikut:
*/
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;
    private final AcademyRepository mAcademyRepository;
    public ViewModelFactory(AcademyRepository AcademyRepository) {
        this.mAcademyRepository = AcademyRepository;
    }
    public static ViewModelFactory getInstance(Context context){
        if (INSTANCE == null){
            synchronized (ViewModelFactory.class){
                if (INSTANCE == null){
                    INSTANCE = new ViewModelFactory(Injection.providerRepository(context));
                }
            }
        }
        return INSTANCE;
    }
    /*
    Method getInstance berfungsi untuk membuat kelas ViewModelFactory sebagai Singleton.
    Fungsinya yaitu supaya tercipta satu instance saja di dalam JVM, sehingga tidak memakan memori yang terbatas.
    Jadi setiap Activity memanggil ViewModelFactory, kelas itu akan membuat instance jika belum dibuat sebelumnya, lalu jika Activity B memanggil fungsi ini, kelas tersebut akan memeriksa apakah instance-nya sudah ada.
    Jika iya, akan mengembalikan instance tersebut pada Activity B, tidak membuat instance baru.

    Khusus di sistem Android, terdapat Multi-threading yang bisa menjalankan kode di thread yang berbeda-beda, sehingga bisa saja instance dibuat di thread yang berbeda.
    Untuk itulah dibutuhkan kode synchronized untuk membuat semua thread tersinkronisasi.
    Dengan cara ini, hanya satu thread yang boleh menjalankan fungsi yang sama di waktu yang sama.

    Selain itu, Kelas ViewModelFactory membutuhkan dependency-dependency yang dibutuhkan untuk memanggil AcademyRepository.
    Dalam kasus ini yaitu membutuhkan RemoteDataSource pada package com.application.academy.data.source.remote; dan
    JsonHelper pada package com.application.academy.utils;
    seperti yang terlihat pada kode berkas Injection pada package com.application.academy.di;
    */
    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AcademyViewModel.class)){
            return (T) new AcademyViewModel(mAcademyRepository);
        }else if (modelClass.isAssignableFrom(DetailCourseViewModel.class)){
            return (T) new DetailCourseViewModel(mAcademyRepository);
        }else if (modelClass.isAssignableFrom(BookmarkViewModel.class)){
            return (T) new BookmarkViewModel(mAcademyRepository);
        }else if (modelClass.isAssignableFrom(CourseReaderViewModel.class)){
            return (T) new CourseReaderViewModel(mAcademyRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class "+ modelClass.getName());
    }
    /*
    Setiap ada ViewModel dalam aplikasi dan membutuhkan AcademyRepository, Anda perlu mendaftarkan ViewModel tersebut di dalam ViewModelFactory.
    Jika tidak, ViewModelFactory tidak akan mengenali kelas ViewModel tersebut.
    */
}
