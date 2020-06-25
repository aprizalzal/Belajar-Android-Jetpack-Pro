package com.application.academy.ui.reader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.application.academy.R;
import com.application.academy.ui.reader.content.ModuleContentFragment;
import com.application.academy.ui.reader.list.ModuleListFragment;
import com.application.academy.viewmodel.ViewModelFactory;

/*CourseReaderActivity:
Menampilkan 2 Fragment(ModuleListFragment dan ModuleContentFragment) dan di Activity ini nanti akan ada 2 tampilan yakni untuk ukuran layar yang besar dan kecil.*/

public class CourseReaderActivity extends AppCompatActivity implements CourseReaderCallback {

    public static final String EXTRA_COURSE_ID = "extra_course_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_reader);
        /*
        Ketika Activity atau Fragment membutuhkan ViewModel, maka akan memanggil kelas Factory untuk membuat ViewModel.
        */
        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        /*
        Pemanggilan ViewModel:
        */
        CourseReaderViewModel viewModel = new ViewModelProvider(this,factory).get(CourseReaderViewModel.class);
        Bundle bundle = getIntent().getExtras();
        if (bundle !=null){
            String courseId = bundle.getString(EXTRA_COURSE_ID);
            if (courseId !=null){
                viewModel.setSelectedCourse(courseId);
                populateFragment();
            }
        }
    }

    private void populateFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ModuleListFragment.TAG);
        if (fragment == null){
            fragment = ModuleListFragment.newInstance();
            fragmentTransaction.add(R.id.frame_container,fragment,ModuleListFragment.TAG);
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    public void moveTo(int position, String moduleId) {
        Fragment fragment = ModuleContentFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_container,fragment,ModuleContentFragment.TAG).addToBackStack(null).commit();

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <=1){
            finish();
        }else {
            super.onBackPressed();
        }
    }
}