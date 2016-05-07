package extrace.user.search;

import java.util.ArrayList;

import extrace.model.ExpressSearchInfo;

/**
 * Created by songchao on 16/5/1.
 */
public interface SearchExpressView {
    void getBundle();
    void init();
    void onError(String errorMessage);
    void getSearchInfo();
    void onRequestSuccess(ArrayList<ExpressSearchInfo> expressSearchInfos);
}
