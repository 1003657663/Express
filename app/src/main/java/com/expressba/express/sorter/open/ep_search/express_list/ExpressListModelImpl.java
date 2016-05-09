package com.expressba.express.sorter.open.ep_search.express_list;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.expressba.express.main.MyApplication;
import com.expressba.express.model.ExpressInfo;
import com.expressba.express.net.VolleyHelper;
import com.expressba.express.R;

/**
 * Created by 黎明 on 2016/4/26.
 */
public class ExpressListModelImpl extends VolleyHelper implements ExpressListModel {
    private ExpressListPresenter ExpressListPresenter;
    String onSearchEByPackageIDurl;

    public ExpressListModelImpl(Activity activity, ExpressListPresenter ExpressListPresenter) {
        super(activity);
        this.ExpressListPresenter = ExpressListPresenter;
        onSearchEByPackageIDurl = activity.getResources().getString(R.string.base_url) + activity.getResources().getString(R.string.searchExpressInPackage_ById);
        //  onOpenurl=activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.getPackageInfo_ById);
    }


    @Override
    public void onSearchEByPackageID(String packageID) {
        onSearchEByPackageIDurl += packageID;
        JSONArray jsonArray = new JSONArray();
        try {
            doJsonArray(onSearchEByPackageIDurl, VolleyHelper.GET, jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String errorMessage) {
        ExpressListPresenter.onExpressListFail(errorMessage);
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONArray jsonArray = (JSONArray) jsonOrArray;
        List<ExpressInfo> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            ExpressInfo p = new ExpressInfo();
            try {
                JSONObject object1 = (JSONObject) jsonArray.get(i);
                p.setID(object1.getString("id"));
                p.setRname(object1.getString("rname"));
                p.setRtel(object1.getString("rtel"));
                p.setRaddinfo(object1.getString("raddinfo"));
                p.setRadd(object1.getString("accAddressId"));
                String gettime = object1.getString("getTime").substring(0, 10);
                p.setGetTime(gettime);
                list.add(p);
            } catch (JSONException e) {
                e.printStackTrace();
                ExpressListPresenter.onExpressListFail("error");
            }

        }
        ExpressListPresenter.onSuccess(list);
    }


}
