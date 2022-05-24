package com.lukman.timetableapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lukman.timetableapp.data.TImeTableContract;
import com.lukman.timetableapp.data.TimeTabledbHelper;

import java.util.ArrayList;





public class CourseAdapter extends ArrayAdapter<CourseData> {
    Context context;
    CourseData currentWord;
    public CourseAdapter(@NonNull Context context, ArrayList<CourseData> course) {
        super(context, 0, course);
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;
        if( listViewItem==null){
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_courses, parent, false);
        }
         currentWord = getItem(position);
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
            unit.setText(currentWord.getUnit());
            linear_id_unit.setVisibility(View.VISIBLE);
        }else {

            linear_id_unit.setVisibility(View.GONE);
        }
        TextView noofstd = listViewItem.findViewById(R.id.course_noofstd);
        if (currentWord.checkNoofStd()){
            noofstd.setText(currentWord.getNoostd());
            linear_id_noofstd.setVisibility(View.VISIBLE);
        }else if (!currentWord.checkNoofStd()){
            linear_id_noofstd.setVisibility(View.GONE);
            title_header.setText("Venue Name:");
            code_header.setText("Venue Location:");
            unit_header.setText("Venue Description:");

        }
        Button button_edit = listViewItem.findViewById(R.id.edit);
        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        if (linear_id_noofstd.getVisibility() == View.GONE && linear_id_unit.getVisibility() == View.VISIBLE){
                Intent i = new Intent(view.getContext(), EditVenue.class);
                i.putExtra("id",  currentWord.getId() );
                i.putExtra("course title", title.getText().toString() +" "+  currentWord.getId() );
                i.putExtra("course code", code.getText().toString());
                i.putExtra("course unit", unit.getText().toString());
                i.putExtra("course numberOfStudent", noofstd.getText().toString());
                view.getContext().startActivity(i);
            }else if (linear_id_noofstd.getVisibility() == View.VISIBLE){
            Intent ij = new Intent(view.getContext(), EditActivity.class);

           // ij.putExtra("course id", currentWord.getId() );
            ij.putExtra("id",   currentWord.getId() );
            ij.putExtra("course title", title.getText().toString() +" "+  currentWord.getId() );
            ij.putExtra("course code", code.getText().toString());
            ij.putExtra("course unit", unit.getText().toString());
            ij.putExtra("course numberOfStudent", noofstd.getText().toString());
            view.getContext().startActivity(ij);
            }else if (!currentWord.checkcode()){

        }
            }
        });
        Button  button_delete = listViewItem.findViewById(R.id.delete);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });

        return listViewItem;
    }
    private void deleteData() {
        TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(context);
        SQLiteDatabase db = timeTabledbHelper.getWritableDatabase();

        long newRowId = db.delete(TImeTableContract.CourseEntry.TABLE_NAME,  "_id = ?", new String[currentWord.getId()]);
        Log.v("Register", "New RoW Id  " +newRowId );


    }
}
