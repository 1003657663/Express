package extrace.user.login;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import extrace.main.MyApplication;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by chao on 2016/4/16.
 */
public class LoginModelImpl extends VolleyHelper implements LoginModel{

    private LoginPresenter loginPresenter;
    private String loginUrl;
    private String registerUrl;

    private String telephone;
    private String mD5Password;
    private String name;
    private Activity activity;
    private MyApplication application;//保存用户登录状态到全局appliction中

    private boolean isLogin;
    public LoginModelImpl(Activity activity,LoginPresenter loginPresenter) {
        super(activity);
        application = (MyApplication) activity.getApplication();
        this.activity = activity;
        this.loginPresenter = loginPresenter;
        loginUrl = activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.login_send);
        registerUrl = activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.register_send);
    }

    @Override
    public void onStartLogin(String tel,String password) {
        isLogin = true;
        String url = loginUrl;
        //加密传输
        this.telephone = tel;
        //tel = MD5.getMD5(tel);
        //password = MD5.getMD5(password);

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
    public void onStartRegister(String tel,String password,String name) {
        isLogin = false;
        String url = registerUrl;
        //加密传输
        this.telephone = tel;
        this.name = name;
        //tel = MD5.getMD5(tel);
        //password = MD5.getMD5(password);
        this.mD5Password = password;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("telephone",tel);
            jsonObject.put("password",password);
            jsonObject.put("name",name);

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
        editor.putString("tel",telephone);
        editor.putString("password",mD5Password);
        editor.putString("name",name);
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
                        this.name = jsonObject.getString("name");//登陆成功后存储必要用户信息
                        application.getUserInfo().setName(this.name);
                        application.getUserInfo().setTelephone(telephone);
                        application.getUserInfo().setPassword(mD5Password);
                        application.getUserInfo().setLoginState(true);
                        loginPresenter.onLoginSuccess();
                        break;
                    case "false":
                        application.getUserInfo().setLoginState(false);
                        application.getUserInfo().setLoginState(false);
                        loginPresenter.onLoginFail();
                        break;
                    default:
                        application.getUserInfo().setLoginState(false);
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
                        application.getUserInfo().setLoginState(true);
                        loginPresenter.onRegisterSuccess();
                        break;
                    case "false":
                        application.getUserInfo().setLoginState(false);
                        application.getUserInfo().setLoginState(false);
                        loginPresenter.onRegisterFail();
                        break;
                    case "deny":
                        application.getUserInfo().setLoginState(false);
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
