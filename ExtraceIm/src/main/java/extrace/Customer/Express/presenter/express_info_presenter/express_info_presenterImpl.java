package extrace.Customer.Express.presenter.express_info_presenter;

import extrace.model.ExpressInfo;
import extrace.Customer.Express.model.express_info_model.Express_info_model;
import extrace.Customer.Express.model.express_info_model.Express_info_modelImpl;
import extrace.Customer.Express.view.express_info_view.Express_info_fragmentView;


/**
 * Created by 黎明 on 2016/4/19.
 */
public class Express_info_presenterImpl implements Express_info_presenter
{
    private Express_info_fragmentView Express_info_fragmentView;
    private Express_info_model express_info_model;
    private static ExpressInfo expressInfo;
    public Express_info_presenterImpl(Express_info_fragmentView Express_info_fragmentView)
    {
        this.Express_info_fragmentView = Express_info_fragmentView;
        express_info_model=new Express_info_modelImpl(Express_info_fragmentView.getTheActivity(),this);
    }


    @Override
    public void Success(ExpressInfo expressInfo)
    {
        Express_info_fragmentView.onSuccess(expressInfo);
    }

    @Override
    public void Fail() {
        Express_info_fragmentView.onFail();
    }

    @Override
    public void onfindInfoByID(String ID) {
        express_info_model.findInfoByID(ID);
    }
}
