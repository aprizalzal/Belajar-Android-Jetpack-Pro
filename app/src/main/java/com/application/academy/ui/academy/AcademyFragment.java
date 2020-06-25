package com.application.academy.ui.academy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.application.academy.R;
import com.application.academy.viewmodel.ViewModelFactory;

/*AcademyFragment:
Digunakan untuk menampilkan semua Course.*/

public class AcademyFragment extends Fragment {
    private RecyclerView rvCourse;
    private ProgressBar progressBar;

    public AcademyFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.academy_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCourse = view.findViewById(R.id.rv_academy);
        progressBar = view.findViewById(R.id.progress_bar);

        if (getActivity() !=null){
            /*
            Ketika Activity atau Fragment membutuhkan ViewModel, maka akan memanggil kelas Factory untuk membuat ViewModel.
            */
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
            /*
            Pemanggilan ViewModel:
            */
            AcademyViewModel viewModel = new ViewModelProvider(this, factory).get(AcademyViewModel.class);
            AcademyAdapter adapter = new AcademyAdapter();
            progressBar.setVisibility(View.VISIBLE);
            viewModel.getCourse().observe(this, courses -> {
                progressBar.setVisibility(View.GONE);
                adapter.setCourses(courses);
                adapter.notifyDataSetChanged();

            });

            rvCourse.setLayoutManager(new LinearLayoutManager(getContext()));
            rvCourse.setHasFixedSize(true);
            rvCourse.setAdapter(adapter);
        }
    }
}