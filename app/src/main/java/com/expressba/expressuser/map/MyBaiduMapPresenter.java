package com.expressba.expressuser.map;

import java.util.ArrayList;

/**
 * Created by songchao on 16/5/20.
 */
public interface MyBaiduMapPresenter {
    void startGetAllTrace(ArrayList<String> entityNames,Boolean resetData);
    void startGetEmployees(String expressID);
}
