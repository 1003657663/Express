package extrace.sorter.open.ep_search.express_list;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import extrace.model.ExpressInfo;

import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/26.
 */
public class ExpressListModelImpl extends VolleyHelper implements ExpressListModel {
    private ExpressListPresenter ExpressListPresenter;
   String onSearchEByPackageIDurl,onOpenurl;

    public ExpressListModelImpl(Activity activity, ExpressListPresenter ExpressListPresenter) {
        super(activity);
        this.ExpressListPresenter = ExpressListPresenter;
        onSearchEByPackageIDurl =activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.searchExpressInPackage_ById);
      //  onOpenurl=activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.getPackageInfo_ById);
    }


    @Override
    public void onSearchEByPackageID(String packageID) {
        onSearchEByPackageIDurl+=packageID;
        JSONArray jsonArray=new JSONArray();
        try {
            doJsonArray(onSearchEByPackageIDurl, VolleyHelper.GET, jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String errorMessage) {
        ExpressListPresenter.onFail(errorMessage);
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject object = (JSONObject) jsonOrArray;
        try {
            int state = object.getInt("state");
            if (state == 1) {
                ExpressListPresenter.onSuccess();}
        } catch (Exception e1) {
            JSONArray jsonArray = (JSONArray) jsonOrArray;
            List<ExpressInfo> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                ExpressInfo p = new ExpressInfo();
                try {
                    JSONObject object1 = (JSONObject) jsonArray.get(i);
                    p.setGetTime(object1.getString("gettime"));
                    p.setSadd(object1.getString("sadd"));
                    p.setRadd(object1.getString("radd"));
                    list.add(p);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ExpressListPresenter.onFail("error");
                }

            }
            ExpressListPresenter.onSuccess(list);
        }
    }

    @Override
    public void onOpen(String packageID) {
        onOpenurl += packageID;
        try {
            doJson(onOpenurl, VolleyHelper.GET, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
