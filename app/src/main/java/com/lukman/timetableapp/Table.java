package com.lukman.timetableapp;

public class Table {
    private final String day;
    private final String course;
    private final String venue;
    private final String times;

    public Table(String day, String course, String venue, String times) {
        this.day = day;
        this.course = course;
        this.venue = venue;
        this.times = times;
    }


    public String getDay() {
        return day;
    }

    public String getCourse() {
        return course;
    }

    public String getVenue() {
        return venue;
    }

    public String getTimes() {
        return times;
    }
}
