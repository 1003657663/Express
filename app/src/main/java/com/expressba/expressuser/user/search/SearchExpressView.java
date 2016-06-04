package com.expressba.expressuser.user.search;

import android.graphics.Bitmap;

import java.util.ArrayList;

import com.expressba.expressuser.model.ExpressSearchInfo;

/**
 * Created by songchao on 16/5/1.
 */
public interface SearchExpressView {
    void onError(String errorMessage);
    void getSearchInfo();
    void onRequestSuccess(ArrayList<ExpressSearchInfo> expressSearchInfos);
    void onGetExpressImageSuccess(int whichImage,Bitmap bitmap);
}
