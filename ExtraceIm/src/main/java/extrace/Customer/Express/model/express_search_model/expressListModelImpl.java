package extrace.Customer.Express.model.express_search_model;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import extrace.model.ExpressInfo;
import extrace.net.VolleyHelper;
import extrace.Customer.Express.presenter.express_search_presenter.expressListPresenter;
import extrace.ui.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 黎明 on 2016/4/17.
 */
public class expressListModelImpl extends VolleyHelper implements expressListModel
{
    private expressListPresenter expressListPresenter;
    private String searchByIDurl,searchByCIDurl,searchByTelurl;
    @Override
    public void onError(String errorMessage) {
        expressListPresenter.onFail();

    }

    public expressListModelImpl(Activity activity, expressListPresenter expressListPresenter) {
    super(activity);
    this.expressListPresenter = expressListPresenter;
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
            expressListPresenter.onSuccess(list);
        }
        catch (JSONException e) {
            e.printStackTrace();
            expressListPresenter.onFail();//失败则调用presenter Fail
        }
    }

    @Override
    public void searchByID(String ID)
    {
        searchByIDurl+=ID;
        try {
            doJson(searchByIDurl,VolleyHelper.GET,null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void searchByTel(String ID) {
        searchByTelurl+=ID;
        try {
            doJson(searchByTelurl,VolleyHelper.GET,null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void searchByCID(int ID) {
        searchByCIDurl+=ID;
        try {
            doJson(searchByCIDurl,VolleyHelper.GET,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
