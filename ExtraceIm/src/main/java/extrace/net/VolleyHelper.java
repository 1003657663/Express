package extrace.net;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public VolleyHelper(Activity context){
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void doJson(String url,int method,JSONObject jsonObject){
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

    public void doJsonArray(String url,Integer postOrGet,JSONArray jsonObject){
        showProgressDialog();
        MyJsonArrayRequest arrayRequest = new MyJsonArrayRequest(postOrGet,url,jsonObject,new Response.Listener<JSONArray>() {
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
