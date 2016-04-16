package extrace.ui.user.login;

import android.app.Activity;
import android.content.Context;

/**
 * Created by chao on 2016/4/16.
 */
public interface LoginFragmentView {
    public void onback();
    public void showToast(String message);
    public Activity getTheActivity();
}
