package com.expressba.expressuser.user.address;

import java.util.ArrayList;

import com.expressba.expressuser.model.UserAddress;

/**
 * Created by chao on 2016/4/17.
 */
public interface AddressView {
    void onError(String errorMessage);
    void toEditFragment(UserAddress userAddress, Integer sendOrReceive);
    void refreshAddress(ArrayList<UserAddress> addressList);
}
