package extrace.Express.model.express_edit;

import android.app.Activity;

import extrace.model.ExpressSheet;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam;
import extrace.Express.presenter.expressPresenter.expressPresenter;
import extrace.net.IDataAdapter;
import extrace.ui.temp.ExTraceApplication;

/**
 * Created by 黎明 on 2016/4/16.
 */
public class expressModelImpl extends HttpAsyncTask implements expressModel
{
    String url;
    expressPresenter expressPresenter;
    IDataAdapter<ExpressSheet> adapter;
    public expressModelImpl(Activity activity, expressPresenter expressPresenter, IDataAdapter<ExpressSheet> adapter)
    {
        super(activity);
        this.expressPresenter=expressPresenter;
        this.adapter=adapter;
        url = ((ExTraceApplication)activity.getApplication()).getMiscServiceUrl();
    }

    @Override
    public void newExpress(int senderID, int receiveID)
    {
         url+="newExpress/senderID=?"+senderID+"&receiveID=?"+receiveID+"?_type=json";
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

        //返回值为true or false
        if(json_data.equals("true"))
        {
            expressPresenter.onSuccess();
        }
        else if(json_data.equals("false"))
        {
            expressPresenter.onFail();
        }
        else
            expressPresenter.onFail();

    }
}
