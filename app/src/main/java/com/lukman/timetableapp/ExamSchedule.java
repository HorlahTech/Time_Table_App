package com.lukman.timetableapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.lukman.timetableapp.data.TImeTableContract;
import com.lukman.timetableapp.data.TimeTabledbHelper;

import java.text.DateFormat;
import java.util.ArrayList;

public class ExamSchedule extends AppCompatActivity {

    String[] Semesteritems = {"First Semester", "Second Semester"};
    LinearLayout linearExamDate;
    TextView exam_day;
    TextInputEditText exam_year;
    TextInputLayout layoutexamsession;
    AutoCompleteTextView exam_time, course, exam_venue, exam_semester;
    ArrayAdapter<TimeObject> examTimeItems;
    ArrayAdapter<String> courseItems;
    ArrayAdapter<String> examVenueItems;
    ArrayAdapter<String> examSemesterItems;
    MaterialButton materialButton ;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_schedule);
        linearExamDate = findViewById(R.id.linear_exam_date);
        exam_year = findViewById(R.id.exam_year);
        layoutexamsession = findViewById(R.id.layoutexamsession);


        exam_day = findViewById(R.id.exam_date);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        linearExamDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ExamSchedule.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        Calendar cale = Calendar.getInstance();
                        cale.set(Calendar.YEAR, year);
                        cale.set(Calendar.MONTH, month);
                        cale.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String cDate = DateFormat.getDateInstance(DateFormat.FULL).format(cale.getTime());
                        exam_day.setText(cDate);
                    }
                }, year, month, day);
                //datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
                datePickerDialog.show();
            }
        });


        course = findViewById(R.id.course);
        courseItems = new ArrayAdapter<String>(this, R.layout.dropdown_list, displayCourseList());
        course.setAdapter(courseItems);
        course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });
        exam_venue = findViewById(R.id.exam_venue);
        examVenueItems = new ArrayAdapter<String>(this, R.layout.dropdown_list, displayVenueList());
        exam_venue.setAdapter(examVenueItems);
        exam_venue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });
        exam_semester = findViewById(R.id.exam_semester);
        examSemesterItems = new ArrayAdapter<String>(this, R.layout.dropdown_list, Semesteritems);
        exam_semester.setAdapter(examSemesterItems);
        exam_semester.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });
        exam_time = findViewById(R.id.exam_time);
        //examTimeItems = new ArrayAdapter<TimeObject>(this, R.layout.dropdown_list, displayTimeList());
        examTimeItems = new TimeAdapter(ExamSchedule.this, displayTimeList());
        exam_time.setAdapter(examTimeItems);
        exam_time.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });

    materialButton = findViewById(R.id.exam_Schedule_submit);
    materialButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            insertExamData();
        }
    });
    }

    private ArrayList<String> displayVenueList() {

        TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
        SQLiteDatabase db = timeTabledbHelper.getReadableDatabase();
        String[] Projection = {

                TImeTableContract.VenueEntry.COLUMN_VENUE_NAME,
        };
        timeTabledbHelper.getReadableDatabase();
        ArrayList<String> Venueitems = new ArrayList<>();

        Cursor cursor = db.query(
                TImeTableContract.VenueEntry.TABLE_NAME,
                Projection,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            String v_Name = cursor.getString(0);
            Venueitems.add(v_Name);
        }

        cursor.close();
        return Venueitems;
    }

    private ArrayList<String> displayCourseList() {

        TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
        SQLiteDatabase db = timeTabledbHelper.getReadableDatabase();
        String[] Projection = {

                TImeTableContract.CourseEntry.COLUMN_COURSE_TITLE,
        };
        timeTabledbHelper.getReadableDatabase();
        ArrayList<String> Courseitems = new ArrayList<>();

        Cursor cursor = db.query(
                TImeTableContract.CourseEntry.TABLE_NAME,
                Projection,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            String c_Title = cursor.getString(0);
            Courseitems.add(c_Title);
        }

        cursor.close();
        return Courseitems;
    }

    ////code to display time in the drop  down from the database/////
    private ArrayList<TimeObject> displayTimeList() {

        TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
        SQLiteDatabase db = timeTabledbHelper.getReadableDatabase();
        String[] Projection = {

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
            String T_Start = cursor.getString(0);
            String T_Stop = cursor.getString(1);
            Timeitems.add(new TimeObject(T_Start, T_Stop));
        }

        cursor.close();
        return Timeitems;
    }

    /////////request focus////////
    public void requestfocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    /////Code to Validate Exam Session Input//////
    public boolean validate(String input, View txtinputEdit, TextInputLayout layout) {
        if (input.isEmpty()) {
            layout.setError("Please Enter Exam Session");
            requestfocus(txtinputEdit);
            return false;
        } else if (input.length() < 3) {
            layout.setError(" Set Minimum of 3 Character");
            return false;
        } else {
            layout.setErrorEnabled(false);
        }
        return true;
    }
//////Code to Insert Exam Schedule data to database/////
    public void insertExamData() {
        /////Code to check if the Exam Session Input is correct//////
        String e_year = exam_year.getText().toString();
        if (!validate(e_year, exam_year, layoutexamsession)) {
            return;

            TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
            SQLiteDatabase db = timeTabledbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_DATE, courseTitleString);
            values.put(TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_TIME, courseCodeString);
            values.put(TImeTableContract.ExamScheduleEntry.COLUMN_COURSE, courseUnitString);
            values.put(TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_VENUE, courseNOSString);
            values.put(TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SEMESTER, courseNOSString);
            values.put(TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SESSION, courseNOSString);
            long newRowId = db.insert(TImeTableContract.CourseEntry.TABLE_NAME, null, values);
            Log.v("Register", "New RoW Id" +newRowId );


        }
    }
}