package com.expressba.express.user.address;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.expressba.express.main.UIFragment;
import com.expressba.express.model.UserAddress;
import com.expressba.express.R;
import com.expressba.express.myelement.MyFragmentManager;
import com.expressba.express.user.address.addressEdit.AddressEditFragment;

/**
 * 用户地址界面
 * Created by chao on 2016/4/17.
 */
public class AddressFragment extends UIFragment implements AddressView, View.OnClickListener {
    public static final int SEND = 0;
    public static final int RECEIVE = 1;

    AddressPresenter presenter;
    LayoutInflater inflater;
    ViewGroup viewGroup;
    ListView listView;
    Integer receiveOrSend;
    Boolean isMe = false;
    String from;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_address_fragment, container, false);
        view.setOnKeyListener(onKeyListener);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        this.inflater = inflater;
        this.viewGroup = container;
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        view.findViewById(R.id.user_addres_add_new).setOnClickListener(this);
        listView = (ListView) view.findViewById(R.id.user_address_list);
        getMyBundle();
        if (receiveOrSend == SEND) {
            ((TextView) view.findViewById(R.id.top_bar_center_text)).setText("管理发货地址");
        } else if (receiveOrSend == RECEIVE) {
            ((TextView) view.findViewById(R.id.top_bar_center_text)).setText("管理收货地址");
        }
        return view;
    }

    private View.OnKeyListener onKeyListener = new View.OnKeyListener(){

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(event.getAction() == KeyEvent.ACTION_DOWN){
                if(keyCode == KeyEvent.KEYCODE_BACK){
                    onBack();
                    return true;
                }
            }
            return false;
        }
    };

    private void onBack(){
        MyFragmentManager.popFragment(getClass(),null,null,getFragmentManager());
    }

    private void getAddress(){
        if (receiveOrSend == SEND) {
            presenter.getSendAddress();
        } else if (receiveOrSend == RECEIVE) {
            presenter.getReceiveAddress();
        }
    }

    @Override
    public void setBundle(Bundle bundle) {
        super.setBundle(bundle);
        getMyBundle();
    }

    /**
     * 获取参数
     */
    private void getMyBundle(){
        if(getArguments()!=null){
            Bundle bundle = getArguments();
            receiveOrSend = bundle.getInt("receiveOrSend");//获取上个页面传的发送或者接受地址的标志
            isMe = bundle.getBoolean("isme", false);
            //如果不是从me界面传过来需要获取from参数
            if(!isMe) {
                from = bundle.getString("wherefrom");
            }
        }
        presenter = new AddressPresenterImpl(getActivity(), this);
        getAddress();//每次跳转到这个页面都会获取一次地址信息
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_bar_left_img:
                onBack();
                break;
            case R.id.user_addres_add_new:
                if (receiveOrSend == SEND) {
                    toEditFragment(null, AddressEditFragment.ADDRESS_NEW_SEND);
                } else if (receiveOrSend == RECEIVE) {
                    toEditFragment(null, AddressEditFragment.ADDRESS_NEW_RECEIVE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void toEditFragment(UserAddress userAddress, Integer receiveOrSend) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("userAddress", userAddress);
        bundle.putInt("editWhat", receiveOrSend);

        MyFragmentManager.turnFragment(AddressFragment.class,AddressEditFragment.class,bundle,getFragmentManager());
    }

    @Override
    public void refreshAddress(final ArrayList<UserAddress> addressList) {
        //地址获取成功后，加载到list通过adapter
        AddressLIstApapter listApapter = new AddressLIstApapter(this, addressList, receiveOrSend);
        listView.setAdapter(listApapter);
        if (!isMe) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
                    toExpress(addressList.get(position));
                }
            });
        }
    }

    /**
     * 返回去寄快递界面并且传参
     */
    private void toExpress(UserAddress userAddress) {
        Bundle bundle = new Bundle();
        if (receiveOrSend == RECEIVE) {
            bundle.putInt("receiveOrSend", RECEIVE);
            bundle.putParcelable("expressaddress", userAddress);
        } else if (receiveOrSend == SEND) {
            bundle.putInt("receiveOrSend", SEND);
            bundle.putParcelable("expressaddress", userAddress);
        }

        try {//这里需要判断一下
            MyFragmentManager.popFragment(AddressFragment.class,(Class<? extends UIFragment>) Class.forName(from),bundle,getFragmentManager());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
