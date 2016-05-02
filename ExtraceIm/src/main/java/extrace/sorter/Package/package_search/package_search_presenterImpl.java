package extrace.sorter.Package.package_search;

import extrace.model.packageInfo;

/**
 * Created by 黎明 on 2016/4/25.
 */
public class Package_search_presenterImpl implements Package_search_presenter
{
    private Package_search_FragmentView package_search_fragmentView;
    private Package_search_model Package_search_model;
    public Package_search_presenterImpl(Package_search_FragmentView package_search_fragmentView)
    {
        this.package_search_fragmentView=package_search_fragmentView;
        Package_search_model =new Package_search_modelImpl(package_search_fragmentView.getTheActivity(),this);
    }
    @Override
    public void onopenPackage(String packageID) {
        Package_search_model.openPackage(packageID);
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
