package com.expressba.express.Customer.Express.presenter.express_info_presenter;

import com.expressba.express.model.ExpressInfo;
import com.expressba.express.Customer.Express.model.expressInfoModel.ExpressInfoModel;
import com.expressba.express.Customer.Express.model.expressInfoModel.ExpressInfoModelImpl;
import com.expressba.express.Customer.Express.view.express_info_view.ExpressInfoFragmentView;


/**
 * Created by 黎明 on 2016/4/19.
 */
public class ExpressInfoPresenterImpl implements ExpressInfoPresenter {
    private ExpressInfoFragmentView ExpressInfoFragmentView;
    private ExpressInfoModel express_infoModel;
    private static ExpressInfo expressInfo;

    public ExpressInfoPresenterImpl(ExpressInfoFragmentView ExpressInfoFragmentView) {
        this.ExpressInfoFragmentView = ExpressInfoFragmentView;
        express_infoModel = new ExpressInfoModelImpl(ExpressInfoFragmentView.getTheActivity(), this);
    }


    @Override
    public void Success(ExpressInfo expressInfo) {
        ExpressInfoFragmentView.onSuccess(expressInfo);
    }

    @Override
    public void Fail() {
        ExpressInfoFragmentView.onFail();
    }

    @Override
    public void onfindInfoByID(String ID) {
        express_infoModel.findInfoByID(ID);
    }
}
