package extrace.user.login;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import extrace.main.MyApplication;
import extrace.model.MD5;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam;

/**
 * Created by chao on 2016/4/16.
 */
public class LoginModelImpl extends HttpAsyncTask implements LoginModel{

    private LoginPresenter loginPresenter;
    private String loginUrl = "www.baidu.com";
    private String registerUrl = "www.baidu.com";

    private String mD5Tel;
    private String mD5Password;
    private Activity activity;
    private MyApplication application;//保存用户登录状态到全局appliction中

    private boolean isLogin;
    public LoginModelImpl(Activity activity,LoginPresenter loginPresenter) {
        super(activity);
        application = (MyApplication) activity.getApplication();
        this.activity = activity;
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void onStartLogin(String tel,String password) {
        isLogin = true;
        String url = loginUrl;
        //加密传输
        tel = MD5.getMD5(tel);
        password = MD5.getMD5(password);


        this.mD5Tel = tel;
        this.mD5Password = password;

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
        isLogin = false;
        String url = registerUrl;
        //加密传输
        tel = MD5.getMD5(tel);
        password = MD5.getMD5(password);
        this.mD5Tel = tel;
        this.mD5Password = password;

        String str_json = "{'tel':"+tel+",password:"+password+"}";
        try {
            execute(url, "POST",str_json);
        } catch (Exception e) {
            e.printStackTrace();
            loginPresenter.onLoginFail();
        }
    }

    @Override
    public void onSaveUserInfo() {
        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("tel",mD5Tel);
        editor.putString("password",mD5Password);
        editor.apply();
    }


    @Override
    public void onDataReceive(String class_name, String json_data) {
        if(isLogin) {
            switch (json_data) {
                case "true":
                    onSaveUserInfo();
                    application.getUserInfo().setLoginState(true);
                    loginPresenter.onLoginSuccess();
                    break;
                case "false":
                    application.getUserInfo().setLoginState(false);
                    loginPresenter.onLoginFail();
                    break;
                default:
                    break;
            }
        }else{
            switch (json_data) {
                case "true":
                    onSaveUserInfo();
                    application.getUserInfo().setLoginState(true);
                    loginPresenter.onRegisterSuccess();
                    break;
                case "false":
                    application.getUserInfo().setLoginState(false);
                    loginPresenter.onRegisterFail();
                    break;
                case "repeat":
                    application.getUserInfo().setLoginState(false);
                    loginPresenter.onRegisterRepeat();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onStatusNotify(HttpResponseParam.RETURN_STATUS status, String str_response) {
    }
}
