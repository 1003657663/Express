package extrace.Express.view.express_info_view;

import android.app.Activity;

import extrace.Express.model.express_info_model.ExpressInfo;
import extrace.user.address.AddressFragment;

/**
 * Created by 黎明 on 2016/4/19.
 */
public interface express_info_fragmentView
{
    public Activity getTheActivity();
    public void onFail();
    public void onSuccess(ExpressInfo expressInfo);
}
