package extrace.model;

import java.io.Serializable;

/**
 * Created by 黎明 on 2016/4/25.
 */
public class PackageInfo implements Serializable {
    private String id;//包裹ID
    private String packageFrom;//从哪里来
    private String packageTo;//到哪里去
    private String employeesName;
    private Integer employeesID;
    private String closeTime;//打包时间

    public PackageInfo() {
    }

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
