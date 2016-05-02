package extrace.sorter.close.add_package_list;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import extrace.net.VolleyHelper;

/**
 * Created by 黎明 on 2016/4/30.
 */
public class Add_package_listPresenterImpl extends VolleyHelper implements Add_package_listPresenter
{
    private Add_package_listFragmentView Add_package_listFragmentView;
    String url="";
    public Add_package_listPresenterImpl(Activity activity, Add_package_listFragmentView Add_package_listFragmentView)
    {
        super(activity);
        this.Add_package_listFragmentView = Add_package_listFragmentView;
    }

    @Override
    public void addPackage(List list) {
/*
        url += "";
        JSONArray jsonArray=new JSONArray();
        for(int i=0;i<list.size();i++)
        {
            try {
                jsonArray.put(i,list.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        doJsonArray(url,VolleyHelper.POST,jsonArray);*/
        String a="{\"state\":\"3\"}";
        try {
            JSONObject object=new JSONObject(a);
            onDataReceive(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addExpress(List list) {
      /*  url += "";
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
        doJsonArray(url,VolleyHelper.POST,jsonArray);*/
        String a="{\"state\":\"0\"}";
        try {
            JSONObject object=new JSONObject(a);
            onDataReceive(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        try {
            int state=jsonObject.getInt("state");
            if (state==0)
                Add_package_listFragmentView.Success();
            else if(state==3)
                Add_package_listFragmentView.pSuccess();
            else
                Add_package_listFragmentView.Fail();
        } catch (JSONException e) {
            e.printStackTrace();
            Add_package_listFragmentView.Fail();
        }
    }
    @Override
    public void onError(String errorMessage) {
        Add_package_listFragmentView.Fail();
    }



}