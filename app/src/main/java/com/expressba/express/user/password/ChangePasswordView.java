package com.expressba.express.user.password;

/**
 * Created by chao on 2016/4/28.
 */
public interface ChangePasswordView {
    void onSubmitSuccess();
    void onError(String errorMessage);
    void showToast(String message);
    void submit();
}
