package com.lukman.timetableapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lukman.timetableapp.data.TimeTabledbHelper;

import java.util.List;

public class CourseDataAdapter extends RecyclerView.Adapter<CourseDataAdapter.ViewHolder>{
    private List<CourseData> c_data;
    private Context context;
    TimeTabledbHelper timeTabledbHelper;

    public CourseDataAdapter(List<CourseData> c_data, Context context) {
        this.c_data = c_data;
        this.context = context;
        timeTabledbHelper = new TimeTabledbHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_courses, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseData courseData = c_data.get(position);

       holder.title.setText(courseData.getTittle());
       holder.code.setText(courseData.getCode());
       holder.unit.setText(courseData.getUnit());
       holder.noofstd.setText(courseData.getNoostd());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, code, unit, noofstd;
        private Button button_edit, button_delete;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.course_title);
        code = itemView.findViewById(R.id.course_code);
        unit = itemView.findViewById(R.id.course_unit);
        noofstd = itemView.findViewById(R.id.course_noofstd);
        button_edit = itemView.findViewById(R.id.edit);
        button_delete = itemView.findViewById(R.id.delete);

    }
}
}
