package extrace.sorter.Package.ep_search.express_list;

import java.util.List;

import extrace.model.ExpressInfo;
import extrace.model.Package;

/**
 * Created by 黎明 on 2016/4/26.
 */
public class express_list_presenterImpl implements express_list_presenter {
    private express_list_fragmentView fragmentView;
    private express_list_model model;
    public express_list_presenterImpl(express_list_fragmentView express_list_fragmentView)
    {
        this.fragmentView=express_list_fragmentView;
        model=new express_list_modelImpl(fragmentView.getTheActivity(),this);
    }


    @Override
    public void onSearchEByPackageID(String packageID) {

        model.onSearchEByPackageID(packageID);
    }


    @Override
    public void onFail() {

        fragmentView.onFail();
    }

    @Override
    public void onSuccess(List<ExpressInfo> list) {
        fragmentView.onSuccess(list);
    }

    @Override
    public void onSuccess() {
        fragmentView.Success();
    }

    @Override
    public void onOpen(String packageID) {
        model.onOpen(packageID);
    }
}
