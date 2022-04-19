package com.lukman.timetableapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;





public class CourseAdapter extends ArrayAdapter<CourseData> {

    public CourseAdapter(@NonNull Context context, ArrayList<CourseData> course) {
        super(context, 0, course);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;
        if( listViewItem==null){
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_courses, parent, false);
        }
        CourseData currentWord = getItem(position);
       TextView title = listViewItem.findViewById(R.id.course_title);
        title.setText(currentWord.getTittle());
        TextView code = listViewItem.findViewById(R.id.course_code);
        TextView title_header = listViewItem.findViewById(R.id.title_header);
        TextView code_header = listViewItem.findViewById(R.id.code_header);
        TextView unit_header = listViewItem.findViewById(R.id.unit_header);
        TextView noofstd_header = listViewItem.findViewById(R.id.noofstd_header);

        LinearLayout linear_id_title = listViewItem.findViewById(R.id.linear_id_title);
        LinearLayout linear_id_code = listViewItem.findViewById(R.id.linear_id_code);
        LinearLayout linear_id_unit = listViewItem.findViewById(R.id.linear_id_unit);
        LinearLayout linear_id_noofstd = listViewItem.findViewById(R.id.linear_id_noofstd);
        if (currentWord.checkcode()){
            code.setText(currentWord.getCode());
            linear_id_code.setVisibility(View.VISIBLE);
        }else {
            linear_id_code.setVisibility(View.GONE);
            title_header.setText("Department Name:");
        }
        TextView unit = listViewItem.findViewById(R.id.course_unit);
        if (currentWord.checkUnit()){
            code.setText(currentWord.getUnit());
            linear_id_unit.setVisibility(View.VISIBLE);
        }else {

            linear_id_unit.setVisibility(View.GONE);
        }
        TextView noofstd = listViewItem.findViewById(R.id.course_noofstd);
        if (currentWord.checkNoofStd()){
            noofstd.setText(currentWord.getNoostd());
            linear_id_noofstd.setVisibility(View.VISIBLE);
        }else {
            linear_id_noofstd.setVisibility(View.GONE);
            title_header.setText("Venue Name:");
            code_header.setText("Venue Location:");
            unit_header.setText("Venue Description:");

        }
        Button button_edit = listViewItem.findViewById(R.id.edit);
        Button  button_delete = listViewItem.findViewById(R.id.delete);

        return listViewItem;
    }
}
