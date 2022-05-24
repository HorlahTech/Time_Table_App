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
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lukman.timetableapp.data.TImeTableContract;
import com.lukman.timetableapp.data.TimeTabledbHelper;

import java.util.ArrayList;


public class ViewVenue extends AppCompatActivity {


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
            TImeTableContract.VenueEntry._ID,
            TImeTableContract.VenueEntry.COLUMN_VENUE_NAME,
            TImeTableContract.VenueEntry.COLUMN_VENUE_LOCATION,
            TImeTableContract.VenueEntry.COLUMN_VENUE_DESC
    };
    timeTabledbHelper.getReadableDatabase();
    ArrayList<CourseData> saveArrayData = new ArrayList<>();

    Cursor cursor = db.query(
            TImeTableContract.VenueEntry.TABLE_NAME,
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
            String v_Name = cursor.getString(1);
            String v_Location = cursor.getString(2);
            String v_Desc = cursor.getString(3);

            saveArrayData.add(new CourseData(id,v_Name, v_Location, v_Desc));
            cursor.moveToNext();
        } while (!cursor.isAfterLast());
    }
    cursor.close();
    return saveArrayData;
}
}