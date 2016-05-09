package com.expressba.express.model;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * Created by 黎明 on 2016/4/25.
 */
public class PackageInfo implements Parcelable{

    private String id;//包裹ID
    private String packageFrom;//从哪里来
    private String packageTo;//到哪里去
    private String employeesName;
    private Integer employeesID;
    private String closeTime;//打包时间

    public PackageInfo() {
    }
    protected PackageInfo(Parcel in) {
        id = in.readString();
       packageFrom = in.readString();
       packageTo = in.readString();
        employeesName = in.readString();
        employeesID = in.readInt();
        closeTime = in.readString();
    }

    public static final Creator<PackageInfo> CREATOR = new Creator<PackageInfo>() {
        @Override
        public PackageInfo createFromParcel(Parcel in) {
            return new PackageInfo(in);
        }

        @Override
        public PackageInfo[] newArray(int size) {
            return new PackageInfo[size];
        }
    };
    public PackageInfo(String id, String packageFrom, String packageTo, String employeesName, Integer employeesID, String closeTime) {
        this.id = id;
        this.packageFrom = packageFrom;
        this.packageTo = packageTo;
        this.employeesName = employeesName;
        this.employeesID = employeesID;
        this.closeTime = closeTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackageFrom() {
        return packageFrom;
    }

    public void setPackageFrom(String packageFrom) {
        this.packageFrom = packageFrom;
    }

    public String getPackageTo() {
        return packageTo;
    }

    public void setPackageTo(String packageTo) {
        this.packageTo = packageTo;
    }

    public String getEmployeesName() {
        return employeesName;
    }

    public void setEmployeesName(String employeesName) {
        this.employeesName = employeesName;
    }

    public Integer getEmployeesID() {
        return employeesID;
    }

    public void setEmployeesID(Integer employeesID) {
        this.employeesID = employeesID;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(packageFrom);
        dest.writeString(packageTo);
        dest.writeString(employeesName);
        dest.writeInt(employeesID);
        dest.writeString(closeTime);
    }
    public String toString() {
        return "PackageInfo{" +
                "id='" + id + '\'' +
                ", packageFrom='" + packageFrom + '\'' +
                ", packageTo='" + packageTo + '\'' +
                ", employeesName='" + employeesName + '\'' +
                ", employeesID=" + employeesID +
                ", closeTime='" + closeTime + '\'' +
                '}';
    }
}
