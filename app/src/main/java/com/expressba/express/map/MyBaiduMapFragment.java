package com.expressba.express.map;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.expressba.express.R;
import com.expressba.express.main.UIFragment;
import com.expressba.express.map.model.MyLatLng;
import com.expressba.express.map.toolbox.MapToastView;
import com.expressba.express.model.EmployeeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songchao on 16/5/17.
 */
public class MyBaiduMapFragment extends UIFragment implements MyBaiduMapView{

    private BaiduMap mBaiduMap;
    private MapView mapView;
    private Bundle bundle;
    private MyBaiduMapPresenterImpl myBaiduMapPresenterImpl;
    private ArrayList<String> entityNames;
    private String expressID;


    public static MyBaiduMapFragment newInstance(Bundle bundle,String expressID) {
        MyBaiduMapFragment fragment = new MyBaiduMapFragment();
        if(bundle!=null) {
            bundle.putString("expressid",expressID);
        }else{
            bundle = new Bundle();
            bundle.putString("expressid",expressID);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setBundle(Bundle bundle,String expressID) {
        this.bundle = bundle;
        this.expressID = expressID;
        getEmployees(expressID);
        //onGetEmployeeSuccess(null);
    }

    public Bundle getBundle(){
        return this.bundle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //SDKInitializer.initialize(getActivity().getApplicationContext());//初始化传入application
        View view = inflater.inflate(R.layout.baidu_map,container,false);
        mapView = (MapView) view.findViewById(R.id.baidu_map_view);
        mBaiduMap = mapView.getMap();
        initMap();//初始化生成mapView
        getBundleData();
        myBaiduMapPresenterImpl = new MyBaiduMapPresenterImpl(getActivity(),this);
        initTraceThread();
        getEmployees(expressID);
        //onGetEmployeeSuccess(null);

        return view;
    }

    /**
     * 获取快递经手的员工id
     * @param expressID
     */
    private void getEmployees(String expressID){
        myBaiduMapPresenterImpl.startGetEmployees(expressID);
    }


    /**
     * 开启异步任务循环获取实时路径信息
     */
    AsyncTask asyncTask;
    private final long time = 10;
    private void initTraceThread(){
        asyncTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                myBaiduMapPresenterImpl.startGetAllTrace(entityNames,true);
            }

            @Override
            protected Object doInBackground(Object[] params) {
                while (true) {
                    try {
                        Thread.sleep(time*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        onError("线程sleep失败");
                    }
                    myBaiduMapPresenterImpl.startGetAllTrace(entityNames,false);
                }
            }

        };
    }

    @Override
    public void onError(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }


    /**
     * 初始化地图获取map和view类
     */
    private void initMap(){
        MapStatus ms = new MapStatus.Builder().overlook(-20).zoom(15).build();
        MapStatusUpdate msU = MapStatusUpdateFactory.newMapStatus(ms);
        mBaiduMap.setMapStatus(msU);
    }

    /**
     * 从argument中取出bundle
     */
    private void getBundleData(){
        if(getBundle() == null){
            bundle = getArguments();
        }
        if(bundle != null){
            expressID = bundle.getString("expressid");
        }
    }


    @Override
    public void getAllTraceCallBack(List<MyLatLng> latLngs) {
        List<LatLng> latLngList = new ArrayList<>();
        for(int i=0;i<latLngs.size();i++){//转换自定义经纬度类到官方类
            latLngList.add(new LatLng(latLngs.get(i).latitude,latLngs.get(i).longitude));
        }
        drawHistoryTrack(latLngList);
    }

    @Override
    public void onGetEmployeeSuccess(ArrayList<EmployeeInfo> employeeInfos) {
        ArrayList<String> entityNames = new ArrayList<>();
        EmployeeInfo employeeInfo;
        if(entityNames!=null && entityNames.size()>0) {
            for (int i = 0; i < employeeInfos.size(); i++) {
                employeeInfo = employeeInfos.get(i);
                if (employeeInfo.getJob() == EmployeeInfo.KUAIDI_EMPLOYEE || employeeInfo.getJob() == EmployeeInfo.SIJI_EMPLOYEE) {
                    entityNames.add(String.valueOf(employeeInfo.getEmployeeId()));
                }
            }
        }

        /*//-----test
        entityNames.add("100");
        entityNames.add("101");
        entityNames.add("102");
        entityNames.add("103");
        //---------*/
        this.entityNames = entityNames;
        startAsyncTask();
    }


    /**
     * 绘制历史轨迹
     * @param points
     */
    MapStatusUpdate msUpdate;
    MapStatusUpdate nextUpdate;
    PolylineOptions polyline;
    BitmapDescriptor bmStart,bmEnd;
    MarkerOptions startMarker,endMarker;
    public void drawHistoryTrack(List<LatLng> pointss) {
        final List<LatLng> points = pointss;
        // 绘制新覆盖物前，清空之前的覆盖物
        mBaiduMap.clear();
        MapStatus preMapStatus = mBaiduMap.getMapStatus();
        if (points == null || points.size() == 0) {
            Looper.prepare();
            Toast.makeText(getActivity(), "当前查询无轨迹点", Toast.LENGTH_SHORT).show();
            Looper.loop();
            //resetMarker();
        } else if (points.size() > 1) {

            LatLng llC = points.get(0);
            LatLng llD = points.get(points.size() - 1);
            LatLngBounds bounds = new LatLngBounds.Builder()
                    .include(llC).include(llD).build();

            msUpdate = MapStatusUpdateFactory.newLatLngBounds(bounds);

            //起点和终点图标
            if(bmStart ==null) {
                Bitmap bitmap = getMapToastBitmap("起点",getResources().getDrawable(R.mipmap.storehouse));
                bmStart = BitmapDescriptorFactory.fromBitmap(bitmap);
            }
            if(bmEnd == null){
                Bitmap bitmap = getMapToastBitmap("终点",getResources().getDrawable(R.mipmap.temphouse));
                bmEnd = BitmapDescriptorFactory.fromBitmap(bitmap);
            }

            // 添加起点图标
            startMarker = new MarkerOptions()
                    .position(points.get(points.size()-1)).icon(bmStart)
                    .zIndex(9).draggable(true);

            // 添加终点图标
            endMarker = new MarkerOptions().position(points.get(0))
                    .icon(bmEnd).zIndex(9).draggable(true);

            // 添加路线（轨迹）
            polyline = new PolylineOptions().width(10)
                    .color(Color.BLUE).points(points);

            MapStatus.Builder builder = new MapStatus.Builder();
            builder.overlook(preMapStatus.overlook);
            builder.rotate(preMapStatus.rotate);
            builder.target(preMapStatus.target);
            builder.targetScreen(preMapStatus.targetScreen);
            builder.zoom(preMapStatus.zoom);
            MapStatus mapStatus = builder.build();
            //new MapStatus(preMapStatus.rotate,preMapStatus.target,preMapStatus.overlook,preMapStatus.zoom,preMapStatus.targetScreen,bounds);
            nextUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);

            addMarker();

        }

    }

