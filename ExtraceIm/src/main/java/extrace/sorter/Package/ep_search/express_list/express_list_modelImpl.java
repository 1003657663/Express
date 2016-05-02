package extrace.sorter.Package.ep_search.express_list;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import extrace.model.ExpressInfo;

import extrace.net.VolleyHelper;

/**
 * Created by 黎明 on 2016/4/26.
 */
public class Express_list_modelImpl extends VolleyHelper implements Express_list_model {
    private Express_list_presenter Express_list_presenter;
    String url = "";

    public Express_list_modelImpl(Activity activity, Express_list_presenter Express_list_presenter) {
        super(activity);
        this.Express_list_presenter = Express_list_presenter;
    }


    @Override
    public void onSearchEByPackageID(String packageID) {
        JSONObject object = new JSONObject();
        url += "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("packageID", packageID);
            doJson(url, VolleyHelper.POST, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            Express_list_presenter.onFail();
        }
    }

    @Override
    public void onError(String errorMessage) {
        Express_list_presenter.onFail();
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        try {
            JSONObject jsonObject = (JSONObject) jsonOrArray;
            if (jsonObject.getInt("state") == 1) {
                Express_list_presenter.onSuccess();
            } else {
                JSONArray jsonArray = (JSONArray) jsonOrArray;
                try {
                    List<ExpressInfo> list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        ExpressInfo p = new ExpressInfo();
                        p.setID(object.getString("ID"));
                        p.setGetTime(object.getString("gettime"));
                        p.setSadd(object.getString("sadd"));
                        p.setRadd(object.getString("radd"));
                        list.add(p);
                    }
                    Express_list_presenter.onSuccess(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Express_list_presenter.onFail();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Express_list_presenter.onFail();
        }
    }

    @Override
    public void onOpen(String packageID) {
        url += "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("packageID", packageID);
            doJson(url, VolleyHelper.POST, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            Express_list_presenter.onFail();
        }
    }
}
