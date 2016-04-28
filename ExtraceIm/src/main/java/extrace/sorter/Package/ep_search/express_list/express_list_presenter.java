package extrace.sorter.Package.ep_search.express_list;


import java.util.List;

import extrace.model.ExpressInfo;


/**
 * Created by 黎明 on 2016/4/26.
 */
public interface express_list_presenter
{
    void onSearchEByPackageID(String packageID);
    void onSuccess(List<ExpressInfo> list);
    void onFail();
    void onOpen(String packageID);
    void onSuccess();
}
