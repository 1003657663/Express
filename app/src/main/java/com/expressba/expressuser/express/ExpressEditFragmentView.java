package com.expressba.expressuser.express;

import android.app.Activity;

/**
 * Created by 黎明 on 2016/4/16.
 */
public interface ExpressEditFragmentView {
    void onToastSuccess(String ID);

    void onToastFail(String string);

    Activity getTheActivity();
}
