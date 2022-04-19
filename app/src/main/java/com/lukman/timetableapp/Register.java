package com.lukman.timetableapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.lukman.timetableapp.data.TImeTableContract;
import com.lukman.timetableapp.data.TimeTabledbHelper;

public class Register extends AppCompatActivity {
    public static final String LOG_TAG = Register.class.getSimpleName();
public TextInputEditText cTitle, cCode, cUnit, cNOS;
public TextInputLayout layout_title, layout_code, layout_unit, layout_Nos;
public Button materialButton;
    String courseTitleString, courseCodeString,courseUnitString, courseNOSString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        layout_title = findViewById(R.id.layoutctitle);
        layout_title.setHint("Enter Course title");
        layout_code = findViewById(R.id.layoutcode);
        layout_code.setHint("Enter Course Code");
        layout_unit = findViewById(R.id.layoutunit);
        layout_unit.setHint("Enter Course Unit");
        layout_Nos = findViewById(R.id.layoutnoofstd);
        layout_Nos.setHint("Enter the Number of Student");
       cTitle = findViewById(R.id.coursetitle);
        courseTitleString = cTitle.getText().toString().trim();
        cCode = findViewById(R.id.coursecode);
         courseCodeString = cCode.getText().toString().trim();
        cUnit = findViewById(R.id.courseunit);
         courseUnitString = cUnit.getText().toString().trim();
        cNOS = findViewById(R.id.numberofstudent);
         courseNOSString = cNOS.getText().toString().trim();
        materialButton = findViewById(R.id.coursesubmit);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getApplicationContext(),   courseUnitString+"\n" +courseCodeString, Toast.LENGTH_LONG).show();
             submitForm();
                cTitle.setText("");
                cCode.setText("");
                cUnit.setText("");
                cNOS.setText("");

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
        insertCourse();
    Toast.makeText(getApplicationContext(), courseUnitString + "\n" + courseCodeString, Toast.LENGTH_LONG).show();
    }

    private void insertCourse() {
        TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
        SQLiteDatabase db = timeTabledbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TImeTableContract.CourseEntry.COLUMN_COURSE_TITLE, courseTitleString);
        values.put(TImeTableContract.CourseEntry.COLUMN_COURSE_CODE, courseCodeString);
        values.put(TImeTableContract.CourseEntry.COLUMN_COURSE_UNIT, courseUnitString);
        values.put(TImeTableContract.CourseEntry.COLUMN_NO_OF_STUDENT, courseNOSString);
       long newRowId = db.insert(TImeTableContract.CourseEntry.TABLE_NAME, null, values);
       Log.v("Register", "New RoW Id" +newRowId );

    }

}