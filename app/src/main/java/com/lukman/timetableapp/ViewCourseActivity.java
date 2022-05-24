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


@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_course);
    ListView recyclerView = findViewById(R.id.recycler);
    //recyclerView.setLayoutManager( new LinearLayoutManager(this));
    // recyclerView.setHasFixedSize(true);


    ArrayList<CourseData> courseData = displayCourseData();
    if (courseData.size() > 0) {
        CourseAdapter Adapter = new CourseAdapter(this, courseData);
//            CourseDataAdapter courseDataAdapter = new CourseDataAdapter(courseData, this);
//            recyclerView.setAdapter(courseDataAdapter);
        recyclerView.setAdapter( Adapter);
    } else {
        Toast.makeText(this, "Empty", Toast.LENGTH_LONG).show();
    }

}

private ArrayList<CourseData> displayCourseData() {
    int id;

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
            null
    );
    if (cursor.moveToFirst()) {
        do {
            id = Integer.parseInt(cursor.getString(0));
            String c_Title = cursor.getString(1);
            String c_Code = cursor.getString(2);
            String c_Unit = cursor.getString(3);
            String c_NoOfStd = cursor.getString(4);
            saveArrayData.add(new CourseData(id, c_Title, c_Code, c_Unit, c_NoOfStd));
            cursor.moveToNext();
        } while (!cursor.isAfterLast());
    }
    cursor.close();
    return saveArrayData;
}
}