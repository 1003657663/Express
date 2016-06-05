package com.expressba.expressuser.map;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.expressba.expressuser.R;
import com.expressba.expressuser.main.UIFragment;
import com.expressba.expressuser.map.model.MyLatLng;
import com.expressba.expressuser.map.toolbox.MapToastView;
import com.expressba.expressuser.model.EmployeeInfo;
import com.expressba.expressuser.user.search.SearchExpressFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songchao on 16/5/17.
 */
public class MyBaiduMapFragment extends UIFragment implements MyBaiduMapView{

    private BaiduMap mBaiduMap;
    private MapView mapView;
    private MyBaiduMapPresenterImpl myBaiduMapPresenterImpl;
    private ArrayList<String> entityNames;
    private String expressID;
    private ImageButton dingweiButton;
    private ImageButton backButton;
    private Integer nowState;


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

    @Override
    protected void onStartHandlerView(View view) {
        super.onStartHandlerView(view);
        if(expressID!=null) {
            getEmployees(expressID);
        }
    }

    public void setBundle(Bundle bundle, String expressID) {
        this.expressID = expressID;
        super.setBundle(bundle);
    }

    protected void handlerIfBundle(Bundle bundle){
        nowState = bundle.getInt("nowstate");
        expressID = bundle.getString("expressid");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.baidu_map,container,false);
        mapView = (MapView) view.findViewById(R.id.baidu_map_view);
        mBaiduMap = mapView.getMap();
        dingweiButton = (ImageButton) view.findViewById(R.id.baidu_map_dingwei);
        backButton = (ImageButton) view.findViewById(R.id.top_bar_left_img);
        ((TextView)view.findViewById(R.id.top_bar_center_text)).setText("物流跟踪路径");
        initDingweiListener();
        initMap();//初始化生成mapView
        myBaiduMapPresenterImpl = new MyBaiduMapPresenterImpl(getActivity(),this);
        initTraceThread();
        return view;
    }

    @Override
    protected void onBack() {
        getFragmentManager().popBackStackImmediate();
    }

    /**
     * 初始化定位按钮监听，点击之后地图轨迹回到屏幕中心
     */
    private void initDingweiListener(){
        dingweiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasSetBound = false;
                drawHistoryTrack(points);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });
    }

    /**
     * 让地图轨迹加载暂停一定时间
     * @param time
     */
    private void delay(final int time){
        AsyncTask task = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                stopAsyncTask();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Thread.sleep(time*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startAsyncTask();
                this.cancel(true);
                return null;
            }
        };
        task.execute();
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
        MapStatus ms = new MapStatus.Builder().overlook(0).build();
        MapStatusUpdate msU = MapStatusUpdateFactory.newMapStatus(ms);
        mBaiduMap.setMapStatus(msU);
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
        if(employeeInfos!=null && employeeInfos.size()>0) {
            for (int i = 0; i < employeeInfos.size(); i++) {
                employeeInfo = employeeInfos.get(i);
                if (employeeInfo.getJob() == EmployeeInfo.KUAIDI_EMPLOYEE || employeeInfo.getJob() == EmployeeInfo.SIJI_EMPLOYEE) {
                    entityNames.add(String.valueOf(employeeInfo.getEmployeeId()));
                }
            }
        }
        this.entityNames = entityNames;
        startAsyncTask();
    }


    /**
     * 绘制历史轨迹
     * @param points
     */
    private MapStatusUpdate msUpdate;
    private MapStatusUpdate nextUpdate;
    private PolylineOptions polyline;
    private BitmapDescriptor bmStart,bmEnd;
    private MarkerOptions startMarker,endMarker;
    private List<LatLng> points;//点备份
    public void drawHistoryTrack(final List<LatLng> points) {
        // 绘制新覆盖物前，清空之前的覆盖物
        this.points = points;
        mBaiduMap.clear();
        MapStatus preMapStatus = mBaiduMap.getMapStatus();
        if (points == null || points.size() == 0) {
            Toast.makeText(getActivity(), "当前查询无轨迹点", Toast.LENGTH_SHORT).show();
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
                String endStr = "终点";
                if(nowState!=null){
                    if(nowState != SearchExpressFragment.QIAN_SHOW){
                        endStr = "当前";
                    }
                }
                Bitmap bitmap = getMapToastBitmap(endStr,getResources().getDrawable(R.mipmap.temphouse));
                bmEnd = BitmapDescriptorFactory.fromBitmap(bitmap);
            }

            // 添加起点图标,如果已经加载了，就不需要动画
            if(hasSetBound){
                startMarker = new MarkerOptions()
                        .position(points.get(points.size() - 1)).icon(bmStart)
                        .zIndex(9).draggable(true).animateType(MarkerOptions.MarkerAnimateType.none);

                // 添加终点图标
                endMarker = new MarkerOptions().position(points.get(0))
                        .icon(bmEnd).zIndex(9).draggable(true).animateType(MarkerOptions.MarkerAnimateType.none);
            }else {
                startMarker = new MarkerOptions()
                        .position(points.get(points.size() - 1)).icon(bmStart)
                        .zIndex(9).draggable(true).animateType(MarkerOptions.MarkerAnimateType.grow);

                // 添加终点图标
                endMarker = new MarkerOptions().position(points.get(0))
                        .icon(bmEnd).zIndex(9).draggable(true).animateType(MarkerOptions.MarkerAnimateType.grow);
            }
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
