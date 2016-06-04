package com.expressba.expressuser.user.expresshistory;

import java.util.ArrayList;

import com.expressba.expressuser.model.ExpressInfo;

/**
 * Created by songchao on 16/5/8.
 */
public interface ExpressHistoryView {

    void startGetData();
    void onSuccess(ArrayList<ExpressInfo> expressInfos);
    void showMessage(String message);
}
