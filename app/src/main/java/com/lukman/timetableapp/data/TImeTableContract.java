package com.lukman.timetableapp.data;

import android.provider.BaseColumns;

public final class TImeTableContract {
  public static final class DepartmentEntry{
    public static final String TABLE_NAME = "Department";

    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_DEPT_NAME = "Name";


  }
  public static final class CourseEntry implements BaseColumns{
    public static final  String TABLE_NAME = "Course";

    public static final  String _ID = BaseColumns._ID;
    public static final  String COLUMN_COURSE_TITLE = "CTitle";
    public static final  String COLUMN_COURSE_CODE = " CCode";
    public static final  String COLUMN_COURSE_UNIT = " CUnit";
    public static final  String COLUMN_NO_OF_STUDENT = " NoofStd";
  }
  public static final class VenueEntry implements BaseColumns{
    public static final String TABLE_NAME = "Venue";

    public static final String _ID = BaseColumns._ID;
    public static final String COLUMN_VENUE_NAME = "VName";
    public static final String COLUMN_VENUE_LOCATION = "Location";
    public static final String COLUMN_VENUE_DESC = "Description";

  }
  public static final class TimeEntry implements BaseColumns{
    public static final String TABLE_NAME = "Time";

    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_START_TIME = "StartTime";
    public static final String COLUMN_STOP_TIME = "StopTime";
  }
  public static final class ExamScheduleEntry implements BaseColumns{
    public static final String TABLE_NAME = "ExamSchedule";

    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_EXAM_DATE = "Date";
    public static final String COLUMN_COURSE = "Course";
    public static final String COLUMN_EXAM_VENUE = "ExamVenue";
    public static final String COLUMN_EXAM_TIME = "ExamTime";
    public static final String COLUMN_EXAM_SEMESTER = "Semester";
    public static final String COLUMN_EXAM_SESSION = "Session";
  }
}
