package com.lukman.timetableapp;

import androidx.annotation.NonNull;

public class TimeObject {
    private int id;
    private final String timeStart;
    private String timeStop;

    public TimeObject(int id, String timeStart, String timeStop) {
        this.id = id;
        this.timeStart = timeStart;
        this.timeStop = timeStop;
    }

    public TimeObject(String timeStart) {
        this.timeStart = timeStart;
    }

    public TimeObject(String timeStart, String timeStop) {
        this.timeStart = timeStart;
        this.timeStop = timeStop;
    }

    public int getId() {
        return id;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeStop() {
        return timeStop;
    }

    @NonNull
    @Override
    public String toString() {

        return getTimeStart() + " - " +getTimeStop() ;
    }

}
