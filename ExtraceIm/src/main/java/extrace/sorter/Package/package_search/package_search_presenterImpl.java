package extrace.sorter.Package.package_search;

import extrace.model.packageInfo;

/**
 * Created by 黎明 on 2016/4/25.
 */
public class package_search_presenterImpl implements package_search_presenter
{
    private package_search_FragmentView package_search_fragmentView;
    private package_search_model package_search_model;
    public package_search_presenterImpl(package_search_FragmentView package_search_fragmentView)
    {
        this.package_search_fragmentView=package_search_fragmentView;
        package_search_model=new package_search_modelImpl(package_search_fragmentView.getTheActivity(),this);
    }
    @Override
    public void onopenPackage(String packageID) {
        package_search_model.openPackage(packageID);
    }

    @Override
    public void onSuccess(packageInfo packageInfo) {
        package_search_fragmentView.Success(packageInfo);
    }

    @Override
    public void onFail() {
        package_search_fragmentView.Fail();
    }

}
