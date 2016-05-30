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
import com.expressba.express.model.FromAndTo;
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
    protected FromAndTo fromAndTo;
    protected Bundle bundle;

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
        init();
        return view;
    }

    /**
     * 留给子类实现
     */
    protected void init(){

    }

    /**
     * 监听返回键
     */
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

    /**
     * 返回
     */
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
    public void getMyBundle(){
        Bundle bundle = getArguments();
        if(bundle==null){
            bundle = getBundle();
        }
        if(bundle!=null){
            fromAndTo = bundle.getParcelable("fromandto");
            receiveOrSend = bundle.getInt("receiveOrSend");//获取上个页面传的发送或者接受地址的标志
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jump(addressList.get(position));
            }
        });
    }

    /**
     * 跳转到xx页面
     * @param userAddress
     */
    protected void jump(UserAddress userAddress){
        if(bundle==null){
            bundle = new Bundle();
        }else {
            bundle.clear();
        }
        if(userAddress!=null) {
            bundle.putParcelable("useraddress", userAddress);
        }
        if(fromAndTo==null){
            toEditFragment(userAddress,receiveOrSend==AddressFragment.SEND? AddressEditFragment.ADDRESS_UPDATE_SEND:AddressEditFragment.ADDRESS_UPDATE_RECEIVE);
        }
    }
}
