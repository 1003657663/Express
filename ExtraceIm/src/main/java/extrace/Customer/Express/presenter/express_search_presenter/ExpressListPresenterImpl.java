package extrace.Customer.Express.presenter.express_search_presenter;


import extrace.model.ExpressInfo;
import extrace.Customer.Express.model.ExpressSearchModel.ExpressListModelImpl;
import extrace.Customer.Express.view.express_search_view.ExpressSearchFragmentView;

import extrace.Customer.Express.model.ExpressSearchModel.ExpressListModel;
import java.util.List;

/**
 * Created by 黎明 on 2016/4/16.
 */
public class ExpressListPresenterImpl implements ExpressListPresenter
{
    ExpressSearchFragmentView express_search_fragmentView;
    ExpressListModel expressListModel;

    public ExpressListPresenterImpl(ExpressSearchFragmentView express_search_fragmentView)
    {
       this.express_search_fragmentView=express_search_fragmentView;
        expressListModel=new ExpressListModelImpl(express_search_fragmentView.getTheActivity(),this);
    }

    @Override
    public void doSearchByCID(int ID) {
        expressListModel.searchByCID(ID);
    }
    @Override
    public void doSearchByID(String ID) {
        expressListModel.searchByID(ID);
    }
    @Override
    public void doSearchByTel(String ID) {
        expressListModel.searchByTel(ID);
    }

    @Override
    public void onSuccess(ExpressInfo expressInfo) {
        express_search_fragmentView.onToastSuccess(expressInfo);
    }

    @Override
    public void Success(List<ExpressInfo> list) {
        express_search_fragmentView.onToastSuccess(list);
    }

    @Override
    public void onFail() {
        express_search_fragmentView.onToastFail();
    }


}
