package extrace.user.search;

import android.app.Activity;

import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by songchao on 16/5/1.
 */
public class SearchExpressPresenterImpl extends VolleyHelper implements SearchExpressPresenter{

    private SearchExpressView searchExpressView;
    private String searchUrl;
    public SearchExpressPresenterImpl(Activity context,SearchExpressView searchExpressView) {
        super(context);
        this.searchExpressView = searchExpressView;
        String baseUrl = context.getResources().getString(R.string.base_url);
        searchUrl =
    }

    @Override
    public void startGetExpressInfo(String expressID) {

    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
    }

    @Override
    public void onError(String errorMessage) {

    }
}
