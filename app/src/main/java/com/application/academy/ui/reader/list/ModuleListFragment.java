package com.application.academy.ui.reader.list;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.application.academy.R;
import com.application.academy.data.ModuleEntity;
import com.application.academy.ui.reader.CourseReaderActivity;
import com.application.academy.ui.reader.CourseReaderViewModel;
import com.application.academy.viewmodel.ViewModelFactory;

import java.util.List;

/*ModuleListFragment:
Digunakan untuk menampilkan semua Module sesuai Course yang dipilih.*/

public class ModuleListFragment extends Fragment implements MyAdapterClickListener {

    public static final String TAG = ModuleListFragment.class.getSimpleName();
    private ModuleListAdapter adapter;
    private CourseReaderActivity courseReaderCallback;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private CourseReaderViewModel viewModel;

    public ModuleListFragment() {
        // Required empty public constructor
    }

    public static ModuleListFragment newInstance(){
        return new ModuleListFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_module_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_module);
        progressBar = view.findViewById(R.id.progress_bar);

        if (getActivity() !=null){
            ViewModelFactory factory = ViewModelFactory.getInstance(requireActivity());
            viewModel = new ViewModelProvider(requireActivity(),factory).get(CourseReaderViewModel.class);
            adapter = new ModuleListAdapter(this);
            progressBar.setVisibility(View.VISIBLE);
            viewModel.getModules().observe(this, module ->{
                progressBar.setVisibility(View.GONE);
                populateRecyclerView(module);
            });
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        courseReaderCallback = ((CourseReaderActivity) context);
    }

    private void populateRecyclerView(List<ModuleEntity> modules) {
        progressBar.setVisibility(View.GONE);
        adapter.setModules(modules);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onItemClicked(int position, String moduleId) {
        courseReaderCallback.moveTo(position,moduleId);
        viewModel.setSelectedModule(moduleId);
    }
}