package com.expressba.express.user.password;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.expressba.express.R;
import com.expressba.express.main.UIFragment;

/**
 * 用户密码修改
 * Created by chao on 2016/4/28.
 */
public class ChangePasswordFragment extends UIFragment implements ChangePasswordView,View.OnClickListener {

    private EditText passwordEdit;
    private EditText passwordEdit2;
    String password;
    String password2;
    ChangePassworPresenter changePassworPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_change_password,container,false);
        ((TextView)view.findViewById(R.id.top_bar_center_text)).setText("密码修改");
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        view.findViewById(R.id.user_change_password_submit).setOnClickListener(this);
        passwordEdit = (EditText) view.findViewById(R.id.user_change_password);
        passwordEdit2 = (EditText) view.findViewById(R.id.user_change_password_again);
        changePassworPresenter = new ChangePassworPresenterImpl(getActivity(),this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_bar_left_img:
                getFragmentManager().popBackStack();
                break;
            case R.id.user_change_password_submit:
                submit();
                break;
            default:
                break;
        }
    }


    @Override
    public void onSubmitSuccess() {
        showToast("修改密码成功");
        getFragmentManager().popBackStack();
    }

    @Override
    public void onError(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void submit() {
        password = passwordEdit.getText().toString();
        password2 = passwordEdit2.getText().toString();

        if(password2.equals("")){
            passwordEdit2.setError("不能为空");
            return;
        }
        if(password.equals("") || password.length()<6){
            passwordEdit.setError("密码长度不能小于6位");
            return;
        }
        if(!password2.equals(password)){
            passwordEdit.setError("两次输入密码不一致");
            return;
        }

        changePassworPresenter.onSubmit(password);

    }
}
