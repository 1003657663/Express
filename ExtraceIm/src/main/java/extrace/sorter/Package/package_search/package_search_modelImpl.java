package extrace.sorter.Package.package_search;

import android.app.Activity;
import org.json.JSONException;
import org.json.JSONObject;
import extrace.model.packageInfo;
import extrace.net.VolleyHelper;
/**
 * Created by 黎明 on 2016/4/25.
 */
public class Package_search_modelImpl extends VolleyHelper implements Package_search_model
{
   private Package_search_presenter Package_search_presenter;
    String url="";
    public Package_search_modelImpl(Activity activity, Package_search_presenter Package_search_presenter)
    {
        super(activity);
      this.Package_search_presenter = Package_search_presenter;
    }

    @Override
    public void openPackage(String packageID) {
       url+=packageID;
        JSONObject jsonObject=new JSONObject();
        try {
            doJson(url, VolleyHelper.POST,jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            Package_search_presenter.onFail();
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
               Package_search_presenter.onSuccess(packageInfo);
        } catch (JSONException e) {
            e.printStackTrace();
           Package_search_presenter.onFail();
        }
    }

    @Override
    public void onError(String errorMessage) {
        Package_search_presenter.onFail();
    }
}
