package extrace.Customer.Express.model.express_edit_model;
import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import extrace.net.VolleyHelper;
/**
 * Created by 黎明 on 2016/4/16.
 */
public class Express_edit_modelImpl extends VolleyHelper implements Express_edit_model {

    extrace.Customer.Express.presenter.express_edit_presenter.Expresspresenter Expresspresenter;
    String url;
    public Express_edit_modelImpl(Activity activity, extrace.Customer.Express.presenter.express_edit_presenter.Expresspresenter Expresspresenter) {
        super(activity);
        this.Expresspresenter = Expresspresenter;
    }

    @Override
    public void newExpress(int customerId,int senderID, int receiverID) {
      url="http://182.254.214.97:8080/REST/Domain/prepareSendExpress/customerId/"+customerId+"/sendAddressId/"+senderID+"/recAddressId/"+receiverID;
        try {
            JSONObject object=new JSONObject();
            doJson(url, VolleyHelper.GET,object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
       /* String ID = (String) jsonOrArray;
        if (ID != null)
            Expresspresenter.onSuccess(ID);*/
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        try {
            String ID=jsonObject.getString("ID");
            if(ID!=null)
                Expresspresenter.onSuccess(ID);
        } catch (JSONException e) {
            e.printStackTrace();
            Expresspresenter.onFail();
        }
    }
    @Override
    public void onError(String errorMessage) {
        Expresspresenter.onFail();
    }

}

