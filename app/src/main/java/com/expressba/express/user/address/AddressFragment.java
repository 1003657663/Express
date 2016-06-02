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

import com.expressba.express.express.ExpressEditFragment;
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
public abstract class AddressFragment extends UIFragment implements AddressView, View.OnClickListener {
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
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.user_address_fragment, container, false);
        this.inflater = inflater;
        this.viewGroup = container;
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        view.findViewById(R.id.user_addres_add_new).setOnClickListener(this);
        listView = (ListView) view.findViewById(R.id.user_address_list);
        presenter = new AddressPresenterImpl(getActivity(), this);
        return view;
    }

    /**
     * 返回
     */
    protected void onBack(){
        MyFragmentManager.popFragment(getClass(),null,null,getFragmentManager());
    }

    protected void getAddress(){
        if (receiveOrSend == SEND) {
            presenter.getSendAddress();
        } else if (receiveOrSend == RECEIVE) {
            presenter.getReceiveAddress();
        }
    }

    /**
     * 在getMyBundle中执行初始化工作
     * @param receiveOrSend
     */
    protected void init(int receiveOrSend){
        this.receiveOrSend = receiveOrSend;
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

    /**
     * 跳转到地址编辑界面
     * @param userAddress
     * @param receiveOrSend
     */
    @Override
    public void toEditFragment(UserAddress userAddress, Integer receiveOrSend) {
        Bundle bundle = new Bundle();
        bundle.putInt("editwhat",receiveOrSend);
        bundle.putParcelable("useraddress",userAddress);
        MyFragmentManager.turnFragment(getClass(),AddressEditFragment.class,bundle,getFragmentManager());
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
        }
        if(userAddress!=null) {
            bundle.putInt("receiveOrSend",receiveOrSend);
            if(receiveOrSend == SEND) {
                bundle.putParcelable("sendaddress", userAddress);
            }else{
                bundle.putParcelable("receiveaddress", userAddress);
            }
        }
        if(fromAndTo==null){
            toEditFragment(userAddress,receiveOrSend==AddressFragment.SEND? AddressEditFragment.ADDRESS_UPDATE_SEND:AddressEditFragment.ADDRESS_UPDATE_RECEIVE);
        }
    }
}
