package extrace.Express.presenter.express_info_presenter;

import extrace.Express.model.express_info_model.ExpressInfo;

/**
 * Created by 黎明 on 2016/4/19.
 */
public interface express_info_presenter
{
    public void Fail();
    public void Success(ExpressInfo expressInfo);
    public void onfindInfoByID(String ID);
}
