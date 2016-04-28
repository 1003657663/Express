package extrace.user.password;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by chao on 2016/4/28.
 */
public class ChangePassworPresenterImpl extends VolleyHelper implements ChangePassworPresenter{
    ChangePasswordView changePasswordView;
    String changePassworeUrl;
    public ChangePassworPresenterImpl(Activity context,ChangePasswordView changePasswordView) {
        super(context);
        this.changePasswordView = changePasswordView;
        changePassworeUrl = context.getResources().getString(R.string.base_url)+
                context.getResources().getString(R.string.user_change_password);
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        try {
            if(jsonObject.getString("state").equals("true")){
                changePasswordView.onSubmitSuccess();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String errorMessage) {
        changePasswordView.onError(errorMessage);
    }

    @Override
    public void onSubmit(String password) {
        doJson(changePassworeUrl,VolleyHelper.GET,null);
    }
}
