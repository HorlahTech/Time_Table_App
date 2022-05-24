package com.lukman.timetableapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.lukman.timetableapp.data.TImeTableContract;
import com.lukman.timetableapp.data.TimeTabledbHelper;

public class EditActivity extends AppCompatActivity {
    public static final String LOG_TAG = EditActivity.class.getSimpleName();
    public TextInputEditText cTitle, cCode, cUnit, cNOS;
    public TextInputLayout layout_title, layout_code, layout_unit, layout_Nos;
    public Button materialButton;
    String courseTitleString, courseCodeString,courseUnitString, courseNOSString;
    int c__IdData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent ii = getIntent();///////////intent to get value from edit button
        String c__IdData = ii.getStringExtra("id");
        String c__TitleData = ii.getStringExtra("course title");
        String c__CodeData = ii.getStringExtra("course code");
        String c__UnitData = ii.getStringExtra("course unit");
        String c__NumberOfData = ii.getStringExtra("course numberOfStudent");

        //////////////////////////////////////////
        layout_title = findViewById(R.id.layoutctitle);
        layout_title.setHint("Edit Course title"); ///////title layout hint
        layout_code = findViewById(R.id.layoutcode);
        layout_code.setHint("Edit Course Code");////////code layout hint
        layout_unit = findViewById(R.id.layoutunit);
        layout_unit.setHint("Edit Course Unit");///////unit layout hint
        layout_Nos = findViewById(R.id.layoutnoofstd);
        layout_Nos.setHint("Edit the Number of Student");//////NOS layout hint
        ///////////////////////////////////////////
        cTitle = findViewById(R.id.coursetitle);
        cTitle.setText(c__TitleData);
        courseTitleString = cTitle.getText().toString().trim();
////////////////////////////////////////
        cCode = findViewById(R.id.coursecode);
        cCode.setText(c__CodeData);
        courseCodeString = cCode.getText().toString().trim();
////////////////////////////////////////
        cUnit = findViewById(R.id.courseunit);
        cUnit.setText(c__UnitData);
        courseUnitString = cUnit.getText().toString().trim();
////////////////////////////////////////
        cNOS = findViewById(R.id.numberofstudent);
        cNOS.setText(c__NumberOfData);
        courseNOSString = cNOS.getText().toString().trim();
////////////////////////////////////////
        materialButton = findViewById(R.id.coursesubmit);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                submitForm();

            }
        });
    }
    /////////request focus////////
    public void requestfocus(View view){
        if (view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    public boolean validate(String input,View txtinputEdit, TextInputLayout layout){
        if(input.isEmpty()){
            layout.setError("Please Enter Course");
            requestfocus(txtinputEdit);
            return false;
        }else if (input.length()<3){
            layout.setError(" Set Minimum of 3 Character");
            return false;
        }else{
            layout.setErrorEnabled(false);
        }
        return true;
    }
    public void submitForm (){
        courseTitleString = cTitle.getText().toString().trim() +"";

        courseCodeString = cCode.getText().toString().trim() +"";

        courseUnitString = cUnit.getText().toString().trim() +"";

        courseNOSString = cNOS.getText().toString().trim()+"";

        if (!validate(courseTitleString, cTitle, layout_title)){
            return;
        }else if(!validate(courseCodeString, cCode, layout_code)){
            return;
        }
        updateCourse();
        Toast.makeText(getApplicationContext(), courseUnitString + "\n" + courseCodeString, Toast.LENGTH_LONG).show();


    }
    private void updateCourse() {
        TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
        SQLiteDatabase db = timeTabledbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TImeTableContract.CourseEntry.COLUMN_COURSE_TITLE, courseTitleString);
        values.put(TImeTableContract.CourseEntry.COLUMN_COURSE_CODE, courseCodeString);
        values.put(TImeTableContract.CourseEntry.COLUMN_COURSE_UNIT, courseUnitString);
        values.put(TImeTableContract.CourseEntry.COLUMN_NO_OF_STUDENT, courseNOSString);
        long newRowId = db.update(TImeTableContract.CourseEntry.TABLE_NAME, values, "_id = ?",
                new String[]{String.valueOf(c__IdData)});
        Log.v("Register", "New RoW Id  " +newRowId );
        finish();

    }
}