package com.expressba.express.user.login;

/**
 * Created by chao on 2016/4/16.
 */
public interface LoginModel {
    void startLogin(String tel, String password);

    void startRegister(String tel, String password, String name);
}
