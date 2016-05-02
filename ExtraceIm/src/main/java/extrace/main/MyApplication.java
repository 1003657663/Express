package extrace.main;

import android.app.Application;

import extrace.model.EmployeesEntity;
import extrace.model.UserInfo;

/**
 * Created by chao on 2016/4/17.
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
        employeesInfo=new EmployeesEntity(111,"name","pass","tel",1,"job",3,4);
        //--------------------------------test
        //userInfo.setLoginState(true);
        //------------------------------------
      // MSSDK.initSDK(this, "12282c18097fb", "55a709db05d0213647f5bd05e29c24f6");//初始化短信发送sdk
        
    }

    public EmployeesEntity getEmployeesInfo()
    {
        return employeesInfo;
    }
    public UserInfo getUserInfo() {
        if(userInfo!=null) {
            return userInfo;
        }else {
            userInfo = new UserInfo(getApplicationContext());
            return userInfo;
        }
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    public void setEmployeesInfo(EmployeesEntity employeesInfo)
    {
        this.employeesInfo=employeesInfo;
    }
}
