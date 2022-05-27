package com.lukman.timetableapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.lukman.timetableapp.data.TImeTableContract;
import com.lukman.timetableapp.data.TimeTabledbHelper;

public class EditDepartment extends AppCompatActivity {
    public static final String LOG_TAG = com.lukman.timetableapp.EditDepartment.class.getSimpleName();
    public TextInputEditText dTitle;
    public TextInputLayout layout_title, layout_code, layout_unit, layout_Nos;
    public Button materialButton;
    String DeptNameString;
    String D__IdData;
    public TextInputEditText dName;
    public TextInputLayout layout_DName;

    String departmentNameString;

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
        DeptNameString = dName.getText().toString().trim();

                Intent ii = getIntent();///////////intent to get value from edit button
                D__IdData = ii.getStringExtra("id");
                String c__TitleData = ii.getStringExtra("course title");
                Log.v("Register", "New RoW Id  " +D__IdData);

                //////////////////////////////////////////
                layout_title = findViewById(R.id.layoutctitle);
                layout_title.setHint("Edit Course title"); ///////title layout hint

                ///////////////////////////////////////////
             dTitle = findViewById(R.id.coursetitle);
             dTitle.setText(c__TitleData);
            DeptNameString = dTitle.getText().toString().trim();
////////////////////////////////////////

                materialButton = findViewById(R.id.coursesubmit);
                materialButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        submitForm();
                        finish();

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
                DeptNameString = dTitle.getText().toString().trim() +"";


                if (!validate(DeptNameString, dTitle, layout_title)){
                    return;

                }
                updateCourse();


            }
            private void updateCourse() {
                TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
                SQLiteDatabase db = timeTabledbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(TImeTableContract.DepartmentEntry.COLUMN_DEPT_NAME, DeptNameString);

                long newRowId = db.update(TImeTableContract.DepartmentEntry.TABLE_NAME, values, "_id = ?",
                        new String[]{D__IdData});
                Log.v("Register", "New RoW Id  " +D__IdData);


            }

    }

