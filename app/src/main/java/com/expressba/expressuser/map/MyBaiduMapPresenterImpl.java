package com.expressba.expressuser.map;

import android.app.Activity;
import android.util.Log;

import com.expressba.expressuser.R;
import com.expressba.expressuser.map.model.MyLatLng;
import com.expressba.expressuser.model.EmployeeInfo;
import com.expressba.expressuser.net.VolleyHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by songchao on 16/5/17.
 * 获取所有的地址信息，处理并返回给调用的Fragment
 */
public class MyBaiduMapPresenterImpl extends VolleyHelper implements MyBaiduMapPresenter{
    private final Activity activity;
    private MyHistoryTrace myHistoryTrace;

    private List<MyLatLng> latLngsHistory;
    private List<List<MyLatLng>> myLatLngArrays;//用来存储每一个获得的ArrayList<MyLatLng>

    private MyBaiduMapView myBaiduMapView;
    private boolean hasGetLastHistory = false;//判断是否是最后一个请求历史
    private String getEmployeeUrl;

    private ArrayList<String> entityNames;
    private int i = 0;


    public MyBaiduMapPresenterImpl(Activity activity,MyBaiduMapView myBaiduMapView){
        super(activity);
        this.activity = activity;
        this.myBaiduMapView = myBaiduMapView;

        String baseUrl = activity.getResources().getString(R.string.base_url);
        getEmployeeUrl = baseUrl + activity.getResources().getString(R.string.employee_get_map);

        latLngsHistory = new ArrayList<>();
        myLatLngArrays = new ArrayList<>();
        myHistoryTrace = new MyHistoryTrace();

        setListener();//设置监听

    }

    /**
     * 开始获取点
     */
    @Override
    public void startGetAllTrace(ArrayList<String> entityNames,Boolean resetData){
        //i = entityNames.size();
        if(entityNames!=null && entityNames.size()==0){
            return;
        }
        this.entityNames = entityNames;
        if(resetData != null && resetData){
            hasGetLastHistory = false;
        }
        if(i<entityNames.size()){//i小于size表示还没有加载完，或者entityname的尺寸发生了增加
            hasGetLastHistory = false;
        }
        if(hasGetLastHistory){
            myHistoryTrace.queryProcessedHistoryTrack(entityNames.get(entityNames.size()-1),historyInterface);
        }else {
            myHistoryTrace.queryProcessedHistoryTrack(entityNames.get(0),historyInterface);
        }
    }

    /**
     * 开始获取员工
     * @param expressID
     */
    @Override
    public void startGetEmployees(String expressID) {
        getEmployeeUrl = getEmployeeUrl.replace("{expressId}",expressID);
        doJsonArray(getEmployeeUrl,VolleyHelper.GET,null);
    }

    /**
     * 获取员工信息回调
     * @param jsonOrArray
     */
    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONArray jsonArray = (JSONArray) jsonOrArray;
        ArrayList<EmployeeInfo> employeeInfos = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                EmployeeInfo employeeInfo = new EmployeeInfo();
                employeeInfo.setEmployeeId(jsonObject.getInt("employeeId"));
                employeeInfo.setJob(jsonObject.getInt("job"));
                employeeInfos.add(employeeInfo);
            }
            myBaiduMapView.onGetEmployeeSuccess(employeeInfos);
        } catch (JSONException e) {
            e.printStackTrace();
            onError("请求路径id出错");
        }
    }

    /**
     * 如果entity不存在，那么添加上去
     * @param entityName
     */
    private void handlerEmptyEntity(String entityName){
        myHistoryTrace.addEntity(entityName, new MyHistoryTrace.EntityListenerInterface() {
            @Override
            public void addEntityCallBack(String s) {
                Log.e("addcallback",s);
            }

            @Override
            public void requestFailedCallBack(String s) {
                Log.e("addfail:",s);
            }
        });
    }

    /**
     * 错误提示
     * @param message
     */
    @Override
    public void onError(String message){
        myBaiduMapView.onError(message);
    }


    /**
     * 设置listener
     */
    MyHistoryTrace.QueryHistoryInterface historyInterface;
    private void setListener(){
        historyInterface = new MyHistoryTrace.QueryHistoryInterface() {
            @Override
            public void queryHistoryCallBack(List<MyLatLng> latLngs) {
                if(i < entityNames.size()-2){
                    i++;
                    if(latLngs !=null && latLngs.size()>1) {
                        myLatLngArrays.add(latLngs);
                    }
                    myHistoryTrace.queryProcessedHistoryTrack(entityNames.get(i),historyInterface);
                }else if(i == entityNames.size()-2){
                    i++;
                    hasGetLastHistory = true;
                    if(latLngs !=null && latLngs.size()>1) {
                        myLatLngArrays.add(latLngs);
                    }
                    myHistoryTrace.queryProcessedHistoryTrack(entityNames.get(i),historyInterface);
                }
                if(i == entityNames.size()-1){
                    handlerLatLngList(myLatLngArrays,latLngs);
                    myBaiduMapView.getAllTraceCallBack(latLngsHistory);
                }
            }

            @Override
            public void requestFail(int state,String s) {
                if(state == 3003){
                     handlerEmptyEntity(entityNames.get(i));
                }else {
                    onError(s);
                }
            }
        };
    }

    /**
     * 把两个列表的点，按照时间顺序排列起来。-------待测试，如果不排列有没有问题
     * @param myLatLngArrays
     */
    private void handlerLatLngList(List<List<MyLatLng>> myLatLngArrays,List<MyLatLng> latLngs){
        List<MyLatLng> myLatLngs;
        String firstTime = null;
        latLngsHistory.clear();
        for(int h=0;h<myLatLngArrays.size();h++){
            myLatLngs = myLatLngArrays.get(h);
            if(myLatLngs!=null && myLatLngs.size()>0){
                if(h==0) {//h是0直接写入
                    firstTime = myLatLngs.get(0).create_time;
                    for (int r = 0; r < myLatLngs.size(); r++) {
                        latLngsHistory.add(myLatLngs.get(r));
                    }
                }else {
                    if(timeCompareBig(firstTime,myLatLngs.get(0).create_time)==1){//第二个大，加在列表后边
                        for(int r=0;r<myLatLngs.size();r++){
                            latLngsHistory.add(myLatLngs.get(r));
                        }
                    }else {
                        for(int r=0;r<myLatLngs.size();r++){
                            latLngsHistory.add(r,myLatLngs.get(r));
                        }
                    }
                }
            }
        }
        latLngsHistory.addAll(latLngs);
    }


    private int timeCompareBig(String firstTime,String lastTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        Date firstDate = null;
        Date lastDate = null;
        try {
            firstDate = df.parse(firstTime);
            lastDate = df.parse(lastTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(firstDate.getTime() < lastDate.getTime()){
            return 1;
        }else{
            return 0;
        }
    }

}
