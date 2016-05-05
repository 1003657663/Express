package extrace.Customer.Express.presenter.express_search_presenter;

import java.util.List;

import extrace.model.ExpressInfo;

/**
 * Created by 黎明 on 2016/4/16.
 */
public interface ExpressListPresenter
{
    void onSuccess(ExpressInfo expressInfo);
    void onFail();
    void doSearchByTel(String ID);
    void doSearchByCID(int ID);
    void doSearchByID(String ID);
    void Success(List<ExpressInfo> list);

}
