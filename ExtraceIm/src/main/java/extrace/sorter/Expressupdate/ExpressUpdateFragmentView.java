package extrace.sorter.Expressupdate;

import extrace.model.ExpressInfo;

/**
 * Created by 黎明 on 2016/5/8.
 */
public interface ExpressUpdateFragmentView {
    void onFail(String errorMessage);

    void onSuccess(ExpressInfo expressInfo);
}
