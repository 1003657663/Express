package extrace.user.me;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import extrace.main.MyApplication;
import extrace.ui.main.R;
import extrace.user.address.AddressFragment;

/**
 * Created by chao on 2016/4/17.
 */
public class MeFragment extends Fragment implements MeView,View.OnClickListener{
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment,container,false);
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
        }
    }

    @Override
    public void toUserReceiveAddress(){
        transaction.replace(R.id.fragment_container_layout,new AddressFragment());
        transaction.addToBackStack("mefragment");
        transaction.commit();
    }

    @Override
    public void loginOut() {
        ((MyApplication)getActivity().getApplication()).setUserInfo(null);
        SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sf.edit();
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
