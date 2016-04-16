package extrace.model;

import android.app.Application;

import extrace.misc.model.UserInfo;

/**
 * Created by chao on 2016/4/15.
 */
public class UserApplication extends Application {
    private UserInfo userInfo;


    //onCreate创建时初始化一个虚拟用户作测试用
    @Override
    public void onCreate() {
        super.onCreate();

        userInfo = new UserInfo();
        userInfo.setID(12);
        userInfo.setTelCode("15039985698");
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
