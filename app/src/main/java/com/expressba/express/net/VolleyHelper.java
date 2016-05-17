package com.expressba.express.net;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.*;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import com.expressba.express.main.MyApplication;
import com.expressba.express.model.UserInfo;

/**
 * Created by chao on 2016/4/17.
 */
public abstract class VolleyHelper {
    private RequestQueue requestQueue;
    public static final int POST = JsonObjectRequest.Method.POST;
    public static final int GET = JsonObjectRequest.Method.GET;
    private Context context;
    private ProgressDialog dialog;
    private Boolean isShowProgress = true;
    private String token;
    private Boolean isLogin = false;

    public VolleyHelper(Activity context){
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        UserInfo userInfo = ((MyApplication)context.getApplication()).getUserInfo();
        isLogin = userInfo.getLoginState();
        if(isLogin){
            token = userInfo.getToken();
        }
    }

    public void doJson(String url,int method,JSONObject jsonObject){
        //添加token
        url = initUrl(url,method,jsonObject);
        showProgressDialog();
        JsonObjectRequest objectRequest = new JsonObjectRequest(method, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                hideProgressDialog();
                onDataReceive(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hideProgressDialog();
                onError(VolleyErrorHelper.getMessage(volleyError,context));
            }
        });
        requestQueue.add(objectRequest);
    }

    public void doJsonArray(String url,Integer postOrGet,JSONArray jsonArray){
        url = initUrl(url,postOrGet,null);
        showProgressDialog();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(postOrGet,url,jsonArray,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                hideProgressDialog();
                onDataReceive(jsonArray);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hideProgressDialog();
                onError(VolleyErrorHelper.getMessage(volleyError,context));
            }
        });
        requestQueue.add(arrayRequest);
    }


    /**
     * 请求图片的方法
     * @param url
     * @param bitmap
     */
    public void doImage(String url,Bitmap bitmap){

        showProgressDialog();
        Listener<Bitmap> listener = new Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                hideProgressDialog();

            }
        };

        ImageRequest imageRequest = new ImageRequest(url, listener, bitmap.getWidth(), bitmap.getHeight(), ImageView.ScaleType.FIT_XY, bitmap.getConfig(), new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                onError(VolleyErrorHelper.getMessage(error,context));
            }
        });

        requestQueue.add(imageRequest);
    }

    /**
     * url加密数据token带上-----其实没用
     * @param url
     * @param method
     * @param jsonObject
     * @return
     */
    private String initUrl(String url,int method ,JSONObject jsonObject){
        if(isLogin) {
            if(method == GET) {
                if (url.charAt(url.length() - 1) == '/') {
                    url = url + token;
                } else {
                    url = url + "/" + token;
                }
            }
        }
        return url;
    }


    private void showProgressDialog(){
        if(isShowProgress) {
            if (dialog == null) {
                dialog = new ProgressDialog(context);
                dialog.setMessage("请稍后..");
                dialog.show();
            } else {
                dialog.show();
            }
        }
    }


    private void hideProgressDialog(){
        if(isShowProgress) {
            if (dialog != null) {
                dialog.hide();
            }
        }
    }

    public abstract void onDataReceive(Object jsonOrArray);
    public abstract void onError(String errorMessage);


    public Boolean getShowProgress() {
        return isShowProgress;
    }

    public void setShowProgress(Boolean showProgress) {
        isShowProgress = showProgress;
    }
}
