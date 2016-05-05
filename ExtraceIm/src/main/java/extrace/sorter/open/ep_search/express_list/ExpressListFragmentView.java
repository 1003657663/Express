package extrace.sorter.open.ep_search.express_list;

import android.app.Activity;

import java.util.List;

import extrace.model.ExpressInfo;


/**
 * Created by 黎明 on 2016/4/26.
 */
public interface ExpressListFragmentView {
    Activity getTheActivity();
    void onSuccess(List<ExpressInfo> list);
    void onFail(String errorMessage);
    void Success();
}
