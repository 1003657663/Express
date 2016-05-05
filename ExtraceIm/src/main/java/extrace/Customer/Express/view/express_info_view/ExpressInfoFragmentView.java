package extrace.Customer.Express.view.express_info_view;

import android.app.Activity;

import extrace.model.ExpressInfo;

/**
 * Created by 黎明 on 2016/4/19.
 */
public interface ExpressInfoFragmentView
{
    Activity getTheActivity();
    void onFail();
    void onSuccess(ExpressInfo expressInfo);
}
