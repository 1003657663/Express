package com.expressba.express.sorter.open.ep_search.package_list;

import android.app.Activity;

import java.util.List;

import com.expressba.express.model.Package;

/**
 * Created by 黎明 on 2016/4/26.
 */
public interface PackageListFragmentView {
    Activity getTheActivity();

    void onPackageSuccess(List<Package> list);

    void onFail(String errorMessage);

    void OpenSuccess();
}
