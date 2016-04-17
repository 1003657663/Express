package extrace.Express.model;

import android.app.Activity;

import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam;
import extrace.Express.presenter.expressPresenter;

/**
 * Created by 黎明 on 2016/4/16.
 */
public class expressModelImpl extends HttpAsyncTask implements expressModel
{
    String url="";
    expressPresenter expressPresenter;
    public expressModelImpl(Activity activity,expressPresenter expressPresenter)
    {
        super(activity);
        this.expressPresenter=expressPresenter;
    }

    @Override
    public void newExpress(int senderID, int receiveID)
    {
         url+="newExpress/senderID=?"+senderID+"&receiveID=?"+receiveID;
        try {
            execute(url,"GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusNotify(HttpResponseParam.RETURN_STATUS status, String str_response) {

    }

    @Override
    public void onDataReceive(String class_name, String json_data) {

        if(json_data.equals("true"))
        {
            expressPresenter.onSuccess();
        }
        else if(json_data.equals("false"))
        {
            expressPresenter.onFail();
        }
        else{
            expressPresenter.onFail();
        }
    }
}
