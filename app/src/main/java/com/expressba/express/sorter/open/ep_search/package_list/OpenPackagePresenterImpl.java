package com.expressba.express.sorter.open.ep_search.package_list;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.expressba.express.net.VolleyHelper;
import com.expressba.express.R;

/**
 * Created by 黎明 on 2016/5/6.
 */
public class OpenPackagePresenterImpl extends VolleyHelper implements OpenPackagePresenter {
    private PackageListFragmentView fragmentView;
    private String url;

    public OpenPackagePresenterImpl(Activity activity, PackageListFragmentView fragmentView) {
        super(activity);
        this.fragmentView = fragmentView;
        url = activity.getResources().getString(R.string.base_url) + "/REST/Domain/OpenPackageByPackageId/";
    }

    @Override
    public void onOpenPackage(String packageID) {
        url += packageID ;
        try {
            doJson(url, VolleyHelper.GET, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject object = (JSONObject) jsonOrArray;
        int state = 0;
        try {
            state = object.getInt("state");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (state == 0) {
            fragmentView.OpenSuccess();
        } else if (state == 1) {
            fragmentView.onFail("error");
        }
    }

    @Override
    public void onError(String errorMessage) {
        fragmentView.onFail(errorMessage);
    }
}