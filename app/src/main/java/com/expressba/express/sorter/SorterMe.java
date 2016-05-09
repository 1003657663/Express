package com.expressba.express.sorter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.expressba.express.main.MyApplication;
import com.expressba.express.sorter.close.add_package_list.AddPackageListFragment;
import com.expressba.express.sorter.work.SearchWorkFragment;
import com.expressba.express.R;
import com.expressba.express.user.address.AddressFragment;
import com.expressba.express.user.password.ChangePasswordFragment;
import com.expressba.express.user.telephone.ChangeTelFragment;

/**
 * Created by 黎明 on 2016/5/4.
 */
public class SorterMe extends Fragment implements SorterMeFragmentView, View.OnClickListener {

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sorter_me_fragment, container, false);
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
        view.findViewById(R.id.my_complaint).setOnClickListener(this);
        view.findViewById(R.id.user_send_address).setOnClickListener(this);
        view.findViewById(R.id.user_me_login_out).setOnClickListener(this);
        view.findViewById(R.id.user_work_log).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.my_complaint:
                toMyComplaint();
                break;
            case R.id.user_me_login_out:
                loginOut();
                break;
            case R.id.user_work_log:
                toWorklog();
        }
    }

    /**
     * 跳转到收货地址
     */
    @Override
    public void toUserReceiveAddress() {
        AddressFragment addressFragment = new AddressFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("receiveOrSend", AddressFragment.RECEIVE);
        addressFragment.setArguments(bundle);

        transaction.replace(R.id.fragment_container_layout, addressFragment);
        transaction.addToBackStack("mefragment");
        transaction.commit();
    }

    /**
     * 修改手机号
     */
    @Override
    public void toTelChange() {
        ChangeTelFragment changeTelFragment = new ChangeTelFragment();

        transaction.replace(R.id.fragment_container_layout, changeTelFragment);
        transaction.addToBackStack("mefragment");
        transaction.commit();
    }

    /**
     * 修改密码
     */
    @Override
    public void toPasswordChange() {
        ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();

        transaction.replace(R.id.fragment_container_layout, changePasswordFragment);
        transaction.addToBackStack("mefragment");
        transaction.commit();
    }

    /**
     * 用户发货地址
     */
    @Override
    public void toUserSendAddress() {
        AddressFragment addressFragment = new AddressFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("receiveOrSend", AddressFragment.SEND);
        addressFragment.setArguments(bundle);

        transaction.replace(R.id.fragment_container_layout, addressFragment);
        transaction.addToBackStack("mefragment");
        transaction.commit();
    }

    /**
     * 注销登陆
     */
    @Override
    public void loginOut() {
        ((MyApplication) getActivity().getApplication()).getUserInfo().setLoginState(false);
        getFragmentManager().popBackStack();
        transaction.commit();
    }

    @Override
    public void toSendRecordFragment() {
        AddPackageListFragment fragment = new AddPackageListFragment();
        Bundle bundle = new Bundle();
        // bundle.putString("packageID", "69026465584374");
        bundle.putString("packageID", ((MyApplication) getActivity().getApplication()).getEmployeesInfo().getSendPackageId());
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_layout, fragment);
        transaction.addToBackStack("SorterMe");
        transaction.commit();
    }

    /**
     * 关于软件
     */
    @Override
    public void toAboutSoftFragment() {

    }

    /**
     * 我的投诉
     */
    @Override
    public void toMyComplaint() {

    }

    //工作量查询
    @Override
    public void toWorklog() {
        SearchWorkFragment fragment = new SearchWorkFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.fragment_container_layout, fragment);
        transaction.addToBackStack("SorterMe");
        transaction.commit();
    }

    @Override
    public void toReceiveRecordFragment() {
        AddPackageListFragment fragment = new AddPackageListFragment();
        Bundle bundle = new Bundle();
        //  bundle.putString("packageID", "69026465584374");
        bundle.putString("packageID", ((MyApplication) getActivity().getApplication()).getEmployeesInfo().getRecvPackageId());
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_layout, fragment);
        transaction.addToBackStack("SorterMe");
        transaction.commit();
    }
}


