package extrace.user.address.getaddressdata;

import android.app.Activity;
import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import extrace.main.MyApplication;
import extrace.model.UserAddress;
import extrace.model.address.City;
import extrace.model.address.Province;
import extrace.model.address.Region;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;
import extrace.user.address.addressEdit.AddressEditFragment;
import extrace.user.address.addressEdit.AddressEditView;

/**
 * Created by chao on 2016/4/25.
 */
public class GetAddressModelImpl extends VolleyHelper implements GetAddressModel {
    private final int SEND = 1;
    private final int RECEIVE = 2;

    private AddressEditView addressEditView;
    private String getProUrl;
    private String getCityUrl;
    private String getRegionUrl;
    private String baseUrl;
    private String submitUpdateUrl;
    private String submitNewUrl;
    private String delAddressUrl;

    private int whichGet;

    public GetAddressModelImpl(Activity activity, AddressEditView addressEditView) {
        super(activity);
        this.addressEditView = addressEditView;
        MyApplication myApplication = (MyApplication) activity.getApplication();
        baseUrl = activity.getResources().getString(R.string.base_url);
        getProUrl = baseUrl + activity.getResources().getString(R.string.address_get_province);
        getCityUrl = baseUrl + activity.getResources().getString(R.string.address_get_city);//后面加上省份号
        getRegionUrl = baseUrl + activity.getResources().getString(R.string.address_get_region);//后面加上城市号
        submitUpdateUrl = baseUrl + activity.getResources().getString(R.string.address_submit_update);
        submitNewUrl = baseUrl + activity.getResources().getString(R.string.address_submit_new);
        delAddressUrl = baseUrl + activity.getResources().getString(R.string.address_delete);
        setShowProgress(false);
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONArray jsonArray;
        SparseArray<Object> sparseArray = null;
        switch (whichGet) {
            case AddressEditFragment.GETPRO:
                jsonArray = (JSONArray) jsonOrArray;
                sparseArray = jsonToProvince(jsonArray);
                addressEditView.onDataReceive(sparseArray, whichGet);
                break;
            case AddressEditFragment.GETCITY:
                jsonArray = (JSONArray) jsonOrArray;
                sparseArray = jsonTocity(jsonArray);
                addressEditView.onDataReceive(sparseArray, whichGet);
                break;
            case AddressEditFragment.GETREGION:
                jsonArray = (JSONArray) jsonOrArray;
                sparseArray = jsonToRegion(jsonArray);
                addressEditView.onDataReceive(sparseArray, whichGet);
                break;
            case AddressEditFragment.ADDRESS_NEW_SEND:
                checkState(jsonOrArray);
                break;
            case AddressEditFragment.ADDRESS_NEW_RECEIVE:
                checkState(jsonOrArray);
                break;
            case AddressEditFragment.ADDRESS_UPDATE_RECEIVE:
                checkState(jsonOrArray);
                break;
            case AddressEditFragment.ADDRESS_UPDATE_SEND:
                checkState(jsonOrArray);
                break;
            case AddressEditFragment.ADDRESS_DELETE:
                checkState(jsonOrArray);
                break;
            default:
                break;
        }
    }

    /**
     * 判断是否获取数据成功
     */
    public void checkState(Object jsonOrArray) {
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        String state = "false";
        try {
            state = jsonObject.getString("updateAddstate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (state.equals("true")) {
            addressEditView.onSubmitSuccess();
        } else {
            addressEditView.onError("提交数据失败，请重试");
        }
    }

    /**
     * json转成相应的类model的SparseArray
     *
     * @param jsonArray
     * @return
     */
    private SparseArray<Object> jsonToProvince(JSONArray jsonArray) {

        SparseArray<Object> provinceSparseArray = new SparseArray<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                Province province = new Province();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                province.setPid(jsonObject.getInt("pid"));
                province.setPname(jsonObject.getString("pname"));
                provinceSparseArray.put(province.getPid(), province);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            addressEditView.onError("获取省份信息出错，请重试");
        }

        return provinceSparseArray;
    }

