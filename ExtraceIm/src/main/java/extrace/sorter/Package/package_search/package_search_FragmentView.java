package extrace.sorter.Package.package_search;

import android.app.Activity;

import extrace.model.packageInfo;

/**
 * Created by 黎明 on 2016/4/25.
 */
public interface package_search_FragmentView {
    Activity getTheActivity();
    void Success(packageInfo packageInfo);
    void Fail();

}
