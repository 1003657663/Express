package extrace.main;

import android.app.Application;

import extrace.model.UserInfo;

/**
 * Created by chao on 2016/4/17.
 * 使用application保存全局变量
 */
public class MyApplication extends Application {
    //全局变量
    private UserInfo userInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        userInfo = new UserInfo();
        //---!!!---先把用户登录状态设为true测试
        userInfo.setLoginState(true);
    }

    public UserInfo getUserInfo() {
        if(userInfo!=null) {
            return userInfo;
        }else {
            return new UserInfo();
        }
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
