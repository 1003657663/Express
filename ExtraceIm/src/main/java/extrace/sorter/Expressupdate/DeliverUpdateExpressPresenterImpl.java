package extrace.sorter.Expressupdate;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import extrace.main.MyApplication;
import extrace.model.ExpressEntity;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/5/3.
 */
public class DeliverUpdateExpressPresenterImpl extends VolleyHelper implements DeliverUpdateExpressPresenter {
    private DeliverUpdateExpressFragmentView fragmentView;
    String url, turl;

    public DeliverUpdateExpressPresenterImpl(Activity activity, DeliverUpdateExpressFragmentView fragmentView) {
        super(activity);
        this.fragmentView = fragmentView;
        turl = activity.getResources().getString(R.string.base_url) + "/REST/Domain/updateExpressFree";
        url = turl;
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        try {
            if (jsonObject.getInt("state") == 1)
                fragmentView.onSuccess();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(String errorMessage) {
        fragmentView.onFail(errorMessage);
    }

    @Override
    public void updateExpress(ExpressEntity express) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", express.getId());
            jsonObject.put("insuFee", express.getInsuFee());
            jsonObject.put("weight", express.getWeight());
            jsonObject.put("tranFee", express.getTranFee());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        doJson(url, VolleyHelper.POST, jsonObject);
    }

}
