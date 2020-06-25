package com.application.academy.ui.reader.content;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.application.academy.R;
import com.application.academy.data.ModuleEntity;
import com.application.academy.ui.reader.CourseReaderViewModel;
import com.application.academy.viewmodel.ViewModelFactory;

/*ModuleContentFragment:
Menampilkan Content dari Module yang dipilih.*/

public class ModuleContentFragment extends Fragment {
    public static final String TAG = ModuleContentFragment.class.getSimpleName();
    private WebView webView;
    private ProgressBar progressBar;

    public ModuleContentFragment() {
        // Required empty public constructor
    }

    public static ModuleContentFragment newInstance() {
        return new ModuleContentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_module_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = view.findViewById(R.id.web_view);
        progressBar = view.findViewById(R.id.progress_bar);

        if (getActivity() !=null) {
            /*
            Ketika Activity atau Fragment membutuhkan ViewModel, maka akan memanggil kelas Factory untuk membuat ViewModel.
            */
            ViewModelFactory factory = ViewModelFactory.getInstance(requireActivity());
            /*
            Pemanggilan ViewModel:
            Untuk pemanggilan ViewModel antara Activity dengan Fragment itu sama.
            Yang membedakan ketika Fragment akan menggunakan ViewModel yang ada pada Activity (shared ViewModel).
            */
            CourseReaderViewModel viewModel = new ViewModelProvider(requireActivity(),factory).get(CourseReaderViewModel.class);
            /*
            Jadi this diganti dengan requireActivity() untuk menghubungkan Fragment dengan ViewModel yang dipakai di Activity.
            */
            progressBar.setVisibility(View.VISIBLE);
            viewModel.getSelectedModule().observe(this, module -> {
                progressBar.setVisibility(View.GONE);
                populateWebView(module);

            });
        }
    }

    private void populateWebView(ModuleEntity module) {
        webView.loadData(module.contentEntity.getContent(), "text/html", "UTF-8");
    }
}