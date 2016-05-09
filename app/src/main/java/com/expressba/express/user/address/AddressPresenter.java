package com.expressba.express.user.address;

import java.util.ArrayList;
import java.util.HashMap;

import com.expressba.express.model.UserAddress;

/**
 * Created by chao on 2016/4/17.
 */
public interface AddressPresenter {
    void getSendAddress();
    void getReceiveAddress();
    void onGetAddressSuccess(ArrayList<UserAddress> addressList);
    void onGetAddressFail(String errorMessage);
}
