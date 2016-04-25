package extrace.user.address.getaddressdata;

import android.app.Activity;
import android.content.Context;
import android.util.ArrayMap;
import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;

import extrace.main.MyApplication;
import extrace.model.address.City;
import extrace.model.address.Province;
import extrace.model.address.Region;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by chao on 2016/4/25.
 */
public class GetAddressModelImpl extends VolleyHelper implements GetAddressModel {
    public static final int GETPRO = 0;
    public static final int GETCITY = 1;
    public static final int GETREGION = 2;
    GetAddressPresenter presenter;
    private String getProUrl;
    private String getCityUrl;
    private String getRegionUrl;
    private String baseUrl;
    int whichGet;
    public GetAddressModelImpl(Activity activity , GetAddressPresenter presenter){
        super(activity);
        this.presenter = presenter;
        MyApplication myApplication = (MyApplication) activity.getApplication();
        baseUrl = activity.getResources().getString(R.string.base_url);
        getProUrl = baseUrl + activity.getResources().getString(R.string.address_get_province);
        getCityUrl = baseUrl + activity.getResources().getString(R.string.address_get_city);//后面加上省份号
        getRegionUrl = baseUrl + activity.getResources().getString(R.string.address_get_region);//后面加上城市号

        setShowProgress(false);
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONArray jsonArray = (JSONArray) jsonOrArray;
        SparseArray<Object> sparseArray = null;
        switch (whichGet){
            case GETPRO:
                sparseArray = jsonToProvince(jsonArray);
                break;
            case GETCITY:
                sparseArray = jsonTocity(jsonArray);
                break;
            case GETREGION:
                sparseArray = jsonTocity(jsonArray);
                break;
            default:
                break;
        }
        presenter.onDataPresenterReceive(sparseArray,whichGet);
    }

    /**
     * json转成相应的类model的SparseArray
     * @param jsonArray
     * @return
     */
    private SparseArray<Object> jsonToProvince(JSONArray jsonArray){

        SparseArray<Object> provinceSparseArray = new SparseArray<>();
        try {
            for(int i=0;i<jsonArray.length();i++){
                Province province = new Province();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                province.setPid(jsonObject.getInt("pid"));
                province.setPname(jsonObject.getString("pname"));
                provinceSparseArray.put(province.getPid(),province);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return provinceSparseArray;
    }

    private SparseArray<Object> jsonTocity(JSONArray jsonArray){
        SparseArray<Object> citySparseArray = new SparseArray<>();
        try{
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                City city = new City();
                city.setPid(jsonObject.getInt("pid"));
                city.setCid(jsonObject.getInt("cid"));
                city.setCode(jsonObject.getString("code"));
                city.setName(jsonObject.getString("name"));
                citySparseArray.put(city.getCid(),city);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return citySparseArray;
    }
    private SparseArray<Object> jsonToRegion(JSONArray jsonArray){
        SparseArray<Object> regionSparseArray = new SparseArray<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Region region = new Region();
                region.setArea(jsonObject.getString("area"));
                region.setCityId(jsonObject.getInt("cityId"));
                region.setId(jsonObject.getInt("id"));
                regionSparseArray.put(region.getId(),region);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return regionSparseArray;
    }

    /**
     * 错误处理方法
     * @param errorMessage
     */
    @Override
    public void onError(String errorMessage) {

    }


    @Override
    public void startGet(String str){
        switch (whichGet){
            case GETPRO:
                doJson(getProUrl,VolleyHelper.GET,null);
                break;
            case GETCITY:
                doJson(getCityUrl+str,VolleyHelper.GET,null);
                break;
            case GETREGION:
                doJson(getRegionUrl+str,VolleyHelper.GET,null);
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
        whichGet = GETPRO;
        startGet(null);
    }

    @Override
    public void getCityByPro(String province) {
        whichGet = GETCITY;
        startGet(province);
    }

    @Override
    public void getRegionByCity(String city) {
        whichGet = GETREGION;
        startGet(city);
    }
}
