package extrace.sorter.Expressupdate;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import extrace.main.MyApplication;
import extrace.model.ExpressInfo;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/28.
 */
public class ExpressUpdatePresenterImpl extends VolleyHelper implements ExpressUpdatePresenter {
    private ExpressUpdateFragmentView fragmentView;
    String url, turl, token;

    public ExpressUpdatePresenterImpl(Activity activity, ExpressUpdateFragmentView fragmentview) {
        super(activity);
        this.fragmentView = fragmentview;
        turl = activity.getResources().getString(R.string.base_url) + activity.getResources().getString(R.string.getExpressInfo_ById);
        token = ((MyApplication) activity.getApplication()).getToken();
        url = turl;
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        ExpressInfo expressInfo = new ExpressInfo();
        try {
            expressInfo.setRname(jsonObject.getString("rname"));
            expressInfo.setRtel(jsonObject.getString("rtel"));
            expressInfo.setRaddinfo(jsonObject.getString("raddinfo"));
            expressInfo.setRadd(jsonObject.getString("radd"));
            expressInfo.setSname(jsonObject.getString("sname"));
            expressInfo.setStel(jsonObject.getString("stel"));
            expressInfo.setSaddinfo(jsonObject.getString("saddinfo"));
            expressInfo.setSadd(jsonObject.getString("sadd"));
            fragmentView.onSuccess(expressInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String errorMessage) {
        fragmentView.onFail(errorMessage);
    }

    @Override
    public void getExpressInfoByID(String ID) {
        url += ID + "/" + token;
        try {
            JSONObject jsonObject = new JSONObject();
            doJson(url, VolleyHelper.GET, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

