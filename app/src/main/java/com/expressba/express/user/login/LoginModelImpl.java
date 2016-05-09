package com.expressba.express.user.login;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import com.expressba.express.main.MyApplication;
import com.expressba.express.net.VolleyHelper;
import com.expressba.express.R;

/**
 * Created by chao on 2016/4/16.
 */
public class LoginModelImpl extends VolleyHelper implements LoginModel{

    private LoginFragmentView loginView;
    private String loginUrl;
    private String registerUrl;

    private String telephone;
    private String mD5Password;
    private String name;
    private Integer userID;
    private Activity activity;
    private MyApplication application;//保存用户登录状态到全局appliction中

    private boolean isLogin;
    public LoginModelImpl(Activity activity,LoginFragmentView loginView) {
        super(activity);
        application = (MyApplication) activity.getApplication();
        this.activity = activity;
        this.loginView = loginView;
        loginUrl = activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.login_send);
        registerUrl = activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.register_send);
    }

    @Override
    public void startLogin(String tel, String password) {
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
            loginView.showToast("登陆数据解析失败");
        }
    }

    @Override
    public void startRegister(String tel, String password, String name) {
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
            loginView.showToast("注册数据解析失败");
        }
    }

    @Override
    public void onDataReceive(Object object) {
        JSONObject jsonObject = (JSONObject) object;
        if(isLogin) {
            try {
                String loginState = jsonObject.getString("loginstate");
                switch (loginState) {
                    case "true":
                        this.name = jsonObject.getString("name");//登陆成功后存储必要用户信息
                        application.getUserInfo().setId(jsonObject.getInt("id"));
                        application.getUserInfo().setName(this.name);
                        application.getUserInfo().setTelephone(telephone);
                        application.getUserInfo().setPassword(mD5Password);
                        application.getUserInfo().setToken(jsonObject.getString("token"));

                        application.getUserInfo().setLoginState(true);
                        application.getUserInfo().setToken(jsonObject.getString("token"));
                        loginView.showToast("登陆成功");
                        loginView.onback();
                        break;
                    case "false":
                        application.getUserInfo().setLoginState(false);
                        loginView.showToast("登陆失败，请重试");
                        break;
                    default:
                        application.getUserInfo().setLoginState(false);
                        loginView.showToast("登陆失败，请重试");
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                loginView.showToast("异常，请重试");
            }
        }else{
            try {
                String registerState = jsonObject.getString("registerstate");
                switch (registerState) {
                    case "true":
                        application.getUserInfo().setLoginState(true);
                        application.getUserInfo().setName(name);
                        application.getUserInfo().setPassword(mD5Password);
                        application.getUserInfo().setTelephone(telephone);
                        application.getUserInfo().setId(jsonObject.getInt("id"));//---!!!
                        application.getUserInfo().setToken(jsonObject.getString("token"));
                        loginView.showToast("注册成功");
                        loginView.onback();
                        break;
                    case "false":
                        application.getUserInfo().setLoginState(false);
                        application.getUserInfo().setLoginState(false);
                        loginView.showToast("注册失败");
                        break;
                    case "deny":
                        application.getUserInfo().setLoginState(false);
                        application.getUserInfo().setLoginState(false);
                        loginView.showToast("手机号已经被注册，请登录");
                        break;
                    default:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                loginView.showToast("异常，请重试");
            }
        }
    }

    @Override
    public void onError(String errorMessage) {
        loginView.showToast(errorMessage);
    }
}
