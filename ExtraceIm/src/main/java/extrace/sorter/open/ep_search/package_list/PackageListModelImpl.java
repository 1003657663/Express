package extrace.sorter.open.ep_search.package_list;

import android.app.Activity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import extrace.model.Package;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/26.
 */
public class PackageListModelImpl extends VolleyHelper implements PackageListModel {
    private PackageListPresenter PackageListPresenter;
    String onOpenurl, onSearchPByPackageIDurl;

    public PackageListModelImpl(Activity activity, PackageListPresenter PackageListPresenter) {
        super(activity);
        this.PackageListPresenter = PackageListPresenter;
        onSearchPByPackageIDurl =  activity.getResources().getString(R.string.base_url) + activity.getResources().getString(R.string.searchPackageInPackage_ById);
       // onOpenurl = activity.getResources().getString(R.string.base_url) + activity.getResources().getString(R.string.getPackageInfo_ById);
    }

    /*@Override
    public void onOpen(String packageID) {
        onOpenurl += "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("packageID", packageID);
            doJson(onOpenurl, VolleyHelper.POST, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            PackageListPresenter.onFail();
        }
    }*/

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

    // @Override
    public void onError(String errorMessage) {
        PackageListPresenter.onFail(errorMessage);
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
       /* JSONObject object = (JSONObject) jsonOrArray;
        try {

            int state = object.getInt("state");
            if (state == 0) {
                PackageListPresenter.onSuccess();
            } else if (state == 1) {
                PackageListPresenter.onFail();
            }
        } catch (Exception e1) {*/
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
//}


