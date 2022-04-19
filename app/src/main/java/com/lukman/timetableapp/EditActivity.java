package com.lukman.timetableapp;

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

public class EditActivity extends AppCompatActivity {
    public static final String LOG_TAG = EditActivity.class.getSimpleName();
    public TextInputEditText cTitle, cCode, cUnit, cNOS;
    public TextInputLayout layout_title, layout_code, layout_unit, layout_Nos;
    public Button materialButton;
    String courseTitleString, courseCodeString,courseUnitString, courseNOSString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        layout_title = findViewById(R.id.layoutctitle);
        layout_title.setHint("Edit Course title");
        layout_code = findViewById(R.id.layoutcode);
        layout_code.setHint("Edit Course Code");
        layout_unit = findViewById(R.id.layoutunit);
        layout_unit.setHint("Edit Course Unit");
        layout_Nos = findViewById(R.id.layoutnoofstd);
        layout_Nos.setHint("Edit the Number of Student");
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
        }else if(!validate(courseUnitString, cUnit, layout_unit)){
            return;
        }else if (!validate(courseNOSString, cNOS, layout_Nos)){
            return;
        }
        Toast.makeText(getApplicationContext(), courseUnitString + "\n" + courseCodeString, Toast.LENGTH_LONG).show();


    }
}