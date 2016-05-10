package com.expressba.express.user.search;

import java.util.ArrayList;

import com.expressba.express.model.ExpressSearchInfo;

/**
 * Created by songchao on 16/5/1.
 */
public interface SearchExpressView {
    void getMyBundle();
    void init();
    void onError(String errorMessage);
    void getSearchInfo();
    void onRequestSuccess(ArrayList<ExpressSearchInfo> expressSearchInfos);
}
