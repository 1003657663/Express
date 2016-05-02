package extrace.Customer.Express.model.express_search_model;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import extrace.Customer.Express.presenter.express_search_presenter.Express_List_Presenter;
import extrace.model.ExpressInfo;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 黎明 on 2016/4/17.
 */
public class Express_list_modelImpl extends VolleyHelper implements Express_list_model
{
    private Express_List_Presenter Express_List_Presenter;
    private String searchByIDurl,searchByCIDurl,searchByTelurl;
    @Override
    public void onError(String errorMessage) {
        Express_List_Presenter.onFail();

    }

    public Express_list_modelImpl(Activity activity, Express_List_Presenter expressListPresenter) {
    super(activity);
    this.Express_List_Presenter = expressListPresenter;
     searchByIDurl =activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.getExpressInfo_ById);
        searchByCIDurl =activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.getExpressInfo_ByCustomerId);
        searchByTelurl =activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.getExpressInfo_ByTel);

}

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONArray jsonArray=(JSONArray)jsonOrArray;
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
            Express_List_Presenter.onSuccess(list);
        }
        catch (JSONException e) {
            e.printStackTrace();
            Express_List_Presenter.onFail();//失败则调用presenter Fail
        }
    }

    @Override
    public void searchByID(String ID)
    {
        JSONObject object=new JSONObject();
        searchByIDurl+=ID;
        try {
            doJson(searchByIDurl,VolleyHelper.GET,object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void searchByTel(String ID) {
        JSONArray jsonArray=new JSONArray();
        searchByTelurl+=ID;
        try {
            doJsonArray(searchByTelurl,VolleyHelper.GET,jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void searchByCID(int ID) {
        JSONObject object=new JSONObject();
        searchByCIDurl+=ID;
        try {
            doJson(searchByCIDurl,VolleyHelper.GET,object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
