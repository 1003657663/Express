package com.expressba.express.sorter.open.package_search;

import com.expressba.express.model.PackageInfo;

/**
 * Created by 黎明 on 2016/4/25.
 */
public interface PackageSearchPresenter {
    void onopenPackage(String packageID);

    void onSuccess(PackageInfo packageInfo);

    void onFail(String errorMessage);

}
