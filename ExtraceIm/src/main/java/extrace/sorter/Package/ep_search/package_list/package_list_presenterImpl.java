package extrace.sorter.Package.ep_search.package_list;

import java.util.List;
import extrace.model.Package;
/**
 * Created by 黎明 on 2016/4/26.
 */
public class Package_list_presenterImpl implements Package_list_presenter {
    private Package_list_FragmentView fragmentView;
    private Package_list_model model;
    public Package_list_presenterImpl(Package_list_FragmentView package_list_fragmentView)
    {
        this.fragmentView=package_list_fragmentView;
        model=new Package_list_modelImpl(fragmentView.getTheActivity(),this);
    }

    @Override
    public void onOpen(String packageID) {

        model.onOpen(packageID);
    }

    @Override
    public void onSearchPByPackageID(String packageID) {

        model.onSearchPByPackageID(packageID);
    }

    @Override
    public void onSuccess() {
        fragmentView.Success();
    }

    @Override
    public void onFail() {

        fragmentView.onFail();
    }

    @Override
    public void onSuccess(List<Package> list) {
        fragmentView.onSuccess(list);
    }
}
