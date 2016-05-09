package extrace.main;

import android.app.Application;
import android.location.Address;

import cn.smssdk.SMSSDK;
import extrace.model.EmployeesEntity;
import extrace.model.UserAddress;
import extrace.model.UserInfo;

/**
 * Created by chao on 2016 /4/17.
 * 使用application保存全局变量
 */
public class MyApplication extends Application {
    //全局变量
    private UserInfo userInfo;
    private EmployeesEntity employeesInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        userInfo = new UserInfo(getApplicationContext());
<<<<<<< HEAD
        employeesInfo = new EmployeesEntity(2, "name", "password","00000000000",1,"jobtext",0, 3,"1","2");
        //--------------------------------test
        //userInfo.setLoginState(true);
        //------------------------------------
=======
>>>>>>> d28d0462aa0c792aa978cc71f810248b4b3c7ec2
        SMSSDK.initSDK(this, "12282c18097fb", "55a709db05d0213647f5bd05e29c24f6");//初始化短信发送sdk

        testInit();//待删除
    }

    /**
     * 测试用方法，待删除
     * 以后需要在application中添加测试程序就写在这个方法里
     */
    private void testInit(){
        employeesInfo = new EmployeesEntity(3, "name", "password","00000000000",1,"jobtext",0, 3,"1","2");
    }

    public EmployeesEntity getEmployeesInfo() {
        if (employeesInfo != null) {
            return employeesInfo;
        } else {
            employeesInfo =  new EmployeesEntity(3, "name", "password","00000000000",1,"jobtext",0, 3,"1","2");
            return employeesInfo;
        }
    }
    public void setEmployeesInfo(EmployeesEntity employeesInfo) {
        this.employeesInfo = employeesInfo;
    }

    public UserInfo getUserInfo() {
        if (userInfo != null) {
            return userInfo;
        } else {
            userInfo = new UserInfo(getApplicationContext());
            return userInfo;
        }
    }
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
