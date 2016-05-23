package com.expressba.express.map;

import com.baidu.mapapi.model.LatLng;
import com.expressba.express.map.model.MyLatLng;
import com.expressba.express.model.EmployeeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songchao on 16/5/20.
 */
public interface MyBaiduMapView {
    void onError(String message);
    void getAllTraceCallBack(List<MyLatLng> latLngs);
    void onGetEmployeeSuccess(ArrayList<EmployeeInfo> employeeInfos);
}
