package com.expressba.express.user.address.addressEdit;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by chao on 2016/4/19.
 */
public interface AddressEditView {
    void setRightDelIcon(View v);//设置topbar右边的按钮作为删除按钮
    void setAddressInfo(View v);
    void onDataReceive(SparseArray<Object> dataArray, Integer whichGet);
    void onError(String message);
    void onSubmitSuccess();
    void onDeleteSuccess();
}
