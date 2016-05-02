package extrace.user.search;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import extrace.model.ExpressInfo;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by songchao on 16/5/1.
 */
public class SearchExpressPresenterImpl extends VolleyHelper implements SearchExpressPresenter{

    private SearchExpressView searchExpressView;
    private String searchUrl;

    public SearchExpressPresenterImpl(Activity context,SearchExpressView searchExpressView) {
        super(context);
        this.searchExpressView = searchExpressView;
        String baseUrl = context.getResources().getString(R.string.base_url);
        searchUrl = baseUrl + context.getResources().getString(R.string.express_search_one);
    }

    @Override
    public void startGetExpressInfo(String expressID) {
        searchUrl = searchUrl.replace("{id}",expressID);
        doJson(searchUrl,VolleyHelper.GET,null);
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        ExpressInfo expressInfo = new ExpressInfo();
        try {
            expressInfo.setAcc1(jsonObject.getString("acc1"));
            expressInfo.setAcc2(jsonObject.getString("acc2"));
            expressInfo.setGetTime(jsonObject.getString("getTime"));
            expressInfo.setID(jsonObject.getString("ID"));
            expressInfo.setInsuFee(jsonObject.getDouble("insuFee"));
            expressInfo.setOutTime(jsonObject.getString("outTime"));
            expressInfo.setRadd(jsonObject.getString("sadd"));
            expressInfo.setRaddinfo(jsonObject.getString("raddinfo"));
            expressInfo.setSadd(jsonObject.getString("sadd"));
            expressInfo.setSaddinfo(jsonObject.getString("saddinfo"));
            expressInfo.setRname(jsonObject.getString("rname"));
            expressInfo.setSname(jsonObject.getString("sname"));
            expressInfo.setRtel(jsonObject.getString("rtel"));
            expressInfo.setStel(jsonObject.getString("stel"));
            expressInfo.setWeight(jsonObject.getDouble("weight"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(String errorMessage) {

    }
}
