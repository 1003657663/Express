package com.expressba.express.user.address;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.expressba.express.main.MyApplication;
import com.expressba.express.model.UserAddress;

/**
 * Created by chao on 2016/4/17.
 */
public class AddressPresenterImpl implements AddressPresenter{
    Activity activity;
    MyApplication application;
    AddressModel addressModel;
    AddressView addressView;
    public AddressPresenterImpl(Activity activity,AddressView addressView){
        this.activity = activity;
        this.addressView = addressView;
        application = (MyApplication) activity.getApplication();
        addressModel = new AddressModelImpl(activity,this);
    }

    @Override
    public void getSendAddress() {
        addressModel.startGetSendAddress();
    }

    @Override
    public void getReceiveAddress() {
        addressModel.startGetReceiveAddress();
    }

    @Override
    public void onGetAddressSuccess(ArrayList<UserAddress> addressList) {
        addressView.refreshAddress(addressList);
    }

    @Override
    public void onGetAddressFail(String errorMessage) {
        addressView.onError(errorMessage);
    }
}
