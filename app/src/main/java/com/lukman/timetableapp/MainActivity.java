package com.lukman.timetableapp;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    ArrayList<String> listGroup = new ArrayList<>();
    HashMap<String, ArrayList<String>> listChild = new HashMap<>();
    Expandable adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "aasdfgg");
        expandableListView = findViewById(R.id.expandable_listView);
//        expandableListView.setAdapter(adapter);
        SendIntentTask sendIntentTask = new SendIntentTask();
        sendIntentTask.execute();

//        listGroup.add(  "Department");
//        listGroup.add("Course");
//        listGroup.add("Venue");
//        listGroup.add("Time Schedule");
//        listGroup.add("Exam Scheduling");
//        listGroup.add("Settings");
//        //add Department child
//        ArrayList<String> mDepartment = new ArrayList<>();
//        mDepartment.add("Reg Department");
//        mDepartment.add("Edit Department");
//        mDepartment.add("View Department");
//        listChild.put("Department",mDepartment);
//        //add course child
//        ArrayList<String> mCourse = new ArrayList<>();
//        mCourse.add("Reg Course");
//        mCourse.add("Edit Course");
//        mCourse.add("View Course");
//        listChild.put("Course",mCourse);
//        //add Venue child
//        ArrayList<String> mVenue = new ArrayList<>();
//        mVenue.add("Reg Venue");
//        mVenue.add("Edit Venue");
//        mVenue.add("View Venue");
//        listChild.put("Venue",mVenue);
//            //add Time child
//            ArrayList<String> mTime = new ArrayList<>();
//        mTime.add("Set Time");
//        mTime.add("Edit Time");
//            listChild.put("Time Schedule",mTime);
//            //add course child
//            ArrayList<String> mExamScheduling = new ArrayList<>();
//        mExamScheduling.add("Set Exam Time Table");
//        mExamScheduling.add("Edit Exam Time Table");
//        mExamScheduling.add("View Exam Time Table");
//            listChild.put("Exam Scheduling",mExamScheduling);
//            //add Settings child
//            ArrayList<String> mSettings = new ArrayList<>();
//        mSettings.add("Reg Admin");
//        mSettings.add("Edit Admin");
//            listChild.put("Settings",mSettings);
//
//
//        adapter = new Expandable(listGroup, listChild);

