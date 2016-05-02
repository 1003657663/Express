package extrace.sorter.Package.package_search;

import extrace.model.packageInfo;

/**
 * Created by 黎明 on 2016/4/25.
 */
public interface Package_search_presenter {
    void onopenPackage(String packageID);
    void onSuccess(packageInfo packageInfo);
    void onFail();

}
