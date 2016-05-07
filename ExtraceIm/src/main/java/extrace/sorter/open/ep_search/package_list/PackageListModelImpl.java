package extrace.sorter.open.ep_search.package_list;

import android.app.Activity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import extrace.main.MyApplication;
import extrace.model.Package;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/26.
 */
public class PackageListModelImpl extends VolleyHelper implements PackageListModel {
    private PackageListPresenter PackageListPresenter;
    String token, onSearchPByPackageIDurl;

    public PackageListModelImpl(Activity activity, PackageListPresenter PackageListPresenter) {
        super(activity);
        this.PackageListPresenter = PackageListPresenter;
        token = ((MyApplication) activity.getApplication()).getToken();
        onSearchPByPackageIDurl = activity.getResources().getString(R.string.base_url) + activity.getResources().getString(R.string.searchPackageInPackage_ById);
    }

    @Override
    public void onSearchPByPackageID(String packageID) {
        onSearchPByPackageIDurl += packageID + "/" + token;
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
            Package p = null;
            try {
                p = (Package) jsonArray.get(i);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            list.add(p);
        }
        PackageListPresenter.onPackageSuccess(list);
    }
}



