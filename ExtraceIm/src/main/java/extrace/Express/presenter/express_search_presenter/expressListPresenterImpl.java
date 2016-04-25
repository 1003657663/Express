package extrace.Express.presenter.express_search_presenter;


import extrace.Express.model.express_info_model.ExpressInfo;
import extrace.Express.model.express_search_model.expressListModelImpl;
import extrace.Express.view.express_search_view.express_search_FragmentView;
import extrace.model.ExpressSheet;

import extrace.Express.model.express_search_model.expressListModel;
import java.util.List;

/**
 * Created by 黎明 on 2016/4/16.
 */
public class expressListPresenterImpl implements expressListPresenter
{
    express_search_FragmentView express_search_fragmentView;
    expressListModel expressListModel;

    public expressListPresenterImpl(express_search_FragmentView express_search_fragmentView)
    {
       this.express_search_fragmentView=express_search_fragmentView;
        expressListModel=new expressListModelImpl(express_search_fragmentView.getTheActivity(),this);
    }

    @Override
    public void doSearchExpress(String ID) {
        expressListModel.searchExpressList(ID);
    }

    @Override
    public void onSuccess(List<ExpressInfo> list) {
        express_search_fragmentView.onToastSuccess(list);

    }

    @Override
    public void onFail() {
        express_search_fragmentView.onToastFail();
    }

}
