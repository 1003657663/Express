package extrace.Express.presenter.expressPresenter;

import extrace.Express.model.express_edit.expressModelImpl;
import extrace.Express.view.express_edit_view.express_edit_FragmentView;
import extrace.Express.model.express_edit.expressModel;
import extrace.model.ExpressSheet;
import extrace.net.IDataAdapter;

/**
 * Created by 黎明 on 2016/4/16.
 */
public class expressPresenterImpl implements expressPresenter
{
    express_edit_FragmentView express_edit_fragmentView;
    expressModel expressModel;
    IDataAdapter<ExpressSheet> adapter;
    public expressPresenterImpl(express_edit_FragmentView express_edit_fragmentView,IDataAdapter<ExpressSheet> adapter)
    {
       this.express_edit_fragmentView=express_edit_fragmentView;
        expressModel=new expressModelImpl(express_edit_fragmentView.getTheActivity(),this,adapter);
    }


    @Override
    public void doNewExpress(int send_ID, int receive_ID) {
       expressModel.newExpress(send_ID,receive_ID);
    }
    @Override
    public void onSuccess() {
        express_edit_fragmentView.onToastSuccess();
    }

    @Override
    public void onFail() {
        express_edit_fragmentView.onToastFail();
    }


}
