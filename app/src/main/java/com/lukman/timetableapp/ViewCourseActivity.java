package com.lukman.timetableapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lukman.timetableapp.data.TImeTableContract;
import com.lukman.timetableapp.data.TimeTabledbHelper;

import java.util.ArrayList;

public class ViewCourseActivity extends AppCompatActivity {
    ListView recyclerView;
    CourseAdapter adapter;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_course);
     recyclerView = findViewById(R.id.recycler);
    //recyclerView.setLayoutManager( new LinearLayoutManager(this));
    // recyclerView.setHasFixedSize(true);


    ArrayList<CourseData> courseData = displayCourseData();
    if (courseData.size() > 0) {
         adapter = new CourseAdapter(this, courseData);
         adapter.notifyDataSetChanged();
//            CourseDataAdapter courseDataAdapter = new CourseDataAdapter(courseData, this);
//            recyclerView.setAdapter(courseDataAdapter);
        recyclerView.setAdapter( adapter);
        recyclerView.invalidateViews();
    } else {
        Toast.makeText(this, "Empty", Toast.LENGTH_LONG).show();
    }

}

private ArrayList<CourseData> displayCourseData() {


    TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
    SQLiteDatabase db = timeTabledbHelper.getReadableDatabase();
    String[] Projection = {
            TImeTableContract.CourseEntry._ID,
            TImeTableContract.CourseEntry.COLUMN_COURSE_TITLE,
            TImeTableContract.CourseEntry.COLUMN_COURSE_CODE,
            TImeTableContract.CourseEntry.COLUMN_COURSE_UNIT,
            TImeTableContract.CourseEntry.COLUMN_NO_OF_STUDENT
    };
    timeTabledbHelper.getReadableDatabase();
    ArrayList<CourseData> saveArrayData = new ArrayList<>();

    Cursor cursor = db.query(
            TImeTableContract.CourseEntry.TABLE_NAME,
            Projection,
            null,
            null,
            null,
            null,
            TImeTableContract.CourseEntry._ID + " ASC"
    );
    if (cursor.moveToFirst()) {
        do {
            int id = Integer.parseInt(cursor.getString(0));
            String c_Title = cursor.getString(1);
            String c_Code = cursor.getString(2);
            String c_Unit = cursor.getString(3);
            String c_NoOfStd = cursor.getString(4);
            saveArrayData.add(new CourseData(id, c_Title, c_Code, c_Unit, c_NoOfStd));

        } while (cursor.moveToNext());
    }
    cursor.close();
    return saveArrayData;
}

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.recycler);
        //recyclerView.setLayoutManager( new LinearLayoutManager(this));
        // recyclerView.setHasFixedSize(true);


        ArrayList<CourseData> courseData = displayCourseData();
        if (courseData.size() > 0) {
            adapter = new CourseAdapter(this, courseData);
            adapter.notifyDataSetChanged();
//            CourseDataAdapter courseDataAdapter = new CourseDataAdapter(courseData, this);
//            recyclerView.setAdapter(courseDataAdapter);
            recyclerView.setAdapter( adapter);
            recyclerView.invalidateViews();
        } else {
            Toast.makeText(this, "Empty", Toast.LENGTH_LONG).show();
        }

        Toast.makeText(getApplicationContext(), "onResume", Toast.LENGTH_SHORT).show();
    }

}