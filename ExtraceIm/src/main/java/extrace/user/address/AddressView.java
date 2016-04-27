package extrace.user.address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import extrace.model.UserAddress;
import extrace.model.UserInfo;

/**
 * Created by chao on 2016/4/17.
 */
public interface AddressView {
    void onError(String errorMessage);
    void toEditFragment(UserAddress userAddress,Integer sendOrReceive);
    void refreshAddress(ArrayList<UserAddress> addressList);
}
