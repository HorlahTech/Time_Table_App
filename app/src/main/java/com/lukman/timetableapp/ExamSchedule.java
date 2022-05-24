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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExamSchedule extends AppCompatActivity {

  String[] Semesteritems = {"First Semester", "Second Semester"};
  LinearLayout linearExamDate;
  TextView exam_day;
  TextInputEditText exam_year;
  TextInputLayout layoutexamsession, layoutexamsemester, layoutexamvenue, layoutexamcourse, layoutexamtime;
  AutoCompleteTextView exam_time, course, exam_venue, exam_semester;
  ArrayAdapter<TimeObject> examTimeItems;
  ArrayAdapter<String> courseItems;
  ArrayAdapter<String> examVenueItems;
  ArrayAdapter<String> examSemesterItems;
  MaterialButton materialButton ;
  SQLiteDatabase db;
  ContentValues values;
  String  e_year, cDate, venue_item2, course_item1, time_item4, semester_item3, fExamDay;
  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_exam_schedule);
    linearExamDate = findViewById(R.id.linear_exam_date);
    exam_year = findViewById(R.id.exam_year);
    layoutexamsession = findViewById(R.id.layoutexamsession);
    exam_year.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        materialButton.setEnabled(false);
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if (pattern(exam_year.getText().toString())){
          materialButton.setEnabled(true);
        }else{
          materialButton.setEnabled(false);
          exam_year.setError("Invalid Input");

        }
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });

    TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
    db = timeTabledbHelper.getWritableDatabase();
    values = new ContentValues();

    exam_day = findViewById(R.id.exam_date);
    String exam_day_emp = exam_day.getText().toString();
    DateTask dateTask = new DateTask();
    dateTask.execute();

    course = findViewById(R.id.course);
    CourseTask courseTask = new CourseTask();
    courseTask.execute();

    exam_venue = findViewById(R.id.exam_venue);
    VenuesTask venuesTask = new VenuesTask();
    venuesTask.execute();

    exam_semester = findViewById(R.id.exam_semester);

    examSemesterItems = new ArrayAdapter<String>(this, R.layout.dropdown_list, Semesteritems);
    exam_semester.setAdapter(examSemesterItems);
    exam_semester.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        semester_item3 = adapterView.getItemAtPosition(position).toString();
//                values.put(TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SEMESTER, semester_item3);

      }
    });
    exam_time = findViewById(R.id.exam_time);
    TimeTask timeTask = new TimeTask();
    timeTask.execute();

    materialButton = findViewById(R.id.exam_Schedule_submit);
    materialButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // String  fExamDay = exam_day.getText().toString();
        if (fExamDay == exam_day_emp  ||  time_item4 == null || course_item1 == null || venue_item2 == null || semester_item3 == null){
          Toast.makeText(ExamSchedule.this, "Fields can not be empty", Toast.LENGTH_LONG).show();


        }else {

          insertExamData();
          exam_day.setText(exam_day_emp);
          course.setText("");
          exam_venue.setText("");
          exam_time.setText("");
          exam_semester.setText("");
          exam_year.setText("");

        }
      }
    });
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
      layout.setError("Field Cannot be empty");
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
    e_year = exam_year.getText().toString();
    if (!validate(e_year, exam_year, layoutexamsession)) {
      return;
    }
//        else if (!validate(time_item4, exam_time, layoutexamsemester)) {
//            return;
//        } else if (!validate(course_item1, course, layoutexamvenue)) {
//            return;
//        } else if (!validate(venue_item2, exam_venue, layoutexamcourse)) {
//            return;
//        } else if (!validate(semester_item3, exam_semester, layoutexamtime)) {
//            return;
//        }
    Toast.makeText(ExamSchedule.this, "values are" + values, Toast.LENGTH_LONG).show();
    values.put(TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_DATE, fExamDay);
    values.put(TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_TIME, time_item4);
    values.put(TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_VENUE, venue_item2);
    values.put(TImeTableContract.ExamScheduleEntry.COLUMN_COURSE, course_item1);
    values.put(TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SEMESTER, semester_item3);
    e_year = exam_year.getText().toString();
    values.put(TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SESSION, e_year);
    long newRowId = db.insert(TImeTableContract.ExamScheduleEntry.TABLE_NAME, null, values);
    Log.v("ExamSchedule", "New RoW Id " + newRowId);

  }
  public  boolean pattern(String fildInput){
    Pattern p = Pattern.compile("[0-9]{4}[/]{1}[0-9]{4}");
    Matcher m = p.matcher(fildInput);
    return m.matches();
  }
  public class TimeTask extends AsyncTask< Void, Void, ArrayList<TimeObject>>{



    @Override
    protected ArrayList<TimeObject> doInBackground(Void... voids) {
      TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(ExamSchedule.this);
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

    @Override
    public void onPostExecute(ArrayList<TimeObject> v) {
      //examTimeItems = new ArrayAdapter<TimeObject>(this, R.layout.dropdown_list, displayTimeList());
      examTimeItems = new TimeAdapter(ExamSchedule.this, v);
      exam_time.setAdapter(examTimeItems);
      exam_time.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
          time_item4 = adapterView.getItemAtPosition(position).toString();

          Toast.makeText(getApplicationContext(), "Item: " + time_item4, Toast.LENGTH_SHORT).show();
        }

      });
    }
  }
  public class CourseTask extends AsyncTask< Void, Void, ArrayList<String>>{



    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
      TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(ExamSchedule.this);
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

    @Override
    public void onPostExecute(ArrayList<String> v) {
      courseItems = new ArrayAdapter<String>(ExamSchedule.this, R.layout.dropdown_list, v);
      course.setAdapter(courseItems);
      course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
          course_item1 = adapterView.getItemAtPosition(position).toString();


        }
      });
    }
  }
  public class VenuesTask extends AsyncTask< Void, Void, ArrayList<String>>{



    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
      TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(ExamSchedule.this);
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



    @Override
    public void onPostExecute(ArrayList<String> v) {
      examVenueItems = new ArrayAdapter<String>(ExamSchedule.this, R.layout.dropdown_list, v);
      exam_venue.setAdapter(examVenueItems);
      exam_venue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
          venue_item2 = adapterView.getItemAtPosition(position).toString();

        }
      });
    }
  }
  public class DateTask extends AsyncTask<Void, Void,String>{
    //   String cDate;

//            @Override
//            protected void onPreExecute() {
//
//            }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected String doInBackground(Void... voids) {
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
              cDate = DateFormat.getDateInstance(DateFormat.FULL).format(cale.getTime());
              exam_day.setText(cDate);
              fExamDay = exam_day.getText().toString();

            }
          }, year, month, day);
          // datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
          datePickerDialog.show();
        }
      });

      return cDate;
    }
  }

}