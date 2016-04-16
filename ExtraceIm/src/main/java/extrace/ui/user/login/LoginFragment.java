package extrace.ui.user.login;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import extrace.ui.main.R;

/**
 * Created by chao on 2016/4/16.
 */
public class LoginFragment extends Fragment implements LoginFragmentView,View.OnClickListener{

    LoginPresenter loginPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_login,container,false);
        TextView topText = (TextView) view.findViewById(R.id.top_bar_center_text);
        topText.setText("登陆");
        loginPresenter = new LoginPresenterImpl(this);

        view.findViewById(R.id.login_button).setOnClickListener(this);
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
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
                loginPresenter.startLogin();
                break;
        }
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
