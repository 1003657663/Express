package extrace.sorter.Expressupdate;

import android.app.Activity;

import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/28.
 */
public class ExpressUpdatePresenterImpl extends VolleyHelper implements ExpressUpdatePresenter
{
    private ExpressUpdateFragmentView fragmentView;
    String url,turl;
    public ExpressUpdatePresenterImpl(Activity activity, ExpressUpdateFragmentView fragmentview)
    {
        super(activity);
        this.fragmentView =fragmentview;
        turl= activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.getExpressInfo_ById);
        url=turl;
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {

    }

    @Override
    public void onError(String errorMessage) {
        fragmentView.onFail(errorMessage);
    }

    @Override
    public void getExpressInfoByID(String ID) {
        url+=ID;
        try {
            doJson(url,VolleyHelper.GET,null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

