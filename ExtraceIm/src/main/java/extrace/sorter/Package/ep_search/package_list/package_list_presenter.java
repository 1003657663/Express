package extrace.sorter.Package.ep_search.package_list;


import java.util.List;

import extrace.model.Package;
/**
 * Created by 黎明 on 2016/4/26.
 */
public interface package_list_presenter
{
    void onSearchPByPackageID(String packageID);
    void onSuccess(List<Package> list);
     void onFail();
    void onOpen(String packageID);
    void onSuccess();
}
