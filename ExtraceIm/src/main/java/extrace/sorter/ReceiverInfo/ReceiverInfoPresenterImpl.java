package extrace.sorter.ReceiverInfo;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import extrace.main.MyApplication;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/5/3.
 */
public class ReceiverInfoPresenterImpl extends VolleyHelper implements ReceiverInfoPresenter {
    private ReceiverInfoFragmentView fragmentView;
    String url, turl;

    public ReceiverInfoPresenterImpl(Activity activity, ReceiverInfoFragmentView fragmentView) {
        super(activity);
        this.fragmentView = fragmentView;
        turl = activity.getResources().getString(R.string.base_url) + activity.getResources().getString(R.string.getExpressInfo_ById);
        url = turl;
    }

    @Override
    public void onError(String errorMessage) {
        url = turl;
        fragmentView.onFail(errorMessage);
    }

    @Override
    public void ReceiveExpress(String ID) {
        url += ID ;
        try {
            doJson(url, VolleyHelper.GET, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            url = turl;
        }
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = new JSONObject();
        try {
            int state = jsonObject.getInt("state");
            if (state == 1)
                fragmentView.onSuccess();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            url = turl;
        }
    }
}
