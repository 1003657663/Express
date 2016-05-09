package com.expressba.express.sorter.open.package_search;

import android.app.Activity;

import com.expressba.express.model.PackageInfo;

/**
 * Created by 黎明 on 2016/4/25.
 */
public interface PackageSearchFragmentView {
    Activity getTheActivity();

    void Success(PackageInfo packageInfo);

    void Fail(String errorMessage);

}
