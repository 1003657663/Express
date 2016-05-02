package extrace.Customer.Express.presenter.express_search_presenter;

import java.util.List;

import extrace.model.ExpressInfo;

/**
 * Created by 黎明 on 2016/4/16.
 */
public interface Express_List_Presenter
{
    void onSuccess(List<ExpressInfo> list);
    void onFail();
    void doSearchByTel(String ID);
    void doSearchByCID(int ID);
    void doSearchByID(String ID);

}
