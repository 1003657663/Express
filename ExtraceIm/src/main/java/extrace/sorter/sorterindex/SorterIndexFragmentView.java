package extrace.sorter.SorterIndex;

import extrace.model.PackageInfo;

/**
 * Created by 黎明 on 2016/5/4.
 */
public interface SorterIndexFragmentView {
    void onSuccess(PackageInfo packageInfo);
    void onError(String errorMessage);
}
