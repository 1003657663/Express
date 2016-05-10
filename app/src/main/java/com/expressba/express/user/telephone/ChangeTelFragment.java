package com.expressba.express.user.telephone;

import android.app.Fragment;
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


import com.expressba.express.R;
import com.expressba.express.main.UIFragment;
import com.expressba.express.user.login.LoginFragment;

/**
 * Created by chao on 2016/4/28.
 */
public class ChangeTelFragment extends UIFragment implements View.OnClickListener,ChangeTelView{
    private EditText telEdit;

    @Override
    public void getVerify() {

    }

    private EditText verifyEdit;
    private Button verifyGetButton;
    private Button verifySubmitButton;

    private String tel;
    private String verify;

    ChangeTelPresenterImpl changeTelPresenterImpl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_change_telephone,container,false);

        changeTelPresenterImpl = new ChangeTelPresenterImpl(getActivity(),this);

        ((TextView)view.findViewById(R.id.top_bar_center_text)).setText("手机号修改");
        telEdit = (EditText) view.findViewById(R.id.user_change_tel);
        verifyEdit = (EditText) view.findViewById(R.id.user_change_tel_verify);
        verifyGetButton = (Button) view.findViewById(R.id.user_change_tel_get_verify);
        verifySubmitButton = (Button) view.findViewById(R.id.user_change_tel_submit);

        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        verifyGetButton.setOnClickListener(this);
        verifySubmitButton.setOnClickListener(this);

       // initSMS();//初始化验证码发送

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_bar_left_img:
                getFragmentManager().popBackStack();
                break;
            case R.id.user_change_tel_submit:
              //  checeVerify();
                break;
            case R.id.user_change_tel_get_verify:
                getVerify();
                break;
            default:
                break;
        }
    }

    private void checeVerify(){
        verify = verifyEdit.getText().toString();
        tel = telEdit.getText().toString();
        if(verify!=null){
         //   SMSSDK.submitVerificationCode(LoginFragment.COUNTRY_CODE,tel,verify);
        }else{
            verifyEdit.setError("请填写验证码");
        }
    }

    /**
     * 获取验证码
     */
    //CountDown countDown;
  //  @Override
  /*  public void getVerify() {
        tel = telEdit.getText().toString();
        if(CheckInput.checkTel(tel)){
            verifyGetButton.setBackground(getActivity().getResources().getDrawable(R.color.button_grey));
            SMSSDK.getVerificationCode(LoginFragment.COUNTRY_CODE, tel);
            countDown = new CountDown(getActivity(),verifyGetButton,60,"重新获取验证码",getActivity().getResources().getDrawable(R.color.button_light));
            countDown.execute(60);
        }else {
            telEdit.setError("手机号错误");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroy();
        if(countDown!=null && countDown.getStatus() != AsyncTask.Status.FINISHED){
            countDown.cancel(true);
        }
    }*/

    /**
     * 初始化验证码发送器
     */
  //  private EventHandler eh;
    Handler smsHandler;
    private void initSMS(){
        smsHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case LoginFragment.TEST_VERIFY_OK :
                        HashMap<String, String> data = (HashMap<String, String>) msg.obj;
                        if (data.get("phone").equals(tel)) {
                            submit();
                        } else {
                            showToast("验证失败，请重新获取验证码");
                        }
                        break;
                    case LoginFragment.TEST_VERIFY_NO:
                        showToast("验证码校验失败，请重新获取");
                        break;
                    case LoginFragment.REQ_VERIFY_OK :
                        showToast("验证码发送成功，注意查收");
                        break;
                    case LoginFragment.REQ_VERIFY_NO:
                        showToast("验证码发送失败，请重新获取");
                }
            }
        };
       // eh=new EventHandler(){
          //  @Override
          //  public void afterEvent(int event, int result, Object data) {
           //     if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
             //       if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
             //           smsHandler.sendMessage(smsHandler.obtainMessage(LoginFragment.TEST_VERIFY_OK,data));
             //       }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
              //          smsHandler.sendMessage(smsHandler.obtainMessage(LoginFragment.REQ_VERIFY_OK));
              //      }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                    }
            //    }else{
             //       ((Throwable)data).printStackTrace();
              //      //回调完成
              //////      if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码失败
                //        smsHandler.sendMessage(smsHandler.obtainMessage(LoginFragment.TEST_VERIFY_NO));
                //    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码失败
                 //       smsHandler.sendMessage(smsHandler.obtainMessage(LoginFragment.REQ_VERIFY_NO));
                  //  }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                //    }
             //   }
         //   }
      //  };
      //  SMSSDK.registerEventHandler(eh); //注册短信回调

    //}

    private void showToast(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    /**
     * 提交用户手机号数据
     */
    @Override
    public void submit() {
        changeTelPresenterImpl.onSubmit(tel);
    }

    /**
     * 提交成功回调
     */
    @Override
    public void onSubmitSuccess() {
        showToast("电话号码修改成功");
        getFragmentManager().popBackStack();
    }

    /**
     * 错误处理
     * @param errormessage
     */
    @Override
    public void onError(String errormessage) {
        showToast(errormessage);
    }
}
