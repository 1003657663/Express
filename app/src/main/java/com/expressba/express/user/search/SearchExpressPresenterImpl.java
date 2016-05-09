package com.expressba.express.user.search;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.expressba.express.model.ExpressInfo;
import com.expressba.express.model.ExpressSearchInfo;
import com.expressba.express.net.VolleyHelper;
import com.expressba.express.R;

/**
 * Created by songchao on 16/5/1.
 */
public class SearchExpressPresenterImpl extends VolleyHelper implements SearchExpressPresenter{

    private SearchExpressView searchExpressView;
    private String searchUrl;

    public SearchExpressPresenterImpl(Activity context,SearchExpressView searchExpressView) {
        super(context);
        this.searchExpressView = searchExpressView;
        String baseUrl = context.getResources().getString(R.string.base_url);
        searchUrl = baseUrl + context.getResources().getString(R.string.express_search_one);
    }

    @Override
    public void startGetExpressInfo(String expressID) {
        searchUrl = searchUrl.replace("{id}",expressID);
        doJsonArray(searchUrl,VolleyHelper.GET,null);
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONArray jsonArray = (JSONArray) jsonOrArray;
        ArrayList<ExpressSearchInfo> expressSearchInfos = new ArrayList<>();
        try {
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ExpressSearchInfo expressSearchInfo = new ExpressSearchInfo();
                expressSearchInfo.setInfo(jsonObject.getString("info"));
                expressSearchInfo.setState(jsonObject.getInt("state"));
                expressSearchInfo.setTime(jsonObject.getString("time"));
                expressSearchInfos.add(expressSearchInfo);
            }
            searchExpressView.onRequestSuccess(expressSearchInfos);
        } catch (JSONException e) {
            e.printStackTrace();
            searchExpressView.onError("快递状态出错");
        }

    }

    @Override
    public void onError(String errorMessage) {
        searchExpressView.onError(errorMessage);
    }
}
