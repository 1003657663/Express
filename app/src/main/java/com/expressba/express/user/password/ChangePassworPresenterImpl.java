package com.expressba.express.user.password;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.expressba.express.main.MyApplication;
import com.expressba.express.model.UserInfo;
import com.expressba.express.net.VolleyHelper;
import com.expressba.express.R;

/**
 * Created by chao on 2016/4/28.
 */
public class ChangePassworPresenterImpl extends VolleyHelper implements ChangePassworPresenter{
    ChangePasswordView changePasswordView;
    String changePasswordUrl;
    UserInfo userInfo;
    MyApplication application;
    String password;
    public ChangePassworPresenterImpl(Activity context,ChangePasswordView changePasswordView) {
        super(context);
        application = (MyApplication)context.getApplication();
        userInfo = application.getUserInfo();
        this.changePasswordView = changePasswordView;
        changePasswordUrl = context.getResources().getString(R.string.base_url)+
                context.getResources().getString(R.string.user_change_password);
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        try {
            if(jsonObject.getString("changepwd").equals("true")){
                application.getUserInfo().setPassword(password);
                changePasswordView.onSubmitSuccess();
            }{
                onError("修改密码失败，请重试");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            onError("修改密码出错，请重试");
        }
    }

    @Override
    public void onError(String errorMessage) {
        changePasswordView.onError(errorMessage);
    }

    @Override
    public void onSubmit(String password) {
        this.password = password;
        changePasswordUrl = changePasswordUrl.replace("{pwdold}",userInfo.getPassword());
        changePasswordUrl = changePasswordUrl.replace("{pwdnew}",password);
        changePasswordUrl = changePasswordUrl.replace("{tel}",userInfo.getTelephone());
        doJson(changePasswordUrl,VolleyHelper.GET,null);
    }
}
