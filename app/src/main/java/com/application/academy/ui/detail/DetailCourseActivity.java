package com.application.academy.ui.detail;

import android.content.Intent;
import android.os.Bundle;

import com.application.academy.R;
import com.application.academy.data.CourseEntity;
import com.application.academy.ui.reader.CourseReaderActivity;
import com.application.academy.viewmodel.ViewModelFactory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/*DetailCourseActivity:
Menampilkan detail Course dan menampilkan list Module yang ada tiap Course-nya.
Selain itu di halaman ini juga nantinya akan ada tombol bookmark untuk menyimpan course yang Anda suka.*/

public class DetailCourseActivity extends AppCompatActivity {

    public static final String EXTRA_COURSE = "extra_course";
    private Button btnStart;
    private TextView textTitle;
    private TextView textDesc;
    private TextView textDate;
    private ImageView imagePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_course);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnStart = findViewById(R.id.btn_start);
        textTitle = findViewById(R.id.text_title);
        textDesc = findViewById(R.id.text_desc);
        textDate = findViewById(R.id.text_date);
        RecyclerView rvModule = findViewById(R.id.rv_module);
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        imagePoster = findViewById(R.id.image_poster);

        DetailCourseAdapter adapter = new DetailCourseAdapter();
        /*
        Ketika Activity atau Fragment membutuhkan ViewModel, maka akan memanggil kelas Factory untuk membuat ViewModel.
        */
        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        /*
        Pemanggilan ViewModel:
        */
        DetailCourseViewModel viewModel = new ViewModelProvider(this,factory).get(DetailCourseViewModel.class);
        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            String courseId = extras.getString(EXTRA_COURSE);
            if (courseId !=null){
                viewModel.setSelectedCourse(courseId);

                progressBar.setVisibility(View.VISIBLE);
                viewModel.getModules().observe(this, modules -> {
                    progressBar.setVisibility(View.GONE);
                    adapter.setModules(modules);
                    adapter.notifyDataSetChanged();
                });
                viewModel.getCourse().observe(this, this::populateCourse);
            }
        }

        rvModule.setNestedScrollingEnabled(false);
        rvModule.setLayoutManager(new LinearLayoutManager(this));
        rvModule.setHasFixedSize(true);
        rvModule.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvModule.getContext(), DividerItemDecoration.VERTICAL);
        rvModule.addItemDecoration(dividerItemDecoration);
    }

    private void populateCourse(CourseEntity courseEntity) {
        textTitle.setText(courseEntity.getTitle());
        textDesc.setText(courseEntity.getDescription());
        textDate.setText(getResources().getString(R.string.deadline_date,courseEntity.getDeadline()));
        Glide.with(this).load(courseEntity.getImagePath()).apply(RequestOptions.placeholderOf(R.drawable.ic_loading)).error(R.drawable.ic_error).into(imagePoster);
        btnStart.setOnClickListener(v ->{
            Intent intent = new Intent(DetailCourseActivity.this, CourseReaderActivity.class);
            intent.putExtra(CourseReaderActivity.EXTRA_COURSE_ID,courseEntity.getCourseId());
            startActivity(intent);
        });
    }
}