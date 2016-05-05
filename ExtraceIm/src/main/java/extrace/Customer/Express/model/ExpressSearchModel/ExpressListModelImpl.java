package extrace.Customer.Express.model.ExpressSearchModel;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import extrace.Customer.Express.presenter.express_search_presenter.ExpressListPresenter;
import extrace.model.ExpressInfo;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 黎明 on 2016/4/17.
 */
public class ExpressListModelImpl extends VolleyHelper implements ExpressListModel
{
    private ExpressListPresenter express_List_Presenter;
    private String searchByIDurl,searchByCIDurl,searchByTelurl;
    @Override
    public void onError(String errorMessage) {
       express_List_Presenter.onFail();

    }

    public ExpressListModelImpl(Activity activity, ExpressListPresenter expressListPresenter) {
    super(activity);
    this.express_List_Presenter = expressListPresenter;
     searchByIDurl =activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.getExpressInfo_ById);
        searchByCIDurl =activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.getExpressInfo_ByCustomerId);
        searchByTelurl =activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.getExpressInfo_ByTel);
}

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONArray jsonArray= null;
        try {
            jsonArray = (JSONArray)jsonOrArray;
            List<ExpressInfo> list=new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++)
            {
                ExpressInfo expressInfo=new ExpressInfo();
                try {
                   JSONObject object=(JSONObject)jsonArray.get(i);
                    expressInfo.setID(object.getString("ID"));
                    expressInfo.setSname(object.getString("sname"));
                    expressInfo.setRname(object.getString("rname"));
                    expressInfo.setGetTime(object.getString("getTime"));
                    expressInfo.setStel(object.getString("stel"));
                    expressInfo.setRtel(object.getString("rtel"));
                    expressInfo.setRaddinfo(object.getString("raddinfo"));
                    expressInfo.setSaddinfo(object.getString("saddinfo"));
                    expressInfo.setAcc1(object.getString("Acc1"));
                    expressInfo.setAcc1(object.getString("Acc2"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                list.add(expressInfo);
            }
            express_List_Presenter.Success(list);//如果返回值是list
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject object=(JSONObject)jsonOrArray;
            try {
                ExpressInfo  expressInfo = new ExpressInfo();
                expressInfo.setID(object.getString("ID"));
                expressInfo.setSname(object.getString("sname"));
                expressInfo.setRname(object.getString("rname"));
                expressInfo.setGetTime(object.getString("getTime"));
                expressInfo.setStel(object.getString("stel"));
                expressInfo.setRtel(object.getString("rtel"));
                expressInfo.setRaddinfo(object.getString("raddinfo"));
                expressInfo.setSaddinfo(object.getString("saddinfo"));
                expressInfo.setAcc1(object.getString("Acc1"));
                expressInfo.setAcc1(object.getString("Acc2"));
                express_List_Presenter.onSuccess(expressInfo);//如果返回值是expressinfo
            } catch (Exception e1) {
                e1.printStackTrace();
            }
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
