package com.lukman.timetableapp;

import android.content.Intent;
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

public class EditVenue extends AppCompatActivity {
    public static final String LOG_TAG = EditVenue.class.getSimpleName();
    public TextInputEditText vName, vLocation, vDesc;
    public TextInputLayout layout_vname, layout_vlocation, layout_vdesc;
    public Button venueButton;
    String venueNameString, venueLocationString,venueDescString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_venue);
        Intent ii = getIntent();///////////intent to get value from edit button
        String v__IdData = ii.getStringExtra("id");
        String v__NameData = ii.getStringExtra("course title");
        String v__LocationData = ii.getStringExtra("course code");
        String v__DescriptionData = ii.getStringExtra("course unit");
//        String c__NumberOfData = ii.getStringExtra("course numberOfStudent");
        ///////////////////////////////
        layout_vname = findViewById(R.id.layoutvname);
        layout_vname.setHint("Edit Venue Name");///////venue Name layout hint
        layout_vlocation = findViewById(R.id.layoutvlocation);
        layout_vlocation.setHint("Edit Venue Location");/////// venue location  layout hint
        layout_vdesc = findViewById(R.id.layoutvdesc);
        layout_vdesc.setHint("Edit Description");///////venue Description  layout hint
///////////////////////////////////////////////////////////////////
        vName = findViewById(R.id.venuename);
        vName.setText(v__NameData);
        // courseTitleString = cTitle.getText().toString().trim();
        vLocation = findViewById(R.id.venuelocation);
        vLocation.setText(v__LocationData);
        //  courseCodeString = cCode.getText().toString().trim();
        vDesc = findViewById(R.id.venuedesc);
        vDesc.setText(v__DescriptionData);

        //  courseUnitString = cUnit.getText().toString().trim();

        venueButton = findViewById(R.id.venuesubmit);
        venueButton.setOnClickListener(new View.OnClickListener() {
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
        venueNameString = vName.getText().toString().trim() +"";

        venueLocationString = vLocation.getText().toString().trim() +"";

        venueDescString = vDesc.getText().toString().trim() +"";


        if (!validate(venueNameString, vName, layout_vname)){
            return;
        }else if(!validate(venueLocationString, vLocation, layout_vlocation)){
            return;
        }else if(!validate(venueDescString, vDesc, layout_vdesc)){
            return;
        }
        Toast.makeText(getApplicationContext(), venueNameString + "\n" + venueLocationString, Toast.LENGTH_LONG).show();


    }
}