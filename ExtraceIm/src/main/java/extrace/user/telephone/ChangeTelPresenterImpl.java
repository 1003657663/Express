package extrace.user.telephone;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by chao on 2016/4/28.
 */
public class ChangeTelPresenterImpl extends VolleyHelper implements ChangeTelPresenter {

    ChangeTelView changeTelView;
    String changTelUrl;

    public ChangeTelPresenterImpl(Activity context, ChangeTelView changeTelView) {
        super(context);
        this.changeTelView = changeTelView;
        changTelUrl = context.getResources().getString(R.string.base_url)+
                context.getResources().getString(R.string.address_delete);
    }

    /**
     * 提交用户手机数据
     */
    @Override
    public void onSubmit(String tel) {
        doJson(changTelUrl,VolleyHelper.GET,null);
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        try {
            if(jsonObject.getString("state").equals("true")){
                changeTelView.onSubmitSuccess();
            }else {
                changeTelView.onError("修改失败，请重试");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            changeTelView.onError("修改失败，请重试");
        }
    }

    @Override
    public void onError(String errorMessage) {
        changeTelView.onError(errorMessage);
    }
}
