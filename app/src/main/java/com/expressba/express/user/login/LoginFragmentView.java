package com.expressba.express.user.login;

import android.app.Activity;

/**
 * Created by chao on 2016/4/16.
 */
public interface LoginFragmentView {
    void onback();

    void showToast(String message);

    Activity getTheActivity();

    boolean checkInput();

    void addUserNameEdit();

    void onError(int whereError, String errorInfo);
}
