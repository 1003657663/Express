package com.expressba.express.model;


import android.os.Parcel;
import android.os.Parcelable;

public class EmployeeInfo implements Parcelable {
    public static final int KUAIDI_EMPLOYEE = 1;
    public static final int FENJIAN_EMPLOYEE = 2;
    public static final int SIJI_EMPLOYEE = 3;

    private Integer employeeId;
    private Integer Job;

    public EmployeeInfo(){

    }

    protected EmployeeInfo(Parcel in) {
    }

    public static final Creator<EmployeeInfo> CREATOR = new Creator<EmployeeInfo>() {
        @Override
        public EmployeeInfo createFromParcel(Parcel in) {
            return new EmployeeInfo(in);
        }

        @Override
        public EmployeeInfo[] newArray(int size) {
            return new EmployeeInfo[size];
        }
    };

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getJob() {
        return Job;
    }

    public void setJob(Integer job) {
        Job = job;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
