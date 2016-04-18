package extrace.Express.model.express_search;

import android.app.Activity;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import extrace.model.ExpressSheet;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.temp.ExTraceApplication;
import extrace.Express.presenter.expressListPresenter.expressListPresenter;
import java.util.List;

/**
 * Created by 黎明 on 2016/4/17.
 */
public class expressListModelImpl extends HttpAsyncTask implements expressListModel
{
    String url;
    private Activity context;
    private expressListPresenter expressListPresenter;
    IDataAdapter<List<ExpressSheet>> adapter;

    public expressListModelImpl(Activity activity,expressListPresenter expressListPresenter,IDataAdapter<List<ExpressSheet>> adapter) {
    super(activity);
    this.adapter=adapter;
    this.expressListPresenter = expressListPresenter;
        url = ((ExTraceApplication)activity.getApplication()).getMiscServiceUrl();
}

    @Override
    public void onDataReceive(String class_name, String json_data) {
        if(json_data.equals("List<ExpressSheet>"))
        {
            //若返回了一个list则说明传送成功 此处的json_data有待商榷
           List<ExpressSheet> list= JsonUtils.fromJson(json_data,new TypeToken<List<ExpressSheet>>(){});
            adapter.setData(list);
            //将所得的list传入adapter以供拿取
            adapter.notifyDataSetChanged();
        }
        else
            expressListPresenter.onFail();
    }

    @Override
    public void onStatusNotify(HttpResponseParam.RETURN_STATUS status, String str_response) {
        Log.i("onStatusNotify", "onStatusNotify: " + str_response);
    }

    @Override
    public void searchExpressList(String ID)
    {
        url += "searchExpressList/"+ID+"?_type=json";
        try {
            execute(url,"GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
