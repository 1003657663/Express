package extrace.Express.presenter.expressListPresenter;


import extrace.Express.model.express_search.expressListModelImpl;
import extrace.Express.view.express_search_view.express_search_FragmentView;
import extrace.model.ExpressSheet;
import extrace.net.IDataAdapter;
import extrace.Express.model.express_search.expressListModel;
import java.util.List;

/**
 * Created by 黎明 on 2016/4/16.
 */
public class expressListPresenterImpl implements expressListPresenter
{
    express_search_FragmentView express_search_fragmentView;
    expressListModel expressListModel;
    IDataAdapter<List<ExpressSheet>> adapter;
    public expressListPresenterImpl(express_search_FragmentView express_search_fragmentView, IDataAdapter<List<ExpressSheet>> adapter)
    {
       this.express_search_fragmentView=express_search_fragmentView;
        expressListModel=new expressListModelImpl(express_search_fragmentView.getTheActivity(),this,adapter);
    }

    @Override
    public void doSearchExpress(String ID) {
        expressListModel.searchExpressList(ID);
    }

    @Override
    public void onSuccess() {
        express_search_fragmentView.onToastSuccess();
    }

    @Override
    public void onFail() {
        express_search_fragmentView.onToastFail();
    }

}
