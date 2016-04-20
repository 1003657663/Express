package extrace.user.address;

import android.app.Activity;

import extrace.main.MyApplication;

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
    public void getAddress() {
        addressModel.startGetAddress();
    }

    @Override
    public void onGetAddressSuccess(String name , String tel, String address, Integer rank) {
        boolean isDefault = false;
        if(rank==0){
            isDefault = true;
        }
        addressView.addAddress(name ,tel,address,isDefault);
    }

    @Override
    public void onGetAddressFail(String errorMessage) {
        addressView.onError(errorMessage);
    }
}
