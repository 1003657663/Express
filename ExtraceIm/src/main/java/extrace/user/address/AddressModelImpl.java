package extrace.user.address;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import extrace.main.MyApplication;
import extrace.model.UserAddress;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by chao on 2016/4/17.
 */
public class AddressModelImpl extends VolleyHelper implements AddressModel {

    AddressPresenter presenter;
    Activity activity;
    String sendAddressUrl;
    String getAddressUrl;
    public AddressModelImpl(Activity context,AddressPresenter presenter) {
        super(context);
        this.activity = context;
        this.presenter = presenter;
        String telephone = ((MyApplication)context.getApplication()).getUserInfo().getTelephone();
        sendAddressUrl = context.getResources().getString(R.string.base_url)+context.getResources().getString(R.string.address_get_send) + telephone;
        getAddressUrl = context.getResources().getString(R.string.base_url)+context.getResources().getString(R.string.address_get_get) + telephone;

    }

    @Override
    public void startGetSendAddress() {
        try {
            doJsonArray(sendAddressUrl,VolleyHelper.GET,null);
        } catch (Exception e) {
            e.printStackTrace();
            presenter.onGetAddressFail(activity.getResources().getString(R.string.throw_net_exception));
        }
    }

    @Override
    public void startGetReceiveAddress() {
        try {
            doJsonArray(getAddressUrl,VolleyHelper.GET,null);
        } catch (Exception e) {
            e.printStackTrace();
            presenter.onGetAddressFail(activity.getResources().getString(R.string.throw_net_exception));
        }
    }


    /**
     * 收到用户地址信息后，取出每一个信息，存入
     * @param jsonOrArray
     */
    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONArray jsonArray = (JSONArray) jsonOrArray;
        HashMap<Integer,UserAddress> addressMap = new HashMap<>();
        try {
            for(int i=0;i<jsonArray.length();i++){
                JSONObject userObject = (JSONObject) jsonArray.get(i);
                Integer rank = userObject.getInt("aid");
                UserAddress userAddress = new UserAddress();

                userAddress.setAid(userObject.getInt("aid"));
                userAddress.setCustomerid(userObject.getInt("customerid"));
                userAddress.setRank(userObject.getInt("rank"));
                userAddress.setProvince(userObject.getString("province"));
                userAddress.setCity(userObject.getString("city"));
                userAddress.setRegion(userObject.getString("region"));
                userAddress.setAddress(userObject.getString("address"));
                userAddress.setTelephone(userObject.getString("telephone"));
                userAddress.setName(userObject.getString("name"));

                addressMap.put(rank,userAddress);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //如果请求成功这里添加信息
        presenter.onGetAddressSuccess(addressMap);
    }

    @Override
    public void onError(String errorMessage) {
        presenter.onGetAddressFail(errorMessage);
    }
}
