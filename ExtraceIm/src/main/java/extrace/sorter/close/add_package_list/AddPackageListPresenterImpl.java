package extrace.sorter.close.add_package_list;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import extrace.main.MyApplication;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/30.
 */
public class AddPackageListPresenterImpl extends VolleyHelper implements AddPackageListPresenter {
    private AddPackageListFragmentView add_packageListFragmentView;
    String url = "";
    String token;

    public AddPackageListPresenterImpl(Activity activity, AddPackageListFragmentView AddPackageListFragmentView) {
        super(activity);
        token = ((MyApplication) activity.getApplication()).getToken();
        this.add_packageListFragmentView = AddPackageListFragmentView;
        url = activity.getResources().getString(R.string.base_url);
    }

    @Override
    public void loadIntoPackage(String packageID, String ID, int isPackage) {
        url += "/REST/Domain/loadIntoPackage/packageId/" + packageID + "/id/" + ID + "/isPackage/" + isPackage + "/" + token;
        JSONObject object = new JSONObject();
        try {
            doJson(url, VolleyHelper.GET, object);
        } catch (Exception e) {
            e.printStackTrace();
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
    }


}