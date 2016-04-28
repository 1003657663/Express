package extrace.model;

/**
 * Created by 黎明 on 2016/4/25.
 */
public class packageInfo {
    public String ID;//包裹ID
    public String packagefrom;//从哪里来
    public String packageto;//到哪里去
    public String EmployeesName;
    public int EmployeesID;
    public String closetime;//打包时间

    public packageInfo(){}
    public String getClosetime() {
        return closetime;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setClosetime(String closetime) {
        this.closetime = closetime;
    }

    public String getEmployeesName() {
        return EmployeesName;
    }

    public void setEmployeesName(String employeesName) {
        EmployeesName = employeesName;
    }

    public int getEmployeesID() {
        return EmployeesID;
    }

    public void setEmployeesID(int employeesID) {
        EmployeesID = employeesID;
    }

    public String getPackagefrom() {
        return packagefrom;
    }

    public void setPackagefrom(String packagefrom) {
        this.packagefrom = packagefrom;
    }

    public String getPackageto() {
        return packageto;
    }

    public void setPackageto(String packageto) {
        this.packageto = packageto;
    }
}
