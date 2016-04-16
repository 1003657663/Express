package extrace.ui.user.login;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import extrace.loader.LoginLoader;
import extrace.model.UserApplication;
import extrace.ui.main.R;

/**
 * Created by chao on 2016/4/12.
 */
public class LoginFragment extends Fragment implements LoginFragmentView,View.OnClickListener{

    LoginPresenter loginPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_login,container,false);
        TextView topText = (TextView) view.findViewById(R.id.top_bar_center_text);
        topText.setText("登陆");
        loginPresenter = new LoginPresenterImpl(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_bar_left_img :
                //点击后退按钮
                onback();
                break;
            case R.id.login_button:
                //点击登陆按钮登陆----点击后执行
                Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        if(msg.arg1 == 1) {
                            loginPresenter.onLoginSuccess();
                        }else {
                            loginPresenter.onLoginFail();
                        }
                    }
                };
                LoginLoader loginLoader = new LoginLoader(getActivity(),handler);
                loginLoader.sendLoginInfo();
                break;
        }
    }



    public void onLoginFail() {
        Toast.makeText(getActivity(), "登陆失败，手机号或密码错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onback() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public Activity getTheActivity() {
        return getActivity();
    }
}
