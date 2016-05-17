package com.expressba.express.map;

import android.app.Activity;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.LBSTraceClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songchao on 16/5/17.
 * 获取所有的地址信息，处理并返回给调用的Fragment
 */
public class GetAllTrace {
    private final Activity activity;
    private String entityName;
    public static LBSTraceClient client;
    private MyHistoryTrace myHistoryTrace;
    private boolean hasHistory = false;//历史数据只请求一次，这个用来标识
    private List<LatLng> latLngsHistory;
    private List<LatLng> tempLatLngs;

    public GetAllTrace(Activity activity, String entityName){
        this.activity = activity;
        this.entityName = entityName;
        tempLatLngs = new ArrayList<>();
        client = new LBSTraceClient(activity);//实例化轨迹客户端
        myHistoryTrace = new MyHistoryTrace();

        setListener();//设置监听

    }

    /**
     * 开始获取点
     */
    public void startGetAllTrace(){
        if(!hasHistory) {
            myHistoryTrace.queryProcessedHistoryTrack(entityName);
        }else {
            myHistoryTrace.queryRealtimeTrack();
        }
    }


    private void onError(String message){
        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
    }


    /**
     * 设置listener
     */
    private void setListener(){
        myHistoryTrace.historyInterface = new MyHistoryTrace.QueryHistoryInterface() {
            @Override
            public void queryHistoryCallBack(List<LatLng> latLngs) {
                if(latLngs.size()>1) {
                    latLngsHistory = latLngs;
                    hasHistory = true;
                    myHistoryTrace.queryRealtimeTrack();
                }else if(latLngs.size() == 1){
                    LatLng latLng = latLngs.get(0);
                    Double latitude = latLng.latitude;
                    Double longitude = latLng.longitude;
                    if(Math.abs(latitude - 0.0) < 0.000001 && Math.abs(longitude - 0.0) < 0.000001){
                        onError("当前查询无轨迹点");
                    }
                }else{
                    onError("当前查询无轨迹点");
                    myHistoryTrace.queryRealtimeTrack();
                }
            }

            @Override
            public void requestFail(String s) {
                onError(s);
            }
        };

        myHistoryTrace.entityInterface = new MyHistoryTrace.OnEntityInterface() {
            @Override
            public void onRequestFailed(String s) {
                onError(s);
                if(hasHistory){
                    if(getAllTraceInterface!=null){
                        getAllTraceInterface.getAllTraceCallBack(latLngsHistory);
                    }
                }
            }

            @Override
            public void onReceiveLocation(LatLng latLng) {
                if (hasHistory) {
                    if(latLng!=null){
                        latLngsHistory.add(latLng);
                    }else{
                        onError("获取的当前位置为空");
                    }
                    if(getAllTraceInterface!=null){
                        getAllTraceInterface.getAllTraceCallBack(latLngsHistory);
                    }
                }else{
                    tempLatLngs.add(latLng);
                    if(getAllTraceInterface!=null){
                        getAllTraceInterface.getAllTraceCallBack(tempLatLngs);
                    }
                }
            }
        };
    }

    /**
     * 获取点之后的回调函数
     */
    public GetAllTraceInterface getAllTraceInterface;

    public interface GetAllTraceInterface{
        void getAllTraceCallBack(List<LatLng> latLngs);
    }

}
