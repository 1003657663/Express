package extrace.user.address;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by chao on 2016/4/17.
 */
public class AddressModelImpl extends VolleyHelper implements AddressModel {

    AddressPresenter presenter;
    Activity activity;
    String getAddressUrl = "http://www.baidu.com";
    public AddressModelImpl(Activity context,AddressPresenter presenter) {
        super(context);
        this.activity = context;
        this.presenter = presenter;
    }

    @Override
    public void startGetAddress() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("getaddress",true);
            doJson(getAddressUrl, VolleyHelper.POST,jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            presenter.onGetAddressFail(activity.getResources().getString(R.string.throw_net_exception));
        }
    }

    /*@Override
    public void onStatusNotify(HttpResponseParam.RETURN_STATUS status, String str_response) {

    }*/

    @Override
    public void onDataReceive(JSONObject jsonObject) {
        String name = "chao";
        String tel = "15029895623";
        String address = "郑州市郑州大学新校区松园19楼508宿舍";
        //如果请求成功这里
        presenter.onGetAddressSuccess(name,tel,address,true);
    }

    @Override
    public void onError(String errorMessage) {
        presenter.onGetAddressFail(errorMessage);
    }
}
