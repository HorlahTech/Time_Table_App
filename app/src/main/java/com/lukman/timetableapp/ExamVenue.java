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

public class ExamVenue extends AppCompatActivity {
  public static final String LOG_TAG = ExamVenue.class.getSimpleName();
  public TextInputEditText vName, vLocation, vDesc;
  public TextInputLayout layout_vname, layout_vlocation, layout_vdesc;
  public Button venueButton;
  String venueNameString, venueLocationString,venueDescString;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_exam_venue);
    layout_vname = findViewById(R.id.layoutvname);
    layout_vname.setHint("Enter Venue Name");
    layout_vlocation = findViewById(R.id.layoutvlocation);
    layout_vlocation.setHint("Enter Venue Location");
    layout_vdesc = findViewById(R.id.layoutvdesc);
    layout_vdesc.setHint("Enter Description");

    vName = findViewById(R.id.venuename);
    // courseTitleString = cTitle.getText().toString().trim();
    vLocation = findViewById(R.id.venuelocation);
    //  courseCodeString = cCode.getText().toString().trim();
    vDesc = findViewById(R.id.venuedesc);
    //  courseUnitString = cUnit.getText().toString().trim();

    venueButton = findViewById(R.id.venuesubmit);
    venueButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {


        submitForm();
//                finish();

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
    insetVenue();
    Toast.makeText(getApplicationContext(), venueNameString + "\n" + venueLocationString, Toast.LENGTH_LONG).show();
    vName.setText("");
    vLocation.setText("");
    vDesc.setText("");

  }
  private void insetVenue() {
    TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
    SQLiteDatabase db = timeTabledbHelper.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(TImeTableContract.VenueEntry.COLUMN_VENUE_NAME, venueNameString);
    values.put(TImeTableContract.VenueEntry.COLUMN_VENUE_LOCATION, venueLocationString);
    values.put(TImeTableContract.VenueEntry.COLUMN_VENUE_DESC, venueDescString);
    long newRowId = db.insert(TImeTableContract.VenueEntry.TABLE_NAME, null, values);

  }

}