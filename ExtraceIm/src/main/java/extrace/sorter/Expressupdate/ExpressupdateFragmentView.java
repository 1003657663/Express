package extrace.sorter.Expressupdate;

import extrace.model.ExpressInfo;

/**
 * Created by 黎明 on 2016/5/3.
 */
public interface ExpressUpdateFragmentView {
    void onSuccess(ExpressInfo expressInfo);
    void onFail(String errorMessage);
}
