package extrace.user.address.getaddressdata;

/**
 * Created by chao on 2016/4/25.
 */
public interface GetAddressModel  {
    void startGet(String str);
    void getProvince();
    void getCityByPro(String province);
    void getRegionByCity(String city);
}
