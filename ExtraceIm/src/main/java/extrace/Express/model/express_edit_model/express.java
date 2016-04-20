package extrace.Express.model.express_edit_model;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;
import extrace.Express.presenter.express_edit_presenter.expressPresenter;
import extrace.net.VolleyHelper;

/**
 * Created by 黎明 on 2016/4/16.
 */
public class express extends VolleyHelper implements express_edit_model {

    String url;
    expressPresenter expressPresenter;

    public express(Activity activity, expressPresenter expressPresenter) {
        super(activity);
        this.expressPresenter = expressPresenter;
       // url = (() activity.getApplication()).getMiscServiceUrl();
    }

    @Override
    public void newExpress(int senderID, int receiverID) {
        JSONObject jsonObject = new JSONObject();
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
    public void onDataReceive(Object jsonOrArray) {
            try {
                JSONObject jsonObject=(JSONObject)jsonOrArray;
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

