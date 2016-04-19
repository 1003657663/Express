package extrace.user.address;

import android.app.Activity;

import org.json.JSONArray;
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
    String getAddressUrl = "http://10.101.244.118:8080/address";
    public AddressModelImpl(Activity context,AddressPresenter presenter) {
        super(context);
        this.activity = context;
        this.presenter = presenter;
    }

    @Override
    public void startGetAddress() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("getaddress","true");
            doJsonArray(getAddressUrl,jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            presenter.onGetAddressFail(activity.getResources().getString(R.string.throw_net_exception));
        }
    }


    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONArray jsonArray = (JSONArray) jsonOrArray;

        try {
            for(int i=0;i<jsonArray.length();i++){
                JSONObject userObject = (JSONObject) jsonArray.get(i);
                String name = userObject.getString("name");
                String telephone = userObject.getString("telephone");
                String address = userObject.getString("address");
                int rank = userObject.getInt("rank");
                //如果请求成功这里添加信息
                presenter.onGetAddressSuccess(name,telephone,address,rank);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String errorMessage) {
        presenter.onGetAddressFail(errorMessage);
    }
}
