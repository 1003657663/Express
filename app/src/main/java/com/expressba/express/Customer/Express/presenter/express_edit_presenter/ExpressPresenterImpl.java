package com.expressba.express.Customer.Express.presenter.express_edit_presenter;

import com.expressba.express.Customer.Express.model.ExpressEdit.ExpressEditModel;
import com.expressba.express.Customer.Express.model.ExpressEdit.ExpressEditModelImpl;
import com.expressba.express.Customer.Express.view.express_edit_view.ExpressEditFragmentView;
/**
 * Created by 黎明 on 2016/4/16.
 */
public class ExpressPresenterImpl implements ExpressPresenter {
    ExpressEditFragmentView express_edit_fragmentView;
    ExpressEditModel express_editModel;

    public ExpressPresenterImpl(ExpressEditFragmentView express_edit_fragmentView) {
        this.express_edit_fragmentView = express_edit_fragmentView;
        express_editModel = new ExpressEditModelImpl(express_edit_fragmentView.getTheActivity(), this);
    }
    @Override
    public void doNewExpress(int customerId, int send_ID, int receive_ID) {
        express_editModel.newExpress(customerId, send_ID, receive_ID);
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
