package com.expressba.express.sorter.open.ep_search.package_list;

import java.util.List;

import com.expressba.express.model.Package;

/**
 * Created by 黎明 on 2016/4/26.
 */
public class PackageListPresenterImpl implements PackageListPresenter {
    private PackageListFragmentView fragmentView;
    private PackageListModel model;

    public PackageListPresenterImpl(PackageListFragmentView package_list_fragmentView) {
        this.fragmentView = package_list_fragmentView;
        model = new PackageListModelImpl(fragmentView.getTheActivity(), this);
    }

    /*@Override
    public void onOpen(String packageID) {

        model.onOpen(packageID);
    }
*/
    @Override
    public void onSearchPByPackageID(String packageID) {

        model.onSearchPByPackageID(packageID);
    }

    /* @Override
     public void onSuccess() {
         fragmentView.Success();
     }
 */
    @Override
    public void onFail(String errorMessage) {

        fragmentView.onFail(errorMessage);
    }

    @Override
    public void onPackageSuccess(List<Package> list) {
        fragmentView.onPackageSuccess(list);
    }
}
