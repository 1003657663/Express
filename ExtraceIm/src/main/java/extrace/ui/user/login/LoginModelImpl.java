package extrace.ui.user.login;

import android.app.Activity;
import android.os.Message;

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
    public void onStartLogin() {
        String url = "http://www.baidu.com";
        String str_json = "{'name':'songchao','age':'23'}";
        try {
            execute(url, "POST",str_json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDataReceive(String class_name, String json_data) {
        if(json_data.equals("true")){
            loginPresenter.onLoginSuccess();
        }else{
            loginPresenter.onLoginFail();
        }
    }

    @Override
    public void onStatusNotify(HttpResponseParam.RETURN_STATUS status, String str_response) {
    }
}
