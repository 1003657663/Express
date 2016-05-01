package extrace.Customer.Express.model.express_edit_model;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;
import extrace.Customer.Express.presenter.express_edit_presenter.expressPresenter;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/16.
 */
public class express extends VolleyHelper implements express_edit_model {

    expressPresenter expressPresenter;
    String url;
    public express(Activity activity, expressPresenter expressPresenter) {
        super(activity);
        this.expressPresenter = expressPresenter;
        url =activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.SendExpress);
    }

    @Override
    public void newExpress(int customerId,int senderID, int receiverID) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customerId",customerId);
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
                String ID=jsonObject.getString("ID");
                if(!ID.equals(""))
                        expressPresenter.onSuccess(ID);
                else
                        expressPresenter.onFail();
                }
             catch (JSONException e) {
                e.printStackTrace();
                expressPresenter.onFail();
            }
        }


    @Override
    public void onError(String errorMessage) {
        expressPresenter.onFail();
    }

}

