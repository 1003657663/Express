package extrace.Express.presenter.expressPresenter;

import android.util.Log;
import android.widget.Toast;

import extrace.Express.model.express_edit.expressModelImpl;
import extrace.Express.view.express_edit_view.express_edit_FragmentView;
import extrace.Express.model.express_edit.expressModel;


/**
 * Created by 黎明 on 2016/4/16.
 */
public class expressPresenterImpl implements expressPresenter
{
    express_edit_FragmentView express_edit_fragmentView;
    expressModel expressModel;
    public expressPresenterImpl(express_edit_FragmentView express_edit_fragmentView)
    {
       this.express_edit_fragmentView=express_edit_fragmentView;
        expressModel=new expressModelImpl(express_edit_fragmentView.getTheActivity(),this);
    }


    @Override
    public void doNewExpress(int send_ID, int receive_ID) {
        Log.i("tag","presenter");
       expressModel.newExpress(send_ID,receive_ID);
    }
    @Override
    public void onSuccess(String ID) {
        express_edit_fragmentView.onToastSuccess(ID);
    }

    @Override
    public void onFail() {
        express_edit_fragmentView.onToastFail();
    }


}
