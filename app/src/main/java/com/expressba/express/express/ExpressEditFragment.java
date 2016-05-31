package com.expressba.express.express;

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

import com.expressba.express.main.MainFragment;
import com.expressba.express.main.MyApplication;
import com.expressba.express.main.UIFragment;
import com.expressba.express.model.FromAndTo;
import com.expressba.express.model.UserAddress;
import com.expressba.express.R;
import com.expressba.express.myelement.MyFragmentManager;
import com.expressba.express.user.address.AddressFragment;
import com.expressba.express.user.address.AddressReceiveFragment;
import com.expressba.express.user.address.AddressSendFragment;

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
    private LinearLayout sendAddress, receiveAddress;
    private Button submit;
    public static int SEND_ID = 0, RECEIVE_ID = 0;
    private TextView title, sname, stel, saddress, saddressinfo, rname, rtel, raddress, raddressinfo;
    private ImageView back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.express_send_edit, container, false);
        title = (TextView) view.findViewById(R.id.top_bar_center_text);
        title.setText("快件信息");
        expressPresenter = new ExpressPresenterImpl(this);
        sendAddress = (LinearLayout) view.findViewById(R.id.send_address);
        receiveAddress = (LinearLayout) view.findViewById(R.id.receive_address);
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
        sendAddress.setOnClickListener(this);
        receiveAddress.setOnClickListener(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        getMyBundle();
        return view;
    }

    @Override
    public void setBundle(Bundle bundle) {
        super.setBundle(bundle);
        getMyBundle();
    }

    private void getMyBundle(){
        Bundle bundle = getBundle();
        if(bundle==null){
            bundle = getArguments();
        }
        if(bundle!=null){
            UserAddress sendAddress = bundle.getParcelable("sendaddress");
            UserAddress receiveAddress = bundle.getParcelable("receiveaddress");
            if(sendAddress!=null){
                setAddress(sendAddress,AddressFragment.SEND);
            }
            if(receiveAddress!=null){
                setAddress(receiveAddress,AddressFragment.RECEIVE);
            }
        }
    }

    private void setAddress(UserAddress userAddress, int receiveOrSend) {
        if(userAddress!=null) {
            if (receiveOrSend == AddressFragment.SEND) {
                sname.setText(userAddress.getName());
                SEND_ID = userAddress.getAid();
                stel.setText(userAddress.getTelephone());
                saddress.setText(userAddress.getProvince() + userAddress.getCity() + userAddress.getRegion());
                saddressinfo.setText(userAddress.getAddress());
            } else {
                rname.setText(userAddress.getName());
                RECEIVE_ID = userAddress.getAid();
                rtel.setText(userAddress.getTelephone());
                raddress.setText(userAddress.getProvince() + userAddress.getCity() + userAddress.getRegion());
                raddressinfo.setText(userAddress.getAddress());
            }
        }
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
        if (SEND_ID == 0 || RECEIVE_ID == 0) {
            Toast.makeText(getActivity(), "寄件人与收件人都不能为空", Toast.LENGTH_SHORT).show();
        } else if (SEND_ID == RECEIVE_ID) {
            Toast.makeText(getActivity(), "寄件人与收件人不能相同", Toast.LENGTH_SHORT).show();
        } else {
            int customerId = ((MyApplication) getActivity().getApplication()).getUserInfo().getId();
            expressPresenter.doNewExpress(customerId, SEND_ID, RECEIVE_ID);
        }
    }

    private void toReceiveAddress() {
        Bundle bundle1 = new Bundle();
        FromAndTo fromAndTo = new FromAndTo(getClass().getName(),ExpressEditFragment.class.getName());
        bundle1.putParcelable("fromandto",fromAndTo);
        MyFragmentManager.turnFragment(ExpressEditFragment.class, AddressReceiveFragment.class, bundle1, getFragmentManager());
    }

    private void toSendAddress() {
        Bundle bundle = new Bundle();
        FromAndTo fromAndTo = new FromAndTo(getClass().getName(),ExpressEditFragment.class.getName());
        bundle.putParcelable("fromandto",fromAndTo);
        MyFragmentManager.turnFragment(ExpressEditFragment.class, AddressSendFragment.class, bundle, getFragmentManager());
    }

    public void onback() {
        MyFragmentManager.popFragment(ExpressEditFragment.class, MainFragment.class,null,getFragmentManager(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
                    }
                }).create();
        dialog.show();
    }

    @Override
    public void onToastFail(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }
}
