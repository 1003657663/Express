package extrace.user.address.getaddressdata;

import android.app.Activity;
import android.content.Context;

import org.json.JSONArray;

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
    final int GETPRO = 0;
    final int GETCITY = 1;
    final int GETREGION = 2;
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


        String[] data = new String[jsonArray.length()];
        presenter.onDataPresenterReceive(data);
    }

    private Province jsonToProvince(JSONArray jsonArray){
        Province province = new Province();



        return province;
    }

    private City jsonTocity(JSONArray jsonArray){
        City city = new City();

        return city;
    }
    private Region jsonToRegion(JSONArray jsonArray){
        Region region = new Region();

        return region;
    }

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
