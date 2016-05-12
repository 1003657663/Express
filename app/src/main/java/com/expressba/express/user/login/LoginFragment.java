package com.expressba.express.user.login;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.expressba.express.main.UIFragment;
import com.expressba.express.myelement.MyFragmentManager;
import com.expressba.express.toolbox.CheckInput;
import com.expressba.express.toolbox.CountDown;
import com.expressba.express.R;

/**
 * Created by chao on 2016/4/16.
 */
public class LoginFragment extends UIFragment implements LoginFragmentView,View.OnClickListener{

    public static final int TEL_ERROR = 0;
    public static final int PASSWORD_ERROR = 1;
    public static final int NAME_ERROT = 2;
    public static final int VERIFY_ERROR = 3;


    private LoginModel loginModel;
    private EditText telEdit;
    private EditText passwordEdit;
    private EditText nameEdit;
    private EditText verifyCodeEdit;
    private Button verifyCodeButton;
    private String tel;
    private String password;
    private String name;
    private String verifyCode;
    private boolean hasUserNameEdit = false;//用户名输入框是否存在
    private boolean isLogin;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_login,container,false);
        TextView topText = (TextView) view.findViewById(R.id.top_bar_center_text);
        topText.setText("登陆");
        loginModel = new LoginModelImpl(getActivity(),this);

        telEdit = (EditText) view.findViewById(R.id.login_tel_edit);
        passwordEdit = (EditText) view.findViewById(R.id.login_password_edit);
        nameEdit = (EditText) view.findViewById(R.id.login_username_edit);
        verifyCodeEdit = (EditText) view.findViewById(R.id.login_tel_verify);
        verifyCodeButton = (Button) view.findViewById(R.id.login_tel_verify_button);
        verifyCodeButton.setOnClickListener(this);
        view.findViewById(R.id.login_button).setOnClickListener(this);
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        view.findViewById(R.id.register_button).setOnClickListener(this);

        initSMS();

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
                isLogin = true;
                //------------------------test
                if(telEdit.getText().toString().equals("")) {
                    tel = "12345678909";
                    password = "123456";
                    loginModel.startLogin(tel, password);
                    break;
                }else {
                    //------------------------test
                    if(checkInput()) {
                        //SMSSDK.submitVerificationCode(COUNTRY_CODE,tel,verifyCode);
                        //---test
                        loginModel.startLogin(tel,password);
                    }
                }
                break;
            case R.id.register_button:
                if(hasUserNameEdit) {
                    isLogin = false;
                    if (checkInput()) {
                        //SMSSDK.submitVerificationCode(COUNTRY_CODE,tel,verifyCode);
                        loginModel.startRegister(tel,password,name);
                    }
                }else {
                    addUserNameEdit();
                }
                break;
            case R.id.login_tel_verify_button:
                tel = telEdit.getText().toString();
                if(CheckInput.checkTel(tel)) {
                    SMSSDK.getVerificationCode(COUNTRY_CODE, tel);
                    setVerifyButton();
                }else{
                    telEdit.setError("手机号错误");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取验证码后，验证码按钮显示倒计时，倒计时完毕，显示重新获取
     */
    CountDown countDown;
    private void setVerifyButton(){

        verifyCodeButton.setClickable(false);
        verifyCodeButton.setBackground(getActivity().getResources().getDrawable(R.drawable.button_radio_grey_background));
        //创建一个循环计时异步线程用来更新主线程
        countDown = new CountDown(getActivity(),verifyCodeButton,60,"重新获取验证码",getActivity().getResources().getDrawable(R.drawable.button_radio_with_background_color));
        countDown.execute(60);
    }


    /**
     * 销毁fragment时候，销毁短信回调,销毁异步任务
     */
    @Override
    public void onDestroyView() {
        SMSSDK.unregisterEventHandler(eh);
        if(countDown!=null && countDown.getStatus() != AsyncTask.Status.FINISHED){
            countDown.cancel(true);
        }
        super.onDestroy();
    }

    /**
     * 注册短信sdk的回调函数
     */
    private EventHandler eh;
    public final static String COUNTRY_CODE = "86";
    public final static int REQ_VERIFY_OK = 1;//请求验证码成功
    public final static int REQ_VERIFY_NO = -1;//请求验证码失败
    public final static int TEST_VERIFY_OK = 2;//校验验证码成功
    public final static int TEST_VERIFY_NO = -2;//校验验证码失败

    private Handler smsHandler;
    private void initSMS(){
        smsHandler = new Handler(){//创建一个回调handler控制UI线程
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case TEST_VERIFY_OK :
                        HashMap<String, String> data = (HashMap<String, String>) msg.obj;
                        if (data.get("phone").equals(tel)) {
                            if (isLogin) {//判断是注册还是登录通过用户姓名输入框的存在性
                                loginModel.startLogin(tel, password);
                            } else {
                                loginModel.startRegister(tel, password, name);
                            }
                        } else {
                            showToast("验证失败，请重新获取验证码");
                        }
                        break;
                    case TEST_VERIFY_NO:
                        showToast("验证码校验失败，请重新获取");
                        break;
                    case REQ_VERIFY_OK :
                        showToast("验证码发送成功，注意查收");
                        break;
                    case REQ_VERIFY_NO:
                        showToast("验证码发送失败，请重新获取");
                }
            }
        };
        eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        smsHandler.sendMessage(smsHandler.obtainMessage(TEST_VERIFY_OK,data));
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        smsHandler.sendMessage(smsHandler.obtainMessage(REQ_VERIFY_OK));
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码失败
                        smsHandler.sendMessage(smsHandler.obtainMessage(TEST_VERIFY_NO));
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码失败
                        smsHandler.sendMessage(smsHandler.obtainMessage(REQ_VERIFY_NO));
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    @Override
    public void onback() {
        /*FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.hide(this);
        ft.commitAllowingStateLoss();*/
        MyFragmentManager.popFragment(LoginFragment.class,null,null,getFragmentManager());
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
        verifyCode = verifyCodeEdit.getText().toString();
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(tel);
        if(tel.length()!=11 || !matcher.matches()) {
            telEdit.setError("必须是电话号");
            return false;
        }
        if(password.length()>20 || password.length()<6){
            passwordEdit.setError("6-20个字符");
            return false;
        }
        if(hasUserNameEdit){
            if(nameEdit.getText().toString().equals("")){
                onError(NAME_ERROT,"请填写用户名");
            }else {
                name = nameEdit.getText().toString();
            }
        }
        if(verifyCode.equals("")){
            onError(VERIFY_ERROR,"请填写验证码");
            return false;
        }
        return true;
    }

    @Override
    public void addUserNameEdit() {
        nameEdit.setVisibility(EditText.VISIBLE);
        hasUserNameEdit = true;
    }

    @Override
    public void onError(int whereError, String errorInfo) {
        switch (whereError){
            case TEL_ERROR:
                telEdit.setError(errorInfo);
                break;
            case PASSWORD_ERROR:
                passwordEdit.setError(errorInfo);
                break;
            case NAME_ERROT:
                nameEdit.setError(errorInfo);
                break;
            case VERIFY_ERROR:
                verifyCodeEdit.setError(errorInfo);
            default:
                break;
        }
    }
}
