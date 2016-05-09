package extrace.sorter.open.package_search;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import extrace.main.MyApplication;
import extrace.model.PackageInfo;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/25.
 */
public class PackageSearchModelImpl extends VolleyHelper implements PackageSearchModel {
    private PackageSearchPresenter package_searchPresenter;
    String url, turl;

    public PackageSearchModelImpl(Activity activity, PackageSearchPresenter PackageSearchPresenter) {
        super(activity);
        this.package_searchPresenter = PackageSearchPresenter;
        turl = activity.getResources().getString(R.string.base_url) + activity.getResources().getString(R.string.getPackageInfo_ById);
        url = turl;
    }

    @Override
    public void openPackage(String packageID) {
        // url+=packageID;
        url += "01908141641757" ;
        JSONObject jsonObject = new JSONObject();
        try {
            doJson(url, VolleyHelper.GET, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        try {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.setCloseTime(jsonObject.getString("closeTime"));
            packageInfo.setPackageFrom(jsonObject.getString("packageFrom"));
            packageInfo.setPackageTo(jsonObject.getString("packageTo"));
            packageInfo.setId(jsonObject.getString("id"));
            packageInfo.setEmployeesID(Integer.parseInt(jsonObject.getString("employeesID")));
            packageInfo.setEmployeesName(jsonObject.getString("employeesName"));

            package_searchPresenter.onSuccess(packageInfo);
        } catch (JSONException e) {
            e.printStackTrace();
            package_searchPresenter.onFail("error");
        }
    }

    @Override
    public void onError(String errorMessage) {
        package_searchPresenter.onFail(errorMessage);
    }
}
