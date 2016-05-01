package extrace.sorter.close.new_package_info;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import extrace.model.packageInfo;
import extrace.net.VolleyHelper;

/**
 * Created by 黎明 on 2016/4/28.
 */
public class new_package_info_presenterImpl extends VolleyHelper implements new_package_info_presenter
{
    private new_package_info_fragmentView fragmentView;
    String url;
    public new_package_info_presenterImpl(Activity activity,new_package_info_fragmentView new_package_info_fragmentView)
    {
        super(activity);
        this.fragmentView =new_package_info_fragmentView;
    }

    @Override
    public void newPackage(int fromID, int toID, int employeesId) {
        url+="";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fromID", fromID);
            jsonObject.put("toID",toID);
            jsonObject.put("employeesID",employeesId);
            doJson(url, VolleyHelper.POST, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
           fragmentView.onFail();
        }
    }

    @Override
    public void onError(String errorMessage) {
        fragmentView.onFail();
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
            JSONObject jsonObject = (JSONObject) jsonOrArray;
            packageInfo packageInfo=new packageInfo();
        try {
            packageInfo.setID(jsonObject.getString("ID"));
            packageInfo.setClosetime(jsonObject.getString("closetime"));
           fragmentView.onSuccess(packageInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
