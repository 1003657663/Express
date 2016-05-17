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

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapFragment;
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
import com.expressba.express.map.toolbox.MapToastView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songchao on 16/5/17.
 */
public class MyBaiduMapFragment extends UIFragment implements GetAllTrace.GetAllTraceInterface{

    private BaiduMap mBaiduMap;
    private MapView mapView;
    private Bundle bundle;
    private GetAllTrace getAllTrace;
    private String entityName;


    public static MyBaiduMapFragment newInstance(Bundle bundle) {
        MyBaiduMapFragment fragment = new MyBaiduMapFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        SDKInitializer.initialize(getActivity().getApplicationContext());//初始化传入application
        initMap();//初始化生成mapView
        mBaiduMap = mapView.getMap();
        getAllTrace = new GetAllTrace(getActivity(),entityName);
        getBundleData();
        getTraceThread();
        return mapView;
    }

    AsyncTask asyncTask;
    private final long time = 60*2;
    private void getTraceThread(){
        asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                while (true) {
                    getAllTrace.startGetAllTrace();
                    try {
                        Thread.sleep(time*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        onError("线程sleep失败");
                    }
                }
            }

        };
    }

    private void onError(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }


    /**
     * 初始化地图获取map和view类
     */
    private void initMap(){
        BaiduMapOptions options = new BaiduMapOptions();
        //options.mapStatus();//设置起始点和缩放级别
        options.overlookingGesturesEnabled(false);//不允许俯仰手势
        options.scaleControlEnabled(true);//显示比例尺控件
        options.zoomControlsEnabled(true);//支持缩放控件显示
        options.mapType(BaiduMap.MAP_TYPE_NORMAL);
        mapView = new MapView(getActivity(),options);
    }

    /**
     * 从argument中取出bundle
     */
    private void getBundleData(){
        if(getBundle() == null){
            bundle = getArguments();
        }
        if(bundle != null){
            entityName = bundle.getString("entityname");
        }
    }


    @Override
    public void getAllTraceCallBack(List<LatLng> latLngs) {
        drawHistoryTrack(latLngs);
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
                    .position(points.get(points.size() - 1)).icon(bmStart)
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

}
