package com.expressba.express.map.model;


import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 历史轨迹数据,model
 * Created by songchao on 16/4/28.
 */
public class HistoryTrackData {
    public int status; // 状态码，0为成功
    public int size; // 返回结果条数，该页返回了几条数据
    public int total; // 符合条件结果条数，一共有几条符合条件的数据
    public String entity_name;// 返回的entity标识
    public String message; // 响应信息,对status的中文描述


    /*public static final double EARTH_RADIUS = 6378.137;//地球半径

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }*/

    /*public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }*/

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HistoryTrackData [status=" + status + ", size=" + size + ", total=" + total + ", entity_name="
                + entity_name + ", message=" + message + "]";
    }

}
