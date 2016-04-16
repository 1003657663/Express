package extrace.user.login;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import extrace.ui.main.R;

/**
 * Created by chao on 2016/4/16.
 */
public class LoginFragment extends Fragment implements LoginFragmentView,View.OnClickListener{

    private LoginPresenter loginPresenter;
    private EditText telEdit;
    private EditText passwordEdit;
    private String tel;
    private String password;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_login,container,false);
        TextView topText = (TextView) view.findViewById(R.id.top_bar_center_text);
        topText.setText("登陆");
        loginPresenter = new LoginPresenterImpl(this);

        telEdit = (EditText) view.findViewById(R.id.login_tel_edit);
        passwordEdit = (EditText) view.findViewById(R.id.login_password_edit);
        view.findViewById(R.id.login_button).setOnClickListener(this);
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        view.findViewById(R.id.register_button).setOnClickListener(this);
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
                if(checkInput()) {
                    loginPresenter.startLogin(tel,password);
                }
                break;
            case R.id.register_button:
                if(checkInput()) {
                    loginPresenter.startRegister(tel,password);
                }
                break;
            default:
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

    @Override
    public boolean checkInput() {
        tel = telEdit.getText().toString();
        password = passwordEdit.getText().toString();
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(tel);
        if(tel.length()!=11 || matcher.matches()) {
            telEdit.setError("必须是电话号");
            return false;
        }
        if(password.length()>20 || password.length()<6){
            passwordEdit.setError("6-20个字符");
            return false;
        }
        return true;
    }
}
