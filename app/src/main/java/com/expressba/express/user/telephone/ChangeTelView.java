package com.expressba.express.user.telephone;

/**
 * Created by chao on 2016/4/28.
 */
public interface ChangeTelView {
    void getVerify();
    void submit();
    void onSubmitSuccess();
    void onError(String errormessage);
}
