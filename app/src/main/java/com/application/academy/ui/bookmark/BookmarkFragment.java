package com.application.academy.ui.bookmark;

import androidx.core.app.ShareCompat;
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
import com.application.academy.data.CourseEntity;
import com.application.academy.utils.DataDummy;
import com.application.academy.viewmodel.ViewModelFactory;

import java.util.List;

/*BookmarkFragment:
Digunakan untuk menampilkan semua Course yang sudah Anda Bookmark.*/

public class BookmarkFragment extends Fragment implements BookmarkFragmentCallback {
    private RecyclerView rvBookmark;
    private ProgressBar progressBar;

    public BookmarkFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bookmark_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvBookmark = view.findViewById(R.id.rv_bookmark);
        progressBar = view.findViewById(R.id.progress_bar);

        if (getActivity() !=null){
            /*
            Ketika Activity atau Fragment membutuhkan ViewModel, maka akan memanggil kelas Factory untuk membuat ViewModel.
            */
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
            /*
            Pemanggilan ViewModel:
            */
            BookmarkViewModel viewModel = new ViewModelProvider(this, factory).get(BookmarkViewModel.class);
            BookmarkAdapter adapter = new BookmarkAdapter(this);
            progressBar.setVisibility(View.VISIBLE);
            viewModel.getCourse().observe(this, courses -> {
                progressBar.setVisibility(View.GONE);
                adapter.setCourses(courses);
                adapter.notifyDataSetChanged();
            });

            rvBookmark.setLayoutManager(new LinearLayoutManager(getContext()));
            rvBookmark.setHasFixedSize(true);
            rvBookmark.setAdapter(adapter);
        }
    }

    @Override
    public void onShareClick(CourseEntity course) {
        if (getActivity() !=null){
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder.from(getActivity()).setType(mimeType).setChooserTitle("Bagikan aplikasi ini sekarang").setText(getResources().getString(R.string.share_text,course.getTitle())).startChooser();
        }
    }
}