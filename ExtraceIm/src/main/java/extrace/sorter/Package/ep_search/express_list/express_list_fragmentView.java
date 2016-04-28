package extrace.sorter.Package.ep_search.express_list;

import android.app.Activity;

import java.util.List;

import extrace.model.ExpressInfo;
import extrace.model.Package;


/**
 * Created by 黎明 on 2016/4/26.
 */
public interface express_list_fragmentView {
    Activity getTheActivity();
    void onSuccess(List<ExpressInfo> list);
    void onFail();
    void Success();
}
