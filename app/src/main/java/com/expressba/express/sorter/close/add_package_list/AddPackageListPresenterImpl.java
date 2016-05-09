package com.expressba.express.sorter.close.add_package_list;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.expressba.express.net.VolleyHelper;
import com.expressba.express.R;

/**
 * Created by 黎明 on 2016/4/30.
 */
public class AddPackageListPresenterImpl extends VolleyHelper implements AddPackageListPresenter {
    private AddPackageListFragmentView add_packageListFragmentView;
    String url = "",turl;

    public AddPackageListPresenterImpl(Activity activity, AddPackageListFragmentView AddPackageListFragmentView) {
        super(activity);
        this.add_packageListFragmentView = AddPackageListFragmentView;
        turl = activity.getResources().getString(R.string.base_url);
        url=turl;
    }

    @Override
    public void loadIntoPackage(String packageID, String ID, int isPackage) {
        url += "/REST/Domain/loadIntoPackage/packageId/" + packageID + "/id/" + ID + "/isPackage/" + isPackage;
        JSONObject object = new JSONObject();
        try {
            doJson(url, VolleyHelper.GET, object);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            url=turl;
        }
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        try {
            if (jsonObject.getInt("state") == 1)
                add_packageListFragmentView.Success();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String errorMessage) {
        add_packageListFragmentView.Fail(errorMessage);
        url=turl;
    }


}