    /**
     * 获取起始地点的view转换的图片
     */
    private Bitmap getMapToastBitmap(String text, Drawable image){
        View view = View.inflate(getActivity(),R.layout.map_toast,null);
        ((TextView)view.findViewById(R.id.map_toast_text)).setText(text);
        ((ImageView)view.findViewById(R.id.map_toast_image)).setImageDrawable(image);

        return MapToastView.getViewBitmap(view);
    }



    /**
     * 添加覆盖物
     */
    Boolean hasSetBound = false;
    public void addMarker() {
        if (null != msUpdate) {
            if(!hasSetBound) {
                mBaiduMap.setMapStatus(msUpdate);
                hasSetBound = true;
            }else {
                mBaiduMap.setMapStatus(nextUpdate);
            }
        }

        if (null != startMarker) {
            mBaiduMap.addOverlay(startMarker);
        }

        if (null != endMarker) {
            mBaiduMap.addOverlay(endMarker);
        }

        if (null != polyline) {
            mBaiduMap.addOverlay(polyline);
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            stopAsyncTask();
        }else {
            startAsyncTask();
        }
    }

    /**
     * 停止循环异步获取轨迹
     */
    private void stopAsyncTask(){
        if(asyncTask != null && asyncTask.getStatus() != AsyncTask.Status.FINISHED){
            asyncTask.cancel(true);
        }
    }

    /**
     * 启动异步获取轨迹点任务
     */
    private void startAsyncTask(){
        if(entityNames!=null) {
            if (asyncTask != null) {
                if (asyncTask.getStatus() == AsyncTask.Status.FINISHED) {
                    asyncTask.execute();
                } else if (asyncTask.getStatus() != AsyncTask.Status.RUNNING) {
                    asyncTask.execute();
                }
            } else {
                initTraceThread();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAsyncTask();
        mapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAsyncTask();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        startAsyncTask();
        mapView.onResume();
    }
}
