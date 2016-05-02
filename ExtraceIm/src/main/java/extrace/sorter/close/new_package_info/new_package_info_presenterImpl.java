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
     private new_package_info_fragmentView fragmentView1;
    String url;
    public new_package_info_presenterImpl(Activity activity,new_package_info_fragmentView fragmentView2)
    {
        super(activity);
        this.fragmentView1 =fragmentView2;
    }

    @Override
    public void newPackage(int fromID, int toID, int employeesId) {
       packageInfo packageInfo=new packageInfo("1234","123","123","123",1,"1234");
        String a="123";
        try {
            JSONObject object=new JSONObject(a);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        onDataReceive(packageInfo);
        /* url+="";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fromID", fromID);
            jsonObject.put("toID",toID);
            jsonObject.put("employeesID",employeesId);
            doJson(url, VolleyHelper.POST, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
           fragmentView.onFail();
        }*/
    }

    @Override
    public void onError(String errorMessage) {
        fragmentView1.onFail();
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
            JSONObject jsonObject = (JSONObject) jsonOrArray;
            packageInfo packageInfo=new packageInfo();
        try {
            packageInfo.setID(jsonObject.getString("ID"));
            packageInfo.setClosetime(jsonObject.getString("closetime"));
           fragmentView1.onSuccess(packageInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
