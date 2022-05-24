package com.lukman.timetableapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.lukman.timetableapp.data.TImeTableContract;
import com.lukman.timetableapp.data.TimeTabledbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class SetExamTime extends AppCompatActivity {
  LinearLayout tstart, tstop;
  TextView txtStart, txtStop;
  Button btn ;
  String start, stop;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_set_exam_time);
    txtStart = findViewById(R.id.txtstart);
    start = txtStart.getText().toString();
    txtStop = findViewById(R.id.txtstop);
    stop = txtStop.getText().toString();
    tstart = findViewById(R.id.tstart);

    tstop = findViewById(R.id.tstop);
    btn = findViewById(R.id.timesubmit);
    tstart.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int mins = calendar.get(Calendar.MINUTE);
        TimePickerDialog  onTimeSetListener = new TimePickerDialog(SetExamTime.this,  new TimePickerDialog.OnTimeSetListener() {
          @Override
          public void onTimeSet(TimePicker timePicker, int selectedhour, int selectedminute) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, selectedhour);
            c.set(Calendar.MINUTE, selectedminute);
            c.setTimeZone(TimeZone.getDefault());
            SimpleDateFormat format = new SimpleDateFormat("k:mm a");
            String time = format.format(c.getTime());
            txtStart.setText(time);



          }
        }, hours, mins, false);
        onTimeSetListener.setTitle("Select Time");
        onTimeSetListener.show();

      }
    });
    tstop.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int mins = calendar.get(Calendar.MINUTE);
        TimePickerDialog  onTimeSetListener = new TimePickerDialog(SetExamTime.this,  new TimePickerDialog.OnTimeSetListener() {
          @Override
          public void onTimeSet(TimePicker timePicker, int selectedhour, int selectedminute) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, selectedhour);
            c.set(Calendar.MINUTE, selectedminute);
            c.setTimeZone(TimeZone.getDefault());
            SimpleDateFormat format = new SimpleDateFormat("k:mm a");
            String time = format.format(c.getTime());
            txtStop.setText(time);



          }
        }, hours, mins, false);
        onTimeSetListener.setTitle("Select Time");
        onTimeSetListener.show();

      }
    });
    btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        insertTime();

      }
    });
  }
  private void insertTime() {

    String tSTart = txtStart.getText().toString();
    String tStop = txtStop.getText().toString();
    if (tSTart == start || tStop == stop){
      Toast.makeText(SetExamTime.this, "Enter Time to Start and Time to Stop the Exam", Toast.LENGTH_LONG).show();
    }else {
      Toast.makeText(SetExamTime.this, tSTart +"time to Start is  Submitted: " +  "\n" + tStop +"Time to Stop is Submitted "  , Toast.LENGTH_SHORT).show();
      TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
      SQLiteDatabase db = timeTabledbHelper.getWritableDatabase();
      ContentValues values = new ContentValues();
      values.put(TImeTableContract.TimeEntry.COLUMN_START_TIME, tSTart);
      values.put(TImeTableContract.TimeEntry.COLUMN_STOP_TIME, tStop);
      long newRowId = db.insert(TImeTableContract.TimeEntry.TABLE_NAME, null, values);
      Log.v("Register", "New RoW Id" + newRowId);
      txtStart.setText("Start Time");
      txtStop.setText("Stop Time");
    } }
}