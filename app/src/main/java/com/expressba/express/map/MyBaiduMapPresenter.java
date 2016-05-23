package com.expressba.express.map;

import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songchao on 16/5/20.
 */
public interface MyBaiduMapPresenter {
    void startGetAllTrace(ArrayList<String> entityNames,Boolean resetData);
    void startGetEmployees(String expressID);
}
