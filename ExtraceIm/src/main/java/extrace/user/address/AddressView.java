package extrace.user.address;

/**
 * Created by chao on 2016/4/17.
 */
public interface AddressView {
    void addAddress(String name ,String tel,String address,boolean isDefault);//从下层获取地址然后添加到view层
    void onError(String errorMessage);
}
