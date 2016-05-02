package extrace.Customer.Express.model.express_edit_model;
import android.app.Activity;
import extrace.Customer.Express.presenter.express_edit_presenter.expressPresenter;
import extrace.net.VolleyHelper;
/**
 * Created by 黎明 on 2016/4/16.
 */
public class express extends VolleyHelper implements express_edit_model {

    expressPresenter expressPresenter;
    String url;
    public express(Activity activity, expressPresenter expressPresenter) {
        super(activity);
        this.expressPresenter = expressPresenter;
    }

    @Override
    public void newExpress(int customerId,int senderID, int receiverID) {
        url="http://182.254.214.97:8080/REST/Domain/prepareSendExpress/customerId/"+customerId+"/sendAddressId/"+senderID+"/recAddressId/"+receiverID;
        try {
            doJson(url, VolleyHelper.GET,null );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        String ID = (String) jsonOrArray;
        if(ID!=null)
        expressPresenter.onSuccess(ID);
    }
    @Override
    public void onError(String errorMessage) {
        expressPresenter.onFail();
    }

}

