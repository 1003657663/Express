package extrace.sorter.open.ep_search.express_list;

import java.util.List;

import extrace.model.ExpressInfo;

/**
 * Created by 黎明 on 2016/4/26.
 */
public class ExpressListPresenterImpl implements ExpressListPresenter {
    private ExpressListFragmentView fragmentView;
    private ExpressListModel model;
    public ExpressListPresenterImpl(ExpressListFragmentView ExpressListFragmentView)
    {
        this.fragmentView= ExpressListFragmentView;
        model=new ExpressListModelImpl(fragmentView.getTheActivity(),this);
    }


    @Override
    public void onSearchEByPackageID(String packageID) {

        model.onSearchEByPackageID(packageID);
    }


    @Override
    public void onFail(String errorMessage) {

        fragmentView.onFail(errorMessage);
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
