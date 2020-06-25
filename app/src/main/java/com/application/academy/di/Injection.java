package com.application.academy.di;

import android.content.Context;

import com.application.academy.data.source.AcademyRepository;
import com.application.academy.data.source.remote.RemoteDataSource;
import com.application.academy.utils.JsonHelper;

public class Injection {
    public static AcademyRepository providerRepository(Context context){
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance(new JsonHelper(context));
        return AcademyRepository.getInstance(remoteDataSource);
    }
}
/*
Dengan menggunakan Injection, ViewModelFactory mampu menyediakan kebutuhan AcademyRepository.
Ketika Activity atau Fragment membutuhkan ViewModel, maka akan memanggil kelas Factory untuk membuat ViewModel.
*/
