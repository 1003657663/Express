package extrace.user.address;

import java.util.HashMap;
import java.util.Map;

import extrace.model.UserAddress;
import extrace.model.UserInfo;

/**
 * Created by chao on 2016/4/17.
 */
public interface AddressView {
    void addAddress(String name ,String tel,String address,boolean isDefault);//从下层获取地址然后添加到view层
    void onError(String errorMessage);
    void toEditFragment(UserAddress userAddress,Integer sendOrReceive);
    void refreshAddress(HashMap<Integer,UserAddress> addressMap);
}