//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//                String sChild = String.valueOf(adapter.getChild(i, i1));
//                if (sChild == "Reg Course") {
//                    Intent regIntent = new Intent(MainActivity.this, Register.class);
//                    startActivity(regIntent);
//
//                } else if (sChild == "Edit Course") {
//                    Intent regIntent = new Intent(MainActivity.this, EditActivity.class);
//                    startActivity(regIntent);
//                } else if (sChild == "Reg Department") {
//                    Intent regIntent = new Intent(MainActivity.this, RegDepartment.class);
//                    startActivity(regIntent);
//                } else if (sChild == "View Department") {
//                    Intent regIntent = new Intent(MainActivity.this, ViewDepartment.class);
//                    startActivity(regIntent);
//                } else if (sChild == "Reg Venue") {
//                    Intent regIntent = new Intent(MainActivity.this, ExamVenue.class);
//                    startActivity(regIntent);
//                } else if (sChild == "Edit Venue") {
//                    Intent regIntent = new Intent(MainActivity.this, EditVenue.class);
//                    startActivity(regIntent);
//                } else if (sChild == "View Venue") {
//                    Intent regIntent = new Intent(MainActivity.this, ViewVenue.class);
//                    startActivity(regIntent);
//                } else if (sChild == "Set Time") {
//                    Intent regIntent = new Intent(MainActivity.this, SetExamTime.class);
//                    startActivity(regIntent);
//                } else if (sChild == "View Course") {
//                    Intent regIntent = new Intent(MainActivity.this, ViewCourseActivity.class);
//                    startActivity(regIntent);
//                } else if (sChild == "Set Exam Time Table") {
//                    Intent regIntent = new Intent(MainActivity.this, ExamSchedule.class);
//                    startActivity(regIntent);
//                }
//                return true;
//            }
//        });
    }

    public class SendIntentTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            adapter = new Expandable(listGroup, listChild);
            expandableListView.setAdapter(adapter);
            listGroup.add("Department");
            listGroup.add("Course");
            listGroup.add("Venue");
            listGroup.add("Time Schedule");
            listGroup.add("Exam Scheduling");
            listGroup.add("Settings");
            //add Department child
            ArrayList<String> mDepartment = new ArrayList<>();
            mDepartment.add("Reg Department");
            mDepartment.add("Edit Department");
            mDepartment.add("View Department");
            listChild.put("Department", mDepartment);
            //add course child
            ArrayList<String> mCourse = new ArrayList<>();
            mCourse.add("Reg Course");
            mCourse.add("Edit Course");
            mCourse.add("View Course");
            listChild.put("Course", mCourse);
            //add Venue child
            ArrayList<String> mVenue = new ArrayList<>();
            mVenue.add("Reg Venue");
            mVenue.add("Edit Venue");
            mVenue.add("View Venue");
            listChild.put("Venue", mVenue);
            //add Time child
            ArrayList<String> mTime = new ArrayList<>();
            mTime.add("Set Time");
            mTime.add("View Time");
            listChild.put("Time Schedule", mTime);
            //add course child
            ArrayList<String> mExamScheduling = new ArrayList<>();
            mExamScheduling.add("Set Exam Time Table");
            mExamScheduling.add("Edit Exam Time Table");
            mExamScheduling.add("View Exam Time Table");
            listChild.put("Exam Scheduling", mExamScheduling);
            //add Settings child
            ArrayList<String> mSettings = new ArrayList<>();
            mSettings.add("Reg Admin");
            mSettings.add("Edit Admin");
            listChild.put("Settings", mSettings);


            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                    String sChild = String.valueOf(adapter.getChild(i, i1));
                    if (sChild == "Reg Course") {
                        Intent regIntent = new Intent(MainActivity.this, Register.class);
                        startActivity(regIntent);


                    } else if (sChild == "Edit Course") {
                        Intent regIntent = new Intent(MainActivity.this, EditActivity.class);
                        startActivity(regIntent);
                    } else if (sChild == "Reg Department") {
                        Intent regIntent = new Intent(MainActivity.this, RegDepartment.class);
                        startActivity(regIntent);
                    } else if (sChild == "View Department") {
                        Intent regIntent = new Intent(MainActivity.this, ViewDepartment.class);
                        startActivity(regIntent);
                    } else if (sChild == "Reg Venue") {
                        Intent regIntent = new Intent(MainActivity.this, ExamVenue.class);
                        startActivity(regIntent);
                    } else if (sChild == "Edit Venue") {
                        Intent regIntent = new Intent(MainActivity.this, EditVenue.class);
                        startActivity(regIntent);
                    } else if (sChild == "View Venue") {
                        Intent regIntent = new Intent(MainActivity.this, ViewVenue.class);
                        startActivity(regIntent);
                    } else if (sChild == "Set Time") {
                        Intent regIntent = new Intent(MainActivity.this, SetExamTime.class);
                        startActivity(regIntent);
                    } else if (sChild == "View Course") {
                        Intent regIntent = new Intent(MainActivity.this, ViewCourseActivity.class);
                        startActivity(regIntent);
                    } else if (sChild == "Set Exam Time Table") {
                        Intent regIntent = new Intent(MainActivity.this, ExamSchedule.class);
                        startActivity(regIntent);
                    } else if (sChild == "View Exam Time Table") {
                        Intent regIntent = new Intent(MainActivity.this, ViewTimeTable.class);
                        startActivity(regIntent);
                    } else if (sChild == "View Time") {
                        Intent regIntent = new Intent(MainActivity.this, ViewExamTime.class);
                        startActivity(regIntent);
                    }
                    return true;
                }
            });
        }

    }
}