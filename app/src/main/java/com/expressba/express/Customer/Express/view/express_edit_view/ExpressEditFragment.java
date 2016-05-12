package com.expressba.express.Customer.Express.view.express_edit_view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.expressba.express.Customer.Express.presenter.express_edit_presenter.ExpressPresenter;
import com.expressba.express.Customer.Express.presenter.express_edit_presenter.ExpressPresenterImpl;
import com.expressba.express.main.MyApplication;
import com.expressba.express.main.UIFragment;
import com.expressba.express.model.UserAddress;
import com.expressba.express.R;
import com.expressba.express.myelement.MyFragmentManager;
import com.expressba.express.user.address.AddressFragment;

/**
 * Created by 黎明 on 2016/4/16.
 * 快件信息编辑：新建express
 * 向address页面传参 int receiveorSend（SEND:0，RECEIVE:1）
 * 接收 UserAddress &int receiveorsend
 * 确认寄件，向后台发送int UserID，int sendID，int receiveID
 * 接收 expressID
 */
public class ExpressEditFragment extends UIFragment implements View.OnClickListener, ExpressEditFragmentView {
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
        return view;
    }

    @Override
    public void setBundle(Bundle bundle) {
        super.setBundle(bundle);
        if (getBundle().getInt("receiveOrSend") == SEND) {
            setSendAddress();
        } else if (getBundle().getInt("receiveOrSend") == RECEIVE) {
            setReceiveAddress();
        }
    }

    private void setReceiveAddress() {
        UserAddress userAddress = getBundle().getParcelable("expressaddress");
        rname.setText(userAddress.getName());
        receive_id = userAddress.getAid();
        rtel.setText(userAddress.getTelephone());
        raddress.setText(userAddress.getProvince() + userAddress.getCity() + userAddress.getRegion());
        raddressinfo.setText(userAddress.getAddress());
    }

    private void setSendAddress() {
        UserAddress userAddress = getBundle().getParcelable("expressaddress");
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
                getFragmentManager().popBackStack();
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

        Bundle bundle1 = new Bundle();
        bundle1.putInt("receiveOrSend", RECEIVE);
        bundle1.putString("wherefrom", getClass().getName());
        MyFragmentManager.turnFragment(ExpressEditFragment.class, AddressFragment.class, bundle1, getFragmentManager());
    }

    private void toSendAddress() {
        Bundle bundle = new Bundle();
        bundle.putInt("receiveOrSend", SEND);
        bundle.putString("wherefrom", getClass().getName());

        MyFragmentManager.turnFragment(ExpressEditFragment.class, AddressFragment.class, bundle, getFragmentManager());
    }

    public void onback() {
        //getFragmentManager().popBackStack();
        //弹出包括自身和在自己上面的所有栈
        MyFragmentManager.popFragment(ExpressEditFragment.class,null,null,getFragmentManager());
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
                        onback();
                        getFragmentManager().popBackStack();
                    }
                }).create();
        dialog.show();
    }

    @Override
    public void onToastFail(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }
}
