package com.expressba.express.Customer.Express.view.express_search_view;

import android.app.Activity;

import java.util.List;

import com.expressba.express.model.ExpressInfo;

/**
 * Created by 黎明 on 2016/4/17.
 */
public interface ExpressSearchFragmentView {
    void onToastFail();

    Activity getTheActivity();

    void onToastSuccess(ExpressInfo expressInfo);

    void onToastSuccess(List<ExpressInfo> list);
}
