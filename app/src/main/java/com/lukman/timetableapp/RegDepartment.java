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

public class RegDepartment extends AppCompatActivity {
    public static final String LOG_TAG = RegDepartment.class.getSimpleName();
    public TextInputEditText dName, cCode, cUnit, cNOS;
    public TextInputLayout layout_DName, layout_code, layout_unit, layout_Nos;
    public Button materialButton;
    String departmentNameString, courseCodeString,courseUnitString, courseNOSString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        layout_DName = findViewById(R.id.layoutctitle);
        layout_DName.setHint("Enter Department Name");
        layout_code = findViewById(R.id.layoutcode);
        layout_code.setVisibility(View.GONE);
        layout_unit = findViewById(R.id.layoutunit);
        layout_unit.setVisibility(View.GONE);;
        layout_Nos = findViewById(R.id.layoutnoofstd);
        layout_Nos.setVisibility(View.GONE);
        dName = findViewById(R.id.coursetitle);
        departmentNameString = dName.getText().toString().trim();
//        cCode = findViewById(R.id.coursecode);
//        courseCodeString = cCode.getText().toString().trim();
//        cUnit = findViewById(R.id.courseunit);
//        courseUnitString = cUnit.getText().toString().trim();
//        cNOS = findViewById(R.id.numberofstudent);
//        courseNOSString = cNOS.getText().toString().trim();
        materialButton = findViewById(R.id.coursesubmit);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getApplicationContext(),   courseUnitString+"\n" +courseCodeString, Toast.LENGTH_LONG).show();
                submitForm();
                dName.setText("");

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
            layout.setError("Please Enter Department");
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
        departmentNameString = dName.getText().toString().trim() +"";

        if (!validate(departmentNameString, dName, layout_DName)){
            return;
        }
//        Toast.makeText(getApplicationContext(), courseUnitString + "\n" + courseCodeString, Toast.LENGTH_LONG).show();
        insertCourse();

    }
    private void insertCourse() {
        TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
        SQLiteDatabase db = timeTabledbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TImeTableContract.DepartmentEntry.COLUMN_DEPT_NAME, departmentNameString);
//        values.put(TImeTableContract.CourseEntry.COLUMN_COURSE_CODE, courseCodeString);
//        values.put(TImeTableContract.CourseEntry.COLUMN_COURSE_UNIT, courseUnitString);
//        values.put(TImeTableContract.CourseEntry.COLUMN_NO_OF_STUDENT, courseNOSString);
        long newRowId = db.insert(TImeTableContract.DepartmentEntry.TABLE_NAME, null, values);
        Log.v("Register", "New RoW Id" +newRowId );

    }
}