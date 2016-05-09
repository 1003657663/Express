package com.expressba.express.sorter.work;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.expressba.express.main.MyApplication;
import com.expressba.express.model.ExpressEntity;
import com.expressba.express.net.VolleyHelper;
import com.expressba.express.R;

/**
 * Created by 黎明 on 2016/5/7.
 */
public class SearchWorkPresenterImpl extends VolleyHelper implements SearchWorkPresenter {
    private SearchWorkFragmentView fragmentview;
    private String turl, url;

    public SearchWorkPresenterImpl(Activity activity, SearchWorkFragmentView fragmentView) {
        super(activity);
        this.fragmentview = fragmentView;
        turl = activity.getResources().getString(R.string.base_url);
        url = turl;
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONArray jsonArray = (JSONArray) jsonOrArray;
        ExpressEntity expressEntity = new ExpressEntity();
        List<ExpressEntity> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = (JSONObject) jsonArray.get(i);
                expressEntity.setId(object.getString("id"));
                expressEntity.setGetTime(object.getString("getTime"));
                expressEntity.setOutTime(object.getString("outTime"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.add(expressEntity);
        }
        fragmentview.onSuccess(list);
    }

    @Override
    public void searchWork(int EmployeesID, String start, int day) {
        turl += "/REST/Domain/getWork/employeeId/" + EmployeesID + "/starttime/" + start + "/days/" + day ;
        try {
            doJsonArray(turl, VolleyHelper.GET, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            url = turl;
        }
    }

    @Override
    public void onError(String errorMessage) {
        url = turl;
        fragmentview.onError(errorMessage);
    }
}
