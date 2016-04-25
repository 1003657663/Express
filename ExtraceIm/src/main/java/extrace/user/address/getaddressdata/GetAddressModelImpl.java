package extrace.user.address.getaddressdata;

import android.app.Activity;
import android.content.Context;

import org.json.JSONArray;

import extrace.main.MyApplication;
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
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONArray jsonArray = (JSONArray) jsonOrArray;



        String[] data = new String[jsonArray.length()];
        presenter.onDataPresenterReceive(data);
    }

    @Override
    public void onError(String errorMessage) {

    }


    @Override
    public void startGet(){
        switch (whichGet){
            case GETPRO:
                break;
            case GETCITY:
                break;
            case GETREGION:
                break;
            default:
                break;
        }

    }

    @Override
    public void getProvince() {
        whichGet = GETPRO;
        startGet();
    }

    @Override
    public void getCityByPro(String province) {
        whichGet = GETCITY;
        startGet();
    }

    @Override
    public void getRegionByCity(String city) {
        whichGet = GETREGION;
        startGet();
    }
}
