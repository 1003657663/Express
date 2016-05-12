package com.expressba.express.user.me;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.expressba.express.main.MyApplication;
import com.expressba.express.R;
import com.expressba.express.main.UIFragment;
import com.expressba.express.myelement.MyFragmentManager;
import com.expressba.express.user.address.AddressFragment;
import com.expressba.express.user.expresshistory.ExpressHistoryFragment;
import com.expressba.express.user.expresshistory.ExpressHistoryPresenterImpl;
import com.expressba.express.user.login.LoginFragment;
import com.expressba.express.user.password.ChangePasswordFragment;
import com.expressba.express.user.telephone.ChangeTelFragment;

/**
 * Created by chao on 2016/4/17.
 */
public class MeFragment extends UIFragment implements MeView,View.OnClickListener{
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_me_fragment,container,false);
        TextView textView = (TextView) view.findViewById(R.id.top_bar_center_text);
        textView.setText("我");
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        view.findViewById(R.id.user_receive_address).setOnClickListener(this);
        view.findViewById(R.id.user_tel).setOnClickListener(this);
        view.findViewById(R.id.user_password).setOnClickListener(this);
        view.findViewById(R.id.send_record).setOnClickListener(this);
        view.findViewById(R.id.receive_record).setOnClickListener(this);
        view.findViewById(R.id.about_soft).setOnClickListener(this);
        view.findViewById(R.id.user_send_address).setOnClickListener(this);
        view.findViewById(R.id.user_me_login_out).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_bar_left_img:
                getFragmentManager().popBackStack();
                break;
            case R.id.user_receive_address:
                toUserReceiveAddress();
                break;
            case R.id.user_send_address:
                toUserSendAddress();
                break;
            case R.id.user_tel:
                toTelChange();
                break;
            case R.id.user_password:
                toPasswordChange();
                break;
            case R.id.send_record:
                toSendRecordFragment();
                break;
            case R.id.receive_record:
                toReceiveRecordFragment();
                break;
            case R.id.about_soft:
                toAboutSoftFragment();
                break;
            case R.id.user_me_login_out:
                loginOut();
                break;
        }
    }

    /**
     * 跳转到收货地址
     */
    @Override
    public void toUserReceiveAddress(){
        Bundle bundle = new Bundle();
        bundle.putInt("receiveOrSend", AddressFragment.RECEIVE);
        bundle.putBoolean("isme",true);

        MyFragmentManager.turnFragment(MeFragment.class,AddressFragment.class,bundle,getFragmentManager());
    }

    /**
     * 修改手机号
     */
    @Override
    public void toTelChange() {
        MyFragmentManager.turnFragment(MeFragment.class,ChangeTelFragment.class,null,getFragmentManager());
    }

    /**
     * 修改密码
     */
    @Override
    public void toPasswordChange() {
        MyFragmentManager.turnFragment(MeFragment.class,ChangePasswordFragment.class,null,getFragmentManager());
    }

    /**
     * 用户发货地址
     */
    @Override
    public void toUserSendAddress() {
        Bundle bundle = new Bundle();
        bundle.putInt("receiveOrSend", AddressFragment.SEND);
        bundle.putBoolean("isme",true);
        MyFragmentManager.turnFragment(MeFragment.class,AddressFragment.class,bundle,getFragmentManager());
    }

    /**
     * 注销登陆
     */
    @Override
    public void loginOut() {
        ((MyApplication)getActivity().getApplication()).getUserInfo().setLoginState(false);
        /*LoginFragment loginFragment = new LoginFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.hide(this);
        if(!loginFragment.isAdded()) {
            ft.add(R.id.fragment_container_layout,loginFragment, LoginFragment.class.getSimpleName());
        }else {
            ft.show(loginFragment);
        }
        ft.commitAllowingStateLoss();*/

        MyFragmentManager.turnFragment(MeFragment.class,LoginFragment.class,null,getFragmentManager(),false);
    }


    @Override
    public void toReceiveRecordFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("sendorreceive", ExpressHistoryPresenterImpl.HISTORY_RECEIVE);
        MyFragmentManager.turnFragment(MeFragment.class,ExpressHistoryFragment.class,bundle,getFragmentManager());
    }

    @Override
    public void toSendRecordFragment(){
        Bundle bundle = new Bundle();
        bundle.putInt("sendorreceive", ExpressHistoryPresenterImpl.HISTORY_SEND);
        MyFragmentManager.turnFragment(MeFragment.class,ExpressHistoryFragment.class,bundle,getFragmentManager());
    }

    /**
     *关于软件
     */
    @Override
    public void toAboutSoftFragment(){

    }

    /**
     * 我的投诉
     */
    @Override
    public void toMyComplaint(){

    }

}
