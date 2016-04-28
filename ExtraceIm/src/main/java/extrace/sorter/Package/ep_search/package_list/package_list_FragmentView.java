package extrace.sorter.Package.ep_search.package_list;

import android.app.Activity;

import java.util.List;

import extrace.model.Package;


/**
 * Created by 黎明 on 2016/4/26.
 */
public interface package_list_FragmentView {
    Activity getTheActivity();
    void onSuccess(List<Package> list);
    void onFail();
    void Success();
}
