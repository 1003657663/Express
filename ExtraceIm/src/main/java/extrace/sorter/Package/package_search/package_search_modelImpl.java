package extrace.sorter.Package.package_search;

import android.app.Activity;


import org.json.JSONException;
import org.json.JSONObject;

import extrace.model.packageInfo;
import extrace.net.VolleyHelper;

/**
 * Created by 黎明 on 2016/4/25.
 */
public class package_search_modelImpl  extends VolleyHelper implements package_search_model
{
   private package_search_presenter package_search_presenter;
    String url="";
    public package_search_modelImpl(Activity activity, package_search_presenter package_search_presenter)
    {
        super(activity);
      this.package_search_presenter=package_search_presenter;
    }

    @Override
    public void openPackage(String packageID) {
        JSONObject object=new JSONObject();
       url+="";
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("packageID",packageID);
            doJson(url, VolleyHelper.POST,jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            package_search_presenter.onFail();
        }
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject=(JSONObject)jsonOrArray;
       try {
            packageInfo packageInfo=new packageInfo();
               packageInfo.setClosetime(jsonObject.getString("closetime"));
               packageInfo.setPackagefrom(jsonObject.getString("packagefrom"));
               packageInfo.setPackageto(jsonObject.getString("packageto"));
               packageInfo.setEmployeesID(Integer.parseInt(jsonObject.getString("EmployeesID")));
               packageInfo.setEmployeesName(jsonObject.getString("ExployeesName"));
               packageInfo.setID(jsonObject.getString("ID"));
               package_search_presenter.onSuccess(packageInfo);
        } catch (JSONException e) {
            e.printStackTrace();
           package_search_presenter.onFail();
        }
    }

    @Override
    public void onError(String errorMessage) {

        package_search_presenter.onFail();
    }
}
