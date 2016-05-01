package extrace.sorter.close.add_package_list;

import android.app.Activity;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import extrace.model.packageInfo;
import extrace.net.VolleyHelper;

/**
 * Created by 黎明 on 2016/4/30.
 */
public class add_package_listPresenterImpl extends VolleyHelper implements add_package_listPresenter
{
    private add_package_listFragmentView add_package_listFragmentView;
    String url="";
    public add_package_listPresenterImpl(Activity activity, add_package_listFragmentView add_package_listFragmentView)
    {
        super(activity);
        this.add_package_listFragmentView=add_package_listFragmentView;
    }

    @Override
    public void addPackage(List list) {
        url += "";
        JSONArray jsonArray=new JSONArray();
        for(int i=0;i<list.size();i++)
        {
            try {
                jsonArray.put(i,list.get(i));
               // jsonObject.put("ID"+i,list.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        doJsonArray(url,VolleyHelper.POST,jsonArray);
      //  doJson(url, VolleyHelper.POST,null);
    }

    @Override
    public void addExpress(List list) {
        url += "";
        JSONArray jsonArray=new JSONArray();
        for(int i=0;i<list.size();i++)
        {
            try {
                jsonArray.put(i,list.get(i));
                // jsonObject.put("ID"+i,list.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        doJsonArray(url,VolleyHelper.POST,jsonArray);
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        try {
            int state=jsonObject.getInt("state");
            if (state==0)
                add_package_listFragmentView.Success();
            else if(state==3)
                add_package_listFragmentView.pSuccess();
            else
                add_package_listFragmentView.Fail();
        } catch (JSONException e) {
            e.printStackTrace();
            add_package_listFragmentView.Fail();
        }
    }
    @Override
    public void onError(String errorMessage) {
        add_package_listFragmentView.Fail();
    }



}