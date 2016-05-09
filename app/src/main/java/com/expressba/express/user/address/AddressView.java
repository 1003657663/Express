package com.expressba.express.user.address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.expressba.express.model.UserAddress;
import com.expressba.express.model.UserInfo;

/**
 * Created by chao on 2016/4/17.
 */
public interface AddressView {
    void onError(String errorMessage);
    void toEditFragment(UserAddress userAddress, Integer sendOrReceive);
    void refreshAddress(ArrayList<UserAddress> addressList);
}
