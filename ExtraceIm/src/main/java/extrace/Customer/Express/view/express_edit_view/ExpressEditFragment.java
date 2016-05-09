package extrace.Customer.Express.view.express_edit_view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import extrace.Customer.Express.presenter.express_edit_presenter.ExpressPresenter;
import extrace.Customer.Express.presenter.express_edit_presenter.ExpressPresenterImpl;
import extrace.main.MainFragment;
import extrace.main.MyApplication;
import extrace.model.UserAddress;
import extrace.ui.main.R;
import extrace.user.address.AddressFragment;

/**
 * Created by 黎明 on 2016/4/16.
 * 快件信息编辑：新建express
 * 向address页面传参 int receiveorSend（SEND:0，RECEIVE:1）
 * 接收 UserAddress &int receiveorsend
 * 确认寄件，向后台发送int UserID，int sendID，int receiveID
 * 接收 expressID
 */
public class ExpressEditFragment extends Fragment implements View.OnClickListener, ExpressEditFragmentView {
    private ExpressPresenter expressPresenter;
    private LinearLayout send_address, receive_address;
    private Button submit;
    public static int send_id = 0, receive_id = 0;
    private TextView title, sname, stel, saddress, saddressinfo, rname, rtel, raddress, raddressinfo;
    private ImageView back;
    public static final int SEND = 0;
    public static final int RECEIVE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.express_send_edit, container, false);
        title = (TextView) view.findViewById(R.id.top_bar_center_text);
        title.setText("快件信息");
        expressPresenter = new ExpressPresenterImpl(this);
        send_address = (LinearLayout) view.findViewById(R.id.send_address);
        receive_address = (LinearLayout) view.findViewById(R.id.receive_address);
        submit = (Button) view.findViewById(R.id.submit);
        sname = (TextView) view.findViewById(R.id.sname);
        stel = (TextView) view.findViewById(R.id.stel);
        back = (ImageView) view.findViewById(R.id.top_bar_left_img);
        saddress = (TextView) view.findViewById(R.id.sadd);
        saddressinfo = (TextView) view.findViewById(R.id.saddressinfo);
        rname = (TextView) view.findViewById(R.id.rname);
        rtel = (TextView) view.findViewById(R.id.rtel);
        raddress = (TextView) view.findViewById(R.id.radd);
        raddressinfo = (TextView) view.findViewById(R.id.raddressinfo);
        send_address.setOnClickListener(this);
        receive_address.setOnClickListener(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        if (getArguments() != null) {
            if (getArguments().getInt("receiveOrSend") == SEND) {
                setSendAddress();
            } else if (getArguments().getInt("receiveOrSend") == RECEIVE) {
                setReceiveAddress();
            }
        }
        return view;
    }

    public UserAddress userAddress;
    public UserAddress getUserAddress(){
        return userAddress;
    }
    public void setUserAddress(UserAddress userAddress){
        this.userAddress = userAddress;
    }
    private void setReceiveAddress() {
        UserAddress userAddress = this.userAddress;
                //(UserAddress) getArguments().getParcelable("expressaddress");
        rname.setText(userAddress.getName());
        receive_id = userAddress.getAid();
        rtel.setText(userAddress.getTelephone());
        raddress.setText(userAddress.getProvince() + userAddress.getCity() + userAddress.getRegion());
        raddressinfo.setText(userAddress.getAddress());
    }

    private void setSendAddress() {
        UserAddress userAddress = (UserAddress) getArguments().getParcelable("expressaddress");
        sname.setText(userAddress.getName());
        send_id = userAddress.getAid();
        stel.setText(userAddress.getTelephone());
        saddress.setText(userAddress.getProvince() + userAddress.getCity() + userAddress.getRegion());
        saddressinfo.setText(userAddress.getAddress());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_bar_left_img:
                //点击后退按钮
                onback();
                break;
            case R.id.send_address:
                //用户点击寄件人姓名，跳转至地址fragment
                toSendAddress();
                break;
            case R.id.receive_address:
                toReceiveAddress();
                break;
            case R.id.submit:
                //判断ID是否为空 是否相同 toast出相应信息
                checkSubmit();
                break;
        }
    }

    private void checkSubmit() {
        if (send_id == 0 || receive_id == 0) {
            Toast.makeText(getActivity(), "寄件人与收件人都不能为空", Toast.LENGTH_SHORT).show();
        } else if (send_id == receive_id) {
            Toast.makeText(getActivity(), "寄件人与收件人不能相同", Toast.LENGTH_SHORT).show();
        } else {
            int customerId = ((MyApplication) getActivity().getApplication()).getUserInfo().getId();
            expressPresenter.doNewExpress(customerId, send_id, receive_id);
        }
    }

    private void toReceiveAddress() {
        Fragment fragment1 = new AddressFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("receiveOrSend", RECEIVE);
        fragment1.setArguments(bundle1);
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.fragment_container_layout, fragment1,"needaddressfragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void toSendAddress() {
        Fragment fragment = new AddressFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("receiveOrSend", SEND);
        fragment.setArguments(bundle);
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.hide(ExpressEditFragment.this);
        transaction.add(R.id.fragment_container_layout, fragment,"needaddressfragment");
       // transaction.replace(R.id.fragment_container_layout, fragment,"needaddressfragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onback() {
        getFragmentManager().popBackStack();
    }

    @Override
    public Activity getTheActivity() {
        return getActivity();
    }

    @Override
    public void onToastSuccess(String ID) {
        Dialog dialog = new AlertDialog.Builder(getActivity()).setIcon(
                android.R.drawable.btn_star).setTitle("确认").setMessage(
                "请妥善保存您的单号：" + ID).setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainFragment fragment = new MainFragment();
                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.replace(R.id.fragment_container_layout, fragment);
                        transaction.addToBackStack("null");
                        transaction.commit();
                    }
                }).create();
        dialog.show();
    }

    @Override
    public void onToastFail(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }
}
