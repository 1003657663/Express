package extrace.user.password;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import extrace.ui.main.R;

/**
 * 用户密码修改
 * Created by chao on 2016/4/28.
 */
public class ChangePasswordFragment extends Fragment implements ChangePasswordView,View.OnClickListener {

    private EditText passwordEdit;
    String password;
    ChangePassworPresenter changePassworPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_change_telephone,container,false);
        ((TextView)view.findViewById(R.id.top_bar_center_text)).setText("密码修改");
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        view.findViewById(R.id.user_change_password_submit).setOnClickListener(this);
        passwordEdit = (EditText) view.findViewById(R.id.user_change_password);

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
        if(password.equals("") || password.length()<6){
            passwordEdit.setError("密码长度不能小于6位");
        }else {
            changePassworPresenter.onSubmit(password);
        }
    }
}
