package extrace.sorter.close.new_package_info;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import extrace.main.MyApplication;
import extrace.model.PackageInfo;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/28.
 */
public class NewPackageInfoPresenterImpl extends VolleyHelper implements NewPackageInfoPresenter {
    private NewPackageInfoFragmentView fragmentView1;
    String url, turl;
    String token;

    public NewPackageInfoPresenterImpl(Activity activity, NewPackageInfoFragmentView fragmentView2) {
        super(activity);
        this.fragmentView1 = fragmentView2;
        token = ((MyApplication) activity.getApplication()).getToken();
        turl = activity.getResources().getString(R.string.base_url) + activity.getResources().getString(R.string.createPackage);
        // turl="http://192.168.1.113:8080"+activity.getResources().getString(R.string.createPackage);
        url = turl;
    }

    @Override
    public void newPackage(int fromID, int toID, int employeesId, int isSorter) {
        url += "fromID/" + fromID + "/toID/" + toID + "/employeesID/" + employeesId + "/isSorter/" + isSorter + "/" + token;
        try {
            doJson(url, VolleyHelper.GET, null);
        } catch (Exception e) {
            e.printStackTrace();
            fragmentView1.onFail();
        }
    }

    @Override
    public void onError(String errorMessage) {
        fragmentView1.onFail();
        url = turl;
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        PackageInfo packageInfo = new PackageInfo();
        try {
            packageInfo.setId(jsonObject.getString("id"));
            packageInfo.setCloseTime(jsonObject.getString("closeTime"));
            fragmentView1.onSuccess(packageInfo);

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            url = turl;
        }
    }
}

