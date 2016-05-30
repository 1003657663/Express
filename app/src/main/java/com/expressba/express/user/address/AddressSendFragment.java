package com.expressba.express.user.address;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.expressba.express.R;
import com.expressba.express.express.ExpressEditFragment;
import com.expressba.express.main.UIFragment;
import com.expressba.express.model.FromAndTo;
import com.expressba.express.model.UserAddress;
import com.expressba.express.myelement.MyFragmentManager;
import com.expressba.express.user.address.addressEdit.AddressEditFragment;

/**
 * Created by songchao on 16/5/30.
 */
public class AddressSendFragment extends AddressFragment {
    @Override
    protected void init() {
        super.init();
        ((TextView)viewGroup.findViewById(R.id.top_bar_center_text)).setText("管理发货地址");
    }

    @Override
    protected void jump(UserAddress userAddress) {
        super.jump(userAddress);
        if(fromAndTo!=null){
            toAddressReceiveFragment(bundle,userAddress);
        }else{
            toEditFragment(userAddress,receiveOrSend==AddressFragment.SEND? AddressEditFragment.ADDRESS_UPDATE_SEND:AddressEditFragment.ADDRESS_UPDATE_RECEIVE);
        }
    }

    /**
     * 跳转到收货地址界面
     * @param bundle
     */
    private void toAddressReceiveFragment(Bundle bundle,UserAddress userAddress){
        //判断页面不同页面，不同参数
        Class toClass;
        FromAndTo fromAndTo2 = new FromAndTo(getClass().getName(),ExpressEditFragment.class.getName());
        bundle.putInt("receiveOrSend",SEND);
        bundle.putParcelable("sendaddress",userAddress);
        bundle.putParcelable("fromandto",fromAndTo2);
        try {
            if(UIFragment.class.isAssignableFrom((toClass = Class.forName(fromAndTo.getTo())))) {
                Toast.makeText(getActivity(),"请选择收件地址",Toast.LENGTH_SHORT).show();
                MyFragmentManager.turnFragment(getClass(),toClass, bundle, getFragmentManager(),false);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
