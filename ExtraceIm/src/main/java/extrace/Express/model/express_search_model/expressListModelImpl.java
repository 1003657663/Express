package extrace.Express.model.express_search_model;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import extrace.Express.model.express_info_model.ExpressInfo;
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
