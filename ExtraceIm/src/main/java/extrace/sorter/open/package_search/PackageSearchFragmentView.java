package extrace.sorter.open.package_search;

import android.app.Activity;

import extrace.model.PackageInfo;

/**
 * Created by 黎明 on 2016/4/25.
 */
public interface PackageSearchFragmentView {
    Activity getTheActivity();

    void Success(PackageInfo packageInfo);

    void Fail(String errorMessage);

}