    private SparseArray<Object> jsonTocity(JSONArray jsonArray) {
        SparseArray<Object> citySparseArray = new SparseArray<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                City city = new City();
                city.setPid(jsonObject.getInt("pid"));
                city.setCid(jsonObject.getInt("cid"));
                city.setCode(jsonObject.getString("code"));
                city.setCname(jsonObject.getString("cname"));
                citySparseArray.put(city.getCid(), city);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            addressEditView.onError("获取城市信息出错，请重试");
        }
        return citySparseArray;
    }

    private SparseArray<Object> jsonToRegion(JSONArray jsonArray) {
        SparseArray<Object> regionSparseArray = new SparseArray<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Region region = new Region();
                region.setArea(jsonObject.getString("area"));
                region.setCityId(jsonObject.getInt("cityId"));
                region.setId(jsonObject.getInt("id"));
                regionSparseArray.put(region.getId(), region);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            addressEditView.onError("获取区域信息出错，请重试");
        }

        return regionSparseArray;
    }

    /**
     * 错误处理方法
     *
     * @param errorMessage
     */
    @Override
    public void onError(String errorMessage) {
        addressEditView.onError(errorMessage);
    }


    @Override
    public void startGet(Integer id) {
        switch (whichGet) {
            case AddressEditFragment.GETPRO:
                doJsonArray(getProUrl, VolleyHelper.GET, null);
                break;
            case AddressEditFragment.GETCITY:
                doJsonArray(getCityUrl + id, VolleyHelper.GET, null);
                break;
            case AddressEditFragment.GETREGION:
                doJsonArray(getRegionUrl + id, VolleyHelper.GET, null);
                break;
            default:
                break;
        }

    }

    /**
     * get相应信息的方法
     */
    @Override
    public void getProvince() {
        whichGet = AddressEditFragment.GETPRO;
        startGet(null);
    }

    @Override
    public void getCityByPro(Integer pid) {
        whichGet = AddressEditFragment.GETCITY;
        startGet(pid);
    }

    @Override
    public void getRegionByCity(Integer cid) {
        whichGet = AddressEditFragment.GETREGION;
        startGet(cid);
    }

    /**
     * 提交更新后的地址信息
     * 收货还是发货通过标志位进行判断
     *
     * @param userAddress
     */
    @Override
    public void submitUpdateSendAddress(UserAddress userAddress) {
        whichGet = AddressEditFragment.ADDRESS_UPDATE_SEND;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", userAddress.getAid());
            jsonObject.put("regionid", userAddress.getRegionid());
            jsonObject.put("address", userAddress.getAddress());
            jsonObject.put("customerid", userAddress.getCustomerid());
            jsonObject.put("name", userAddress.getName());
            jsonObject.put("telephone", userAddress.getTelephone());
            jsonObject.put("rank", userAddress.getRank());
            jsonObject.put("status", SEND);

        } catch (JSONException e) {
            e.printStackTrace();
            onError("提交地址错误，请重试");
        }

        doJson(submitUpdateUrl, VolleyHelper.POST, jsonObject);
    }

    @Override
    public void submitUpdateReceiveAddress(UserAddress userAddress) {
        whichGet = AddressEditFragment.ADDRESS_UPDATE_RECEIVE;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", userAddress.getAid());
            jsonObject.put("regionid", userAddress.getRegionid());
            jsonObject.put("address", userAddress.getAddress());
            jsonObject.put("customerid", userAddress.getCustomerid());
            jsonObject.put("name", userAddress.getName());
            jsonObject.put("telephone", userAddress.getTelephone());
            jsonObject.put("rank", userAddress.getRank());
            jsonObject.put("status", RECEIVE);
        } catch (JSONException e) {
            e.printStackTrace();
            onError("提交地址错误，请重试");
        }
        doJson(submitUpdateUrl, VolleyHelper.POST, jsonObject);
    }

    @Override
    public void submitNewSendAddress(UserAddress userAddress) {
        whichGet = AddressEditFragment.ADDRESS_NEW_SEND;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", userAddress.getAid());
            jsonObject.put("regionid", userAddress.getRegionid());
            jsonObject.put("address", userAddress.getAddress());
            jsonObject.put("customerid", userAddress.getCustomerid());
            jsonObject.put("name", userAddress.getName());
            jsonObject.put("telephone", userAddress.getTelephone());
            jsonObject.put("rank", userAddress.getRank());
            jsonObject.put("status", SEND);
        } catch (JSONException e) {
            e.printStackTrace();
            onError("提交地址错误，请重试");
        }
        doJson(submitNewUrl, VolleyHelper.POST, jsonObject);
    }

    @Override
    public void submitNewReceiveAddress(UserAddress userAddress) {
        whichGet = AddressEditFragment.ADDRESS_NEW_RECEIVE;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", userAddress.getAid());
            jsonObject.put("regionid", userAddress.getRegionid());
            jsonObject.put("address", userAddress.getAddress());
            jsonObject.put("customerid", userAddress.getCustomerid());
            jsonObject.put("name", userAddress.getName());
            jsonObject.put("telephone", userAddress.getTelephone());
            jsonObject.put("rank", userAddress.getRank());
            jsonObject.put("status", RECEIVE);
        } catch (JSONException e) {
            e.printStackTrace();
            onError("提交地址错误，请重试");
        }
        doJson(submitNewUrl, VolleyHelper.POST, jsonObject);
    }

    @Override
    public void addressDelete(UserAddress userAddress) {
        whichGet = AddressEditFragment.ADDRESS_DELETE;
        delAddressUrl += userAddress.getAid();
        doJson(delAddressUrl,VolleyHelper.GET,null);
    }
}
