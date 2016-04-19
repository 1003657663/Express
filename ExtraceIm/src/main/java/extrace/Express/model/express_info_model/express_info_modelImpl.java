package extrace.Express.model.express_info_model;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;


import extrace.Express.presenter.express_info_presenter.express_info_presenter;
import extrace.model.ExpressSheet;
import extrace.net.IDataAdapter;
import extrace.net.VolleyHelper;

/**
 * Created by 黎明 on 2016/4/19.
 */
public class express_info_modelImpl extends VolleyHelper implements express_info_model
{
    IDataAdapter<ExpressSheet> adapter;
    private express_info_presenter express_info_presenter;
    String url="";
    public express_info_modelImpl(Activity activity, express_info_presenter express_info_presenter, IDataAdapter<ExpressSheet> adapter)
    {
        //此处IDATA<>里应是包含输出信息的一个实体 而不是express
        super(activity);
        this.express_info_presenter=express_info_presenter;
        this.adapter=adapter;
    }
    @Override
    public void onDataReceive(JSONObject jsonObject) {

        try {
            ExpressSheet expressSheet=new ExpressSheet();
            expressSheet.setID(jsonObject.getString("ID"));
           /* expressSheet.set
            String sname=jsonObject.getString("sname");
            String stel=jsonObject.getString("stel");
            */
            adapter.setData(expressSheet);
            express_info_presenter.Success();
            //根据ID拿到快件详情需要显示的信息 放到adapter中
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(String errorMessage) {

    }

    @Override
    public void findInfoByID(String ID)
    {
        url+="";
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("ID",ID);
            doJson(url,VolleyHelper.POST,jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            express_info_presenter.Fail();
        }

    }
}
