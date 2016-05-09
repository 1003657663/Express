package com.expressba.express.sorter.open.package_search;

import com.expressba.express.model.PackageInfo;

/**
 * Created by 黎明 on 2016/4/25.
 */
public class PackageSearchPresenterImpl implements PackageSearchPresenter {
    private PackageSearchFragmentView package_search_fragmentView;
    private PackageSearchModel PackageSearchModel;

    public PackageSearchPresenterImpl(PackageSearchFragmentView package_search_fragmentView) {
        this.package_search_fragmentView = package_search_fragmentView;
        PackageSearchModel = new PackageSearchModelImpl(package_search_fragmentView.getTheActivity(), this);
    }

    @Override
    public void onopenPackage(String packageID) {
        PackageSearchModel.openPackage(packageID);
    }

    @Override
    public void onSuccess(PackageInfo packageInfo) {
        package_search_fragmentView.Success(packageInfo);
    }

    @Override
    public void onFail(String errorMessage) {
        package_search_fragmentView.Fail(errorMessage);
    }

}
