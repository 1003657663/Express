package com.expressba.express.user.address.getaddressdata;

import com.expressba.express.model.UserAddress;

/**
 * 获取省份，城市，区域信息---接口
 * 删除，提交地址信息---接口
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
    void addressDelete(UserAddress userAddress);
}
