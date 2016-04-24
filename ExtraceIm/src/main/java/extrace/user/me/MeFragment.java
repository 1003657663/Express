package extrace.user.me;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import extrace.main.MyApplication;
import extrace.ui.main.R;
import extrace.user.address.AddressFragment;
import extrace.user.login.LoginFragment;

/**
 * Created by chao on 2016/4/17.
 */
public class MeFragment extends Fragment implements MeView,View.OnClickListener{
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
        view.findViewById(R.id.about_soft).setOnClickListener(this);
        view.findViewById(R.id.my_complaint).setOnClickListener(this);
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
                break;
            case R.id.user_password:
                break;
            case R.id.send_record:
                break;
            case R.id.about_soft:
                break;
            case R.id.my_complaint:
                break;
            case R.id.user_me_login_out:
                loginOut();
                break;
        }
    }

    public final static int RECEIVE = 1;
    public final static int SEND = 0;
    @Override
    public void toUserReceiveAddress(){
        AddressFragment addressFragment = new AddressFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("receiveOrSend", RECEIVE);
        addressFragment.setArguments(bundle);

        transaction.replace(R.id.fragment_container_layout,addressFragment);
        transaction.addToBackStack("mefragment");
        transaction.commit();
    }

    @Override
    public void toUserSendAddress() {
        AddressFragment addressFragment = new AddressFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("receiveOrSend", SEND);
        addressFragment.setArguments(bundle);

        transaction.replace(R.id.fragment_container_layout,addressFragment);
        transaction.addToBackStack("mefragment");
        transaction.commit();
    }

    //注销登录
    @Override
    public void loginOut() {
        ((MyApplication)getActivity().getApplication()).getUserInfo().setLoginState(false);
        LoginFragment loginFragment = new LoginFragment();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentManager.popBackStack();
        transaction.add(R.id.fragment_container_layout,loginFragment);
        transaction.commit();
    }

    @Override
    public void toUserInfoFragment(){

    }

    @Override
    public void toSendRecordFragment(){

    }

    @Override
    public void toAboutSoftFragment(){

    }

    @Override
    public void toMyComplaint(){

    }

}
