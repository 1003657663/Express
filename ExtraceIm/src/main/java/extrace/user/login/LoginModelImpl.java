package extrace.user.login;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import extrace.main.MyApplication;
import extrace.model.MD5;
import extrace.net.VolleyHelper;

/**
 * Created by chao on 2016/4/16.
 */
public class LoginModelImpl extends VolleyHelper implements LoginModel{

    private LoginPresenter loginPresenter;
    private String loginUrl = "http://182.254.214.97:8080/REST/Domain/login";
    private String registerUrl = "http://182.254.214.97:8080/REST/Domain/register";

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
        //tel = MD5.getMD5(tel);
        //password = MD5.getMD5(password);

        this.mD5Tel = tel;
        this.mD5Password = password;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("telephone",tel);
            jsonObject.put("password",password);

            doJson(url,VolleyHelper.POST,jsonObject);

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

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("telephone",tel);
            jsonObject.put("password",password);

            doJson(url,VolleyHelper.POST,jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
            loginPresenter.onRegisterFail();
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
    public void onDataReceive(Object object) {
        JSONObject jsonObject = (JSONObject) object;
        if(isLogin) {
            try {
                String loginState = jsonObject.getString("loginstate");
                switch (loginState) {
                    case "true":
                        onSaveUserInfo();
                        application.getUserInfo().setName(jsonObject.getString("name"));
                        application.getUserInfo().setLoginState(true);
                        loginPresenter.onLoginSuccess();
                        break;
                    case "false":
                        application.getUserInfo().setLoginState(false);
                        loginPresenter.onLoginFail();
                        break;
                    default:
                        loginPresenter.onLoginFail();
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                loginPresenter.onLoginFail();
            }
        }else{
            try {
                String registerState = jsonObject.getString("registerstate");
                switch (registerState) {
                    case "true":
                        onSaveUserInfo();
                        application.getUserInfo().setName(jsonObject.getString("name"));
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
            } catch (JSONException e) {
                e.printStackTrace();
                loginPresenter.onRegisterFail();
            }
        }
    }

    @Override
    public void onError(String errorMessage) {

    }
}
