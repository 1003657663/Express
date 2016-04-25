package extrace.user.address.getaddressdata;

import android.util.SparseArray;

/**
 * Created by chao on 2016/4/25.
 */
public interface GetAddressPresenter {
    void onDataPresenterReceive(SparseArray<Object> dataArray,Integer whichGet);
}
