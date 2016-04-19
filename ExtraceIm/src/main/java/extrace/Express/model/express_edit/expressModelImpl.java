package extrace.Express.model.express_edit;

import android.app.Activity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import extrace.Express.presenter.expressPresenter.expressPresenter;
import extrace.net.VolleyHelper;

/**
 * Created by 黎明 on 2016/4/16.
 */
public class expressModelImpl extends VolleyHelper implements expressModel {

    String url;
    expressPresenter expressPresenter;

    public expressModelImpl(Activity activity, expressPresenter expressPresenter) {
        super(activity);
        this.expressPresenter = expressPresenter;
       // url = (() activity.getApplication()).getMiscServiceUrl();
    }

    @Override
    public void newExpress(int senderID, int receiverID) {
        JSONObject jsonObject = new JSONObject();
        Log.i("tag","model");
        url+="";
        try {
            jsonObject.put("senderID", senderID);
            jsonObject.put("receiverID", receiverID);

            doJson(url, VolleyHelper.POST, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDataReceive(JSONObject jsonObject) {
        try {
            String state = jsonObject.getString("state");
            String ID=jsonObject.getString("ID");
            switch (state) {
                case "true":
                    expressPresenter.onSuccess(ID);
                    break;
                case "false":
                    expressPresenter.onFail();
                    break;
                default:
                    expressPresenter.onFail();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            expressPresenter.onFail();
        }
    }

    @Override
    public void onError(String errorMessage) {

    }

}

