package extrace.user.address.getaddressdata;

import extrace.model.UserAddress;

/**
 * Created by chao on 2016/4/25.
 */
public interface GetAddressModel  {
    void startGet(Integer id);
    void getProvince();
    void getCityByPro(Integer pid);
    void getRegionByCity(Integer cid);
    void submitUpdateSendAddress(UserAddress userAddress);
    void submitUpdateReceiveAddress(UserAddress userAddress);
    void submitNewSendAddress(UserAddress userAddress);
    void submitNewReceiveAddress(UserAddress userAddress);
}
