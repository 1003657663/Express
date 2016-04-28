package extrace.sorter.Package.ep_search.package_list;

import android.app.Activity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import extrace.model.Package;
import extrace.net.VolleyHelper;

/**
 * Created by 黎明 on 2016/4/26.
 */
public class package_list_modelImpl extends VolleyHelper implements package_list_model
{
    private package_list_presenter package_list_presenter;
    String url="";
    public package_list_modelImpl(Activity activity, package_list_presenter package_list_presenter)
    {
        super(activity);
        this.package_list_presenter=package_list_presenter;
    }

    @Override
    public void onOpen(String packageID) {
       url+="";
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("packageID",packageID);
            doJson(url, VolleyHelper.POST,jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
             package_list_presenter.onFail();
        }
    }

    @Override
    public void onSearchPByPackageID(String packageID) {

       url+="";
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("packageID",packageID);
            doJson(url, VolleyHelper.POST,jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            package_list_presenter.onFail();
        }
    }

   // @Override
    public void onError(String errorMessage) {
        package_list_presenter.onFail();
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        try {
            JSONObject object=(JSONObject)jsonOrArray;
            if(object.getInt("state")==0)
            {
                package_list_presenter.onSuccess();
            }
            else if(object.getInt("state")==1)
            {
                package_list_presenter.onFail();
            }
            else
            {
                JSONArray jsonArray=(JSONArray)jsonOrArray;
                List<Package> list=new ArrayList<>();
                for(int i=0;i<jsonArray.length();i++) {
                    Package p = (Package) jsonArray.get(i);
                    list.add(p);
                }
                package_list_presenter.onSuccess(list);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            package_list_presenter.onFail();
        }
    }
}
