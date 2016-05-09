package com.expressba.express.sorter.open.ep_search.package_list;

import android.app.Activity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.expressba.express.main.MyApplication;
import com.expressba.express.model.Package;
import com.expressba.express.net.VolleyHelper;
import com.expressba.express.R;

/**
 * Created by 黎明 on 2016/4/26.
 */
public class PackageListModelImpl extends VolleyHelper implements PackageListModel {
    private PackageListPresenter PackageListPresenter;
    String onSearchPByPackageIDurl;

    public PackageListModelImpl(Activity activity, PackageListPresenter PackageListPresenter) {
        super(activity);
        this.PackageListPresenter = PackageListPresenter;
        onSearchPByPackageIDurl = activity.getResources().getString(R.string.base_url) + activity.getResources().getString(R.string.searchPackageInPackage_ById);
    }

    @Override
    public void onSearchPByPackageID(String packageID) {
        onSearchPByPackageIDurl += packageID;
        JSONObject jsonObject = new JSONObject();
        try {
            doJson(onSearchPByPackageIDurl, VolleyHelper.GET, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onError(String errorMessage) {
        PackageListPresenter.onFail(errorMessage);
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONArray jsonArray = (JSONArray) jsonOrArray;
        List<Package> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object=(JSONObject)jsonArray.get(i);
                Package p=new Package();
                p.setEmployeesId(object.getInt("employeesId"));
                p.setId(object.getString("Id"));
                p.setTime(object.getString("time"));
                list.add(p);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        PackageListPresenter.onPackageSuccess(list);
    }
}



