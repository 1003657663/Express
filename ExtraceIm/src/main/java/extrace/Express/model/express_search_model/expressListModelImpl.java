package extrace.Express.model.express_search_model;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import extrace.model.ExpressSheet;
import extrace.net.IDataAdapter;
import extrace.net.VolleyHelper;
import extrace.Express.presenter.express_search_presenter.expressListPresenter;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by 黎明 on 2016/4/17.
 */
public class expressListModelImpl extends VolleyHelper implements expressListModel
{
    String url;
    private expressListPresenter expressListPresenter;
    IDataAdapter<List<ExpressSheet>> adapter;
    @Override
    public void onError(String errorMessage) {

    }

    public expressListModelImpl(Activity activity, expressListPresenter expressListPresenter, IDataAdapter<List<ExpressSheet>> adapter) {
    super(activity);
    this.adapter=adapter;
    this.expressListPresenter = expressListPresenter;
       // url = ((ExTraceApplication)activity.getApplication()).getMiscServiceUrl();
}

    @Override
    public void onDataReceive(JSONObject jsonObject) {
        try {
            if (jsonObject.getString("state").equals("true")) {
                JSONArray array = jsonObject.getJSONArray("express");
                List<ExpressSheet> list = new ArrayList<ExpressSheet>();

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = (JSONObject) array.get(i);
                    ExpressSheet expressSheet = new ExpressSheet();
                    //json转换为List<Express>
                    expressSheet.setID(object.getString("ID"));
                    expressSheet.setStatus(object.getInt("status"));
                    //expressSheet.setAccepteTime(object.getString("AcceptTime"));
                    expressSheet.setType(object.getInt("type"));
                    if (expressSheet != null) {
                        list.add(expressSheet);
                    }
                }
                    adapter.setData(list);//将list放入adapter
                    expressListPresenter.onSuccess();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            expressListPresenter.onFail();//失败则调用presenter
        }
    }
    @Override
    public void searchExpressList(String ID)
    {
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
