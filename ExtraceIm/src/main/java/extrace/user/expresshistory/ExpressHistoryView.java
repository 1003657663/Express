package extrace.user.expresshistory;

import java.util.ArrayList;

import extrace.model.ExpressInfo;

/**
 * Created by songchao on 16/5/8.
 */
public interface ExpressHistoryView {

    void startGetData();
    void onSuccess(ArrayList<ExpressInfo> expressInfos);
    void showMessage(String message);
}
