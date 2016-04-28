package extrace.Customer.Express.model.express_info_model;

import android.app.Activity;
import org.json.JSONException;
import org.json.JSONObject;
import extrace.Customer.Express.presenter.express_info_presenter.express_info_presenter;
import extrace.model.ExpressInfo;
import extrace.net.VolleyHelper;

/**
 * Created by 黎明 on 2016/4/19.
 */
public class express_info_modelImpl extends VolleyHelper implements express_info_model
{
    private express_info_presenter express_info_presenter;
    String url="";
    public express_info_modelImpl(Activity activity, express_info_presenter express_info_presenter)
    {
        super(activity);
        this.express_info_presenter=express_info_presenter;
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
            express_info_presenter.Success(expressInfo);
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
        url+="";
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("ID",ID);
            doJson(url,VolleyHelper.POST,jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            express_info_presenter.Fail();
        }
    }
}
