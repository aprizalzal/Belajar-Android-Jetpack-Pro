package com.application.academy.ui.bookmark;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.academy.R;
import com.application.academy.data.CourseEntity;
import com.application.academy.ui.detail.DetailCourseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.CourseViewHolder> {
    private final BookmarkFragmentCallback callback;
    private ArrayList<CourseEntity> listCourses = new ArrayList<>();

    BookmarkAdapter(BookmarkFragmentCallback callback){
        this.callback=callback;
    }

    public void setCourses(List<CourseEntity> courses) {
        if (listCourses == null)return;
        listCourses.clear();
        listCourses.addAll(courses);
    }

    @NonNull
    @Override
    public BookmarkAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_bookmark,parent,false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.CourseViewHolder holder, int position) {
        CourseEntity course = listCourses.get(position);
        holder.bind(course);
    }

    @Override
    public int getItemCount() {
        return listCourses.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle;
        final TextView tvDescription;
        final TextView tvDate;
        final ImageButton imaShare;
        final ImageView imgPoster;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            tvDate = itemView.findViewById(R.id.tv_item_date);
            imaShare = itemView.findViewById(R.id.img_share);
            imgPoster = itemView.findViewById(R.id.img_poster);
        }

        public void bind(CourseEntity course) {
            tvTitle.setText(course.getTitle());
            tvDescription.setText(course.getDescription());
            tvDate.setText(itemView.getResources().getString(R.string.deadline_date,course.getDeadline()));
            itemView.setOnClickListener(v ->{
                Intent intent = new Intent(itemView.getContext(), DetailCourseActivity.class);
                intent.putExtra(DetailCourseActivity.EXTRA_COURSE,course.getCourseId());
                itemView.getContext().startActivity(intent);
            });
            imaShare.setOnClickListener(v -> callback.onShareClick(course));
            Glide.with(itemView.getContext()).load(course.getImagePath()).apply(RequestOptions.placeholderOf(R.drawable.ic_loading)).error(R.drawable.ic_error).into(imgPoster);
        }
    }
}
