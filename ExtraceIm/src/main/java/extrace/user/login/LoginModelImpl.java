package extrace.user.login;

import android.app.Activity;

import junit.framework.Assert;

import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam;

/**
 * Created by chao on 2016/4/16.
 */
public class LoginModelImpl extends HttpAsyncTask implements LoginModel{

    LoginPresenter loginPresenter;
    public LoginModelImpl(Activity activity,LoginPresenter loginPresenter) {
        super(activity);
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void onStartLogin(String tel,String password) {
        String url = "http://www.baidu.com";
        String str_json = "{'tel':"+tel+",password:"+password+"}";
        try {
            execute(url, "POST",str_json);
        } catch (Exception e) {
            e.printStackTrace();
            loginPresenter.onLoginFail();
        }
    }

    @Override
    public void onStartRegister(String tel,String password) {
        String url = "http:www.baidu.com";
        String str_json = "{'tel':"+tel+",password:"+password+"}";
        try {
            execute(url, "POST",str_json);
        } catch (Exception e) {
            e.printStackTrace();
            loginPresenter.onLoginFail();
        }
    }


    @Override
    public void onDataReceive(String class_name, String json_data) {
        switch (json_data){
            case "true":
                loginPresenter.onLoginSuccess();
                break;
            case "false":
                loginPresenter.onLoginFail();
                break;
            case "repeat":
                loginPresenter.onLoginRepeat();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStatusNotify(HttpResponseParam.RETURN_STATUS status, String str_response) {
    }
}
