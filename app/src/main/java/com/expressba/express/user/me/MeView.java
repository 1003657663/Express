package com.expressba.express.user.me;

/**
 * Created by songchao on 16/4/24.
 */
public interface MeView {
    void toReceiveRecordFragment();
    void toSendRecordFragment();
    void toAboutSoftFragment();
    void toMyComplaint();
    void toUserSendAddress();
    void toUserReceiveAddress();
    void toTelChange();
    void toPasswordChange();
    void loginOut();
}
