package extrace.sorter.Package.ep_search.express_list;

import java.util.List;

import extrace.model.ExpressInfo;

/**
 * Created by 黎明 on 2016/4/26.
 */
public class Express_list_presenterImpl implements Express_list_presenter {
    private Express_list_fragmentView fragmentView;
    private Express_list_model model;
    public Express_list_presenterImpl(Express_list_fragmentView Express_list_fragmentView)
    {
        this.fragmentView= Express_list_fragmentView;
        model=new Express_list_modelImpl(fragmentView.getTheActivity(),this);
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
