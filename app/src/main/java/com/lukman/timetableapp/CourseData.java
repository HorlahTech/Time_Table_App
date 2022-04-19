package com.lukman.timetableapp;


public class CourseData {
    private int id;
    private String tittle;
    private final String HASCCODE = null;
    private final String HASCUNIT =  null;
    private final String HASNOOFSTD = null;
    private String code = HASCCODE;
    private String unit = HASCUNIT;
    private String noostd = HASNOOFSTD;

    public CourseData(int id,String tittle) {
        this.tittle = tittle;

    }

        public CourseData(int id,String tittle, String code, String unit) {
        this.tittle = tittle;
        this.code = code;
        this.unit = unit;

    }
    public CourseData(String tittle, String code, String unit, String noostd) {
        this.tittle = tittle;
        this.code = code;
        this.unit = unit;
        this.noostd = noostd;
    }

    public CourseData(int id, String tittle, String code, String unit, String noostd) {
        this.id = id;
        this.tittle = tittle;
        this.code = code;
        this.unit = unit;
        this.noostd = noostd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }



    public String getCode() {
        return code;
    }



    public String getUnit() {
        return unit;
    }


    public String getNoostd() {
        return noostd;
    }
//    public boolean checktitle(){
//        return code != HASCCODE;
//    }
    public boolean checkcode(){
        return code != HASCCODE;
    }
    public boolean checkUnit(){
        return unit != HASCUNIT;
    }
    public boolean checkNoofStd(){ return noostd != HASNOOFSTD; }

}

