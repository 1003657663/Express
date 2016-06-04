package com.expressba.expressuser.user.login;

/**
 * Created by chao on 2016/4/16.
 */
public interface LoginFragmentView {
    void onBack();

    void onSuccess();

    void showToast(String message);

    boolean checkInput();

    void addUserNameEdit();

    void onError(int whereError, String errorInfo);
}
