package extrace.Customer.Express.presenter.express_info_presenter;

import extrace.model.ExpressInfo;

/**
 * Created by 黎明 on 2016/4/19.
 */
public interface ExpressInfoPresenter
{
    void Fail();
    void Success(ExpressInfo expressInfo);
    void onfindInfoByID(String ID);
}
