package com.lukman.timetableapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lukman.timetableapp.data.TImeTableContract;
import com.lukman.timetableapp.data.TimeTabledbHelper;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lukman.timetableapp.data.TImeTableContract;
import com.lukman.timetableapp.data.TimeTabledbHelper;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lukman.timetableapp.data.TImeTableContract;
import com.lukman.timetableapp.data.TimeTabledbHelper;



public class ViewDepartment extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        ListView listView = findViewById(R.id.recycler);
//        listView.setLayoutManager( new LinearLayoutManager(this));
//        listView.setHasFixedSize(true);


        ArrayList<CourseData> courseData = displayVenueData();
        if (courseData.size() > 0) {
            CourseAdapter Adapter = new CourseAdapter(this, courseData);
            listView.setAdapter( Adapter);
        } else {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<CourseData> displayVenueData() {

        TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
        SQLiteDatabase db = timeTabledbHelper.getReadableDatabase();
        String[] Projection = {
                TImeTableContract.DepartmentEntry.COLUMN_ID,
                TImeTableContract.DepartmentEntry.COLUMN_DEPT_NAME,

        };
        timeTabledbHelper.getReadableDatabase();
        ArrayList<CourseData> saveArrayData = new ArrayList<>();

        Cursor cursor = db.query(
                TImeTableContract.DepartmentEntry.TABLE_NAME,
                Projection,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String DName = cursor.getString(1);

                saveArrayData.add(new CourseData(id, DName));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return saveArrayData;
    }
}