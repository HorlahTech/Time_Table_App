package com.lukman.timetableapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.lukman.timetableapp.data.TImeTableContract;
import com.lukman.timetableapp.data.TimeTabledbHelper;

import java.util.ArrayList;

public class ViewExamTime extends AppCompatActivity {
    EditTimeAdapter editTimeAdapter;
    ArrayList<TimeObject> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exam_time);
        RecyclerView timeRecycler = findViewById(R.id.TimeRecycler);
        populateTimeLise();
        editTimeAdapter = new EditTimeAdapter(this, list);
        timeRecycler.setAdapter(editTimeAdapter);
        timeRecycler.setItemAnimator(new DefaultItemAnimator());
        timeRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL
                , false));


    }

    private ArrayList<TimeObject> populateTimeLise() {
        list = new ArrayList<>();
        TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
        SQLiteDatabase db = timeTabledbHelper.getReadableDatabase();
        String[] Projection = {

                TImeTableContract.TimeEntry.COLUMN_ID,
                TImeTableContract.TimeEntry.COLUMN_START_TIME,
                TImeTableContract.TimeEntry.COLUMN_STOP_TIME,
        };
        timeTabledbHelper.getReadableDatabase();
        ArrayList<TimeObject> Timeitems = new ArrayList<>();

        Cursor cursor = db.query(
                TImeTableContract.TimeEntry.TABLE_NAME,
                Projection,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            int T_Id = Integer.parseInt(cursor.getString(0));
            String T_Start = cursor.getString(1);
            String T_Stop = cursor.getString(2);
            list.add(new TimeObject(T_Id, T_Start, T_Stop));
        }
        cursor.close();
        return list;

    }
}