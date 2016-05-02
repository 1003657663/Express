package extrace.Customer.Express.presenter.express_search_presenter;


import extrace.model.ExpressInfo;
import extrace.Customer.Express.model.express_search_model.Express_list_modelImpl;
import extrace.Customer.Express.view.express_search_view.Express_search_FragmentView;

import extrace.Customer.Express.model.express_search_model.Express_list_model;
import java.util.List;

/**
 * Created by 黎明 on 2016/4/16.
 */
public class Express_List_PresenterImpl implements Express_List_Presenter
{
    Express_search_FragmentView express_search_fragmentView;
    Express_list_model expressListModel;

    public Express_List_PresenterImpl(Express_search_FragmentView express_search_fragmentView)
    {
       this.express_search_fragmentView=express_search_fragmentView;
        expressListModel=new Express_list_modelImpl(express_search_fragmentView.getTheActivity(),this);
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
    public void onSuccess(List<ExpressInfo> list) {
        express_search_fragmentView.onToastSuccess(list);
    }

    @Override
    public void onFail() {
        express_search_fragmentView.onToastFail();
    }


}
