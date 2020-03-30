package com.tupt.torqapp.Model;

public class Ppmp {
    private String status;
    private String course;
    private String type;
    private String fiscal_year;
    private int id;

    public  Ppmp(){}

    public Ppmp(int id, String status, String course, String type, String fiscal_year){
        this.id = id;
        this.status = status;
        this.course = course;
        this.type = type;
        this.fiscal_year = fiscal_year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFiscal_year() {
        return fiscal_year;
    }

    public void setFiscal_year(String fiscal_year) {
        this.fiscal_year = fiscal_year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
