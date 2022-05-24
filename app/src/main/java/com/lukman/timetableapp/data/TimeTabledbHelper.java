package com.lukman.timetableapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.lukman.timetableapp.EditActivity;

public class TimeTabledbHelper extends SQLiteOpenHelper {
  public static final String LOG_TAG = EditActivity.class.getSimpleName();
  public static final String DATABASE_NAME = "TimeTable.db";
  public static final int DATABASE_VERSION = 3;

  public TimeTabledbHelper(@Nullable Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {

    String SQL_CREATE_COURSE_ENTRIES = "CREATE TABLE " + TImeTableContract.CourseEntry.TABLE_NAME +"("
            + TImeTableContract.CourseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TImeTableContract.CourseEntry.COLUMN_COURSE_TITLE + " TEXT NOT NULL, "
            + TImeTableContract.CourseEntry.COLUMN_COURSE_CODE + " TEXT, "
            + TImeTableContract.CourseEntry.COLUMN_COURSE_UNIT + " TEXT, "
            + TImeTableContract.CourseEntry.COLUMN_NO_OF_STUDENT + " INTEGER NOT NULL DEFAULT 0 " + ");";

    String SQL_CREATE_VENUE_ENTRIES = "CREATE TABLE " + TImeTableContract.VenueEntry.TABLE_NAME +"("
            + TImeTableContract.VenueEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TImeTableContract.VenueEntry.COLUMN_VENUE_NAME+ " TEXT NOT NULL,"
            + TImeTableContract.VenueEntry.COLUMN_VENUE_LOCATION + " TEXT, "
            + TImeTableContract.VenueEntry.COLUMN_VENUE_DESC + " TEXT " + ");";


    String SQL_CREATE_TIME_ENTRIES = "CREATE TABLE " + TImeTableContract.TimeEntry.TABLE_NAME +"("
            + TImeTableContract.TimeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TImeTableContract.TimeEntry.COLUMN_START_TIME + " TEXT NOT NULL, "
            + TImeTableContract.TimeEntry.COLUMN_STOP_TIME + " TEXT NOT NULL " + ");";

    String SQL_CREATE_DEPARTMENT_ENTRIES = "CREATE TABLE " + TImeTableContract.DepartmentEntry.TABLE_NAME +"("
            + TImeTableContract.DepartmentEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TImeTableContract.DepartmentEntry.COLUMN_DEPT_NAME+ " TEXT NOT NULL " + ");";

    String SQL_CREATE_EXAMSCHEDULE_ENTRIES = "CREATE TABLE " + TImeTableContract.ExamScheduleEntry.TABLE_NAME +"("
            + TImeTableContract.ExamScheduleEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_DATE+ " TEXT NOT NULL, "
            + TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_TIME+ " TEXT NOT NULL, "
            + TImeTableContract.ExamScheduleEntry.COLUMN_COURSE+ " TEXT NOT NULL, "
            + TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_VENUE+ " TEXT NOT NULL, "
            + TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SEMESTER+ " TEXT NOT NULL, "
            + TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SESSION+ " TEXT NOT NULL "
            + ");";

    sqLiteDatabase.execSQL(SQL_CREATE_COURSE_ENTRIES);
    sqLiteDatabase.execSQL(SQL_CREATE_VENUE_ENTRIES);
    sqLiteDatabase.execSQL(SQL_CREATE_TIME_ENTRIES);
    sqLiteDatabase.execSQL(SQL_CREATE_DEPARTMENT_ENTRIES);
    sqLiteDatabase.execSQL(SQL_CREATE_EXAMSCHEDULE_ENTRIES);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    String SQL_DELETE_COURSE_ENTRIES = "DROP TABLE IF EXISTS " + TImeTableContract.CourseEntry.TABLE_NAME;
    String SQL_DELETE_VENUE_ENTRIES = "DROP TABLE IF EXISTS " + TImeTableContract.VenueEntry.TABLE_NAME;
    String SQL_DELETE_TIME_ENTRIES = "DROP TABLE IF EXISTS " + TImeTableContract.TimeEntry.TABLE_NAME;
    String SQL_DELETE_DEPARTMRNT_ENTRIES = "DROP TABLE IF EXISTS " + TImeTableContract.DepartmentEntry.TABLE_NAME;
    String SQL_DELETE_EXAMSCHEDULE_ENTRIES = "DROP TABLE IF EXISTS " + TImeTableContract.DepartmentEntry.TABLE_NAME;
    sqLiteDatabase.execSQL(SQL_DELETE_COURSE_ENTRIES);
    sqLiteDatabase.execSQL(SQL_DELETE_VENUE_ENTRIES);
    sqLiteDatabase.execSQL(SQL_DELETE_TIME_ENTRIES);
    sqLiteDatabase.execSQL(SQL_DELETE_DEPARTMRNT_ENTRIES);
    sqLiteDatabase.execSQL(SQL_DELETE_EXAMSCHEDULE_ENTRIES);
    onCreate(sqLiteDatabase);
  }
}
