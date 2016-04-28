package extrace.Customer.Express.presenter.express_info_presenter;

import extrace.model.ExpressInfo;
import extrace.Customer.Express.model.express_info_model.express_info_model;
import extrace.Customer.Express.model.express_info_model.express_info_modelImpl;
import extrace.Customer.Express.view.express_info_view.express_info_fragmentView;


/**
 * Created by 黎明 on 2016/4/19.
 */
public class express_info_presenterImpl  implements express_info_presenter
{
    private express_info_fragmentView express_info_fragmentView;
    private express_info_model express_info_model;
    private static ExpressInfo expressInfo;
    public express_info_presenterImpl(express_info_fragmentView express_info_fragmentView)
    {
        this.express_info_fragmentView=express_info_fragmentView;
        express_info_model=new express_info_modelImpl(express_info_fragmentView.getTheActivity(),this);
    }


    @Override
    public void Success(ExpressInfo expressInfo)
    {
        express_info_fragmentView.onSuccess(expressInfo);
    }

    @Override
    public void Fail() {
        express_info_fragmentView.onFail();
    }

    @Override
    public void onfindInfoByID(String ID) {
        express_info_model.findInfoByID(ID);
    }
}
