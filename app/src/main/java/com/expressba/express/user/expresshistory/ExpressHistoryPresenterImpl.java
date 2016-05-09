package com.expressba.express.user.expresshistory;

import android.app.Activity;

import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.expressba.express.main.MyApplication;
import com.expressba.express.model.ExpressInfo;
import com.expressba.express.net.VolleyHelper;
import com.expressba.express.R;

/**
 * Created by songchao on 16/5/8.
 */
public class ExpressHistoryPresenterImpl extends VolleyHelper implements ExpressHistoryPresenter{

    public static final int HISTORY_SEND = 0;
    public static final int HISTORY_RECEIVE = 1;

    private int sendOrReceive;
    private String url;
    private MyApplication application;

    ExpressHistoryView expressHistoryView;
    public ExpressHistoryPresenterImpl(Activity context,ExpressHistoryView expressHistoryView,int sendOrReceive) {
        super(context);
        this.expressHistoryView = expressHistoryView;
        application = (MyApplication) context.getApplication();
        this.sendOrReceive = sendOrReceive;
        String baseUrl = context.getResources().getString(R.string.base_url);
        if(sendOrReceive == HISTORY_SEND){
            url = baseUrl + context.getResources().getString(R.string.user_express_history_send);
        }else{
            url = baseUrl + context.getResources().getString(R.string.user_express_history_receive);
        }
    }

    @Override
    public void onGetData() {
        url = url.replace("{CustomerId}",application.getUserInfo().getId()+"");
        doJsonArray(url, VolleyHelper.GET,null);
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONArray jsonArray = (JSONArray) jsonOrArray;
        ArrayList<ExpressInfo> expressInfos = new ArrayList<>();
        try {
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ExpressInfo expressInfo = new ExpressInfo();
                expressInfo.setAcc1(jsonObject.getString("acc1"));
                expressInfo.setAcc2(jsonObject.getString("acc2"));
                expressInfo.setGetTime(jsonObject.getString("getTime"));
                expressInfo.setID(jsonObject.getString("ID"));
                expressInfo.setInsuFee(jsonObject.getDouble("insuFee"));
                expressInfo.setOutTime(jsonObject.getString("outTime"));
                expressInfo.setRadd(jsonObject.getString("sadd"));
                expressInfo.setRaddinfo(jsonObject.getString("raddinfo"));
                expressInfo.setSadd(jsonObject.getString("sadd"));
                expressInfo.setSaddinfo(jsonObject.getString("saddinfo"));
                expressInfo.setRname(jsonObject.getString("rname"));
                expressInfo.setSname(jsonObject.getString("sname"));
                expressInfo.setRtel(jsonObject.getString("rtel"));
                expressInfo.setStel(jsonObject.getString("stel"));
                expressInfo.setWeight(jsonObject.getDouble("weight"));

                expressInfos.add(expressInfo);
            }
            expressHistoryView.onSuccess(expressInfos);//回调成功接口
        } catch (JSONException e) {
            e.printStackTrace();
            onError("解析数据出错，请重试");
        }
    }

    @Override
    public void onError(String errorMessage) {
        expressHistoryView.showMessage(errorMessage);
    }
}
