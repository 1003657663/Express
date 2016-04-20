package extrace.user.address;

/**
 * Created by chao on 2016/4/17.
 */
public interface AddressPresenter {
    void getAddress();
    void onGetAddressSuccess(String name , String tel, String address, Integer rank);
    void onGetAddressFail(String errorMessage);
}
