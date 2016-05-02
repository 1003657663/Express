package extrace.Customer.Express.model.express_info_model;

import android.app.Activity;
import org.json.JSONException;
import org.json.JSONObject;
import extrace.Customer.Express.presenter.express_info_presenter.Express_info_presenter;
import extrace.model.ExpressInfo;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/19.
 */
public class Express_info_modelImpl extends VolleyHelper implements Express_info_model
{
    private Express_info_presenter Express_info_presenter;
    private String url;
    public Express_info_modelImpl(Activity activity, Express_info_presenter express_info_presenter)
    {
        super(activity);
        this.Express_info_presenter =express_info_presenter;
        url =activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.getExpressInfo_ById);
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject=(JSONObject)jsonOrArray;
        try {
            ExpressInfo expressInfo=new ExpressInfo();
            expressInfo.setID(jsonObject.getString("ID"));
            expressInfo.setAcc1(jsonObject.getString("Acc1"));
            expressInfo.setAcc2(jsonObject.getString("Acc2"));
            expressInfo.setSname(jsonObject.getString("sname"));
            expressInfo.setStel(jsonObject.getString("stel"));
            expressInfo.setSadd(jsonObject.getString("sadd"));
            expressInfo.setSaddinfo(jsonObject.getString("saddinfo"));
            expressInfo.setRname(jsonObject.getString("rname"));
            expressInfo.setRtel(jsonObject.getString("rtel"));
            expressInfo.setRadd(jsonObject.getString("radd"));
            expressInfo.setRaddinfo(jsonObject.getString("raddinfo"));
            expressInfo.setGetTime(jsonObject.getString("GetTime"));
            expressInfo.setOutTime(jsonObject.getString("OutTime"));
            expressInfo.setTranFee((float) jsonObject.getDouble("TranFee"));
            expressInfo.setInsuFee((float) jsonObject.getDouble("InsuFee"));
            Express_info_presenter.Success(expressInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String errorMessage) {

    }

    @Override
    public void findInfoByID(String ID)
    {
        url+=ID;
        try {
            doJson(url,VolleyHelper.GET,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
