package extrace.Customer.Express.model.expressInfoModel;

import android.app.Activity;
import org.json.JSONException;
import org.json.JSONObject;
import extrace.Customer.Express.presenter.express_info_presenter.ExpressInfoPresenter;
import extrace.model.ExpressInfo;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/19.
 */
public class ExpressInfoModelImpl extends VolleyHelper implements ExpressInfoModel
{
    private ExpressInfoPresenter ExpressInfoPresenter;
    private String url;
    public ExpressInfoModelImpl(Activity activity, ExpressInfoPresenter express_infoPresenter)
    {
        super(activity);
        this.ExpressInfoPresenter = express_infoPresenter;
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
            ExpressInfoPresenter.Success(expressInfo);
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
