package com.expressba.express.model;


import java.util.Date;

/**
 * Created by violet on 2016/4/6.
 */

public class Package{
    public String id;
    public int employeesId;
    public String time;

    public Package() {
    }

    public Package(String id, int employeesId, String time) {
        this.id = id;
        this.employeesId = employeesId;
        this.time = time;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getEmployeesId() {
        return employeesId;
    }

    public void setEmployeesId(int employeesId) {
        this.employeesId = employeesId;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



}
