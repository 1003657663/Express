package extrace.Customer.Express.presenter.express_edit_presenter;

import extrace.Customer.Express.model.express_edit_model.Express_edit_modelImpl;
import extrace.Customer.Express.view.express_edit_view.Express_edit_FragmentView;
import extrace.Customer.Express.model.express_edit_model.Express_edit_model;


/**
 * Created by 黎明 on 2016/4/16.
 */
public class ExpresspresenterImpl implements Expresspresenter
{
    Express_edit_FragmentView express_edit_fragmentView;
    Express_edit_model express_edit_model;
    public ExpresspresenterImpl(Express_edit_FragmentView express_edit_fragmentView)
    {
       this.express_edit_fragmentView=express_edit_fragmentView;
        express_edit_model =new Express_edit_modelImpl(express_edit_fragmentView.getTheActivity(),this);
    }


    @Override
    public void doNewExpress(int customerId,int send_ID, int receive_ID) {
       express_edit_model.newExpress(customerId,send_ID,receive_ID);
    }
    @Override
    public void onSuccess(String ID) {
        express_edit_fragmentView.onToastSuccess(ID);
    }

    @Override
    public void onFail(String message) {
        express_edit_fragmentView.onToastFail(message);
    }


}
