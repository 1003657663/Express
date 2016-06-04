package com.expressba.expressuser.user.address;

import java.util.ArrayList;

import com.expressba.expressuser.model.UserAddress;

/**
 * Created by chao on 2016/4/17.
 */
public interface AddressPresenter {
    void getSendAddress();
    void getReceiveAddress();
    void onGetAddressSuccess(ArrayList<UserAddress> addressList);
    void onGetAddressFail(String errorMessage);
}
