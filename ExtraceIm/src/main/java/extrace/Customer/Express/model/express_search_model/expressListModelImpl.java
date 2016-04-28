package extrace.Customer.Express.model.express_search_model;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import extrace.model.ExpressInfo;
import extrace.net.VolleyHelper;
import extrace.Customer.Express.presenter.express_search_presenter.expressListPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by 黎明 on 2016/4/17.
 */
public class expressListModelImpl extends VolleyHelper implements expressListModel
{
    String url;
    private expressListPresenter expressListPresenter;
    @Override
    public void onError(String errorMessage) {

    }

    public expressListModelImpl(Activity activity, expressListPresenter expressListPresenter) {
    super(activity);
    this.expressListPresenter = expressListPresenter;
       // url = ((ExTraceApplication)activity.getApplication()).getMiscServiceUrl();
}

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject object=(JSONObject)jsonOrArray;
        List<ExpressInfo> list = new ArrayList<ExpressInfo>();
        for (int i = 0; i < 20; i++)
        { ExpressInfo expressInfo = new ExpressInfo();
        //json转换为List<Express>
        expressInfo.setID("1");
        expressInfo.setSname("2");
        expressInfo.setRname("3");
        expressInfo.setGetTime("4");
        list.add(expressInfo);}
            expressListPresenter.onSuccess(list);
       /* JSONArray jsonArray=(JSONArray)jsonOrArray;
        try {
            List<ExpressInfo> list = new ArrayList<ExpressInfo>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                ExpressInfo expressInfo = new ExpressInfo();
                //json转换为List<Express>
                expressInfo.setID(object.getString("ID"));
                expressInfo.setSname(object.getString("sname"));
                expressInfo.setRname(object.getString("rname"));
                expressInfo.setGetTime(object.getString("GetTime"));
                if (expressInfo != null) {
                    list.add(expressInfo);
                }
            }
            expressListPresenter.onSuccess(list);
        }
        catch (JSONException e) {
            e.printStackTrace();
            expressListPresenter.onFail();//失败则调用presenter Fail
        }*/
    }

    @Override
    public void searchByID(String ID)
    {
        JSONObject object=new JSONObject();
        onDataReceive(object);
       /* Object object=new Object();
        url += "";
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("ID",ID);
            doJson(url,VolleyHelper.POST,jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void searchByTel(String ID) {
        Object object=new Object();
        url += "";
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("ID",ID);
            doJson(url,VolleyHelper.POST,jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void searchByCID(String ID) {
        Object object=new Object();
        url += "";
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("ID",ID);
            doJson(url,VolleyHelper.POST,jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
