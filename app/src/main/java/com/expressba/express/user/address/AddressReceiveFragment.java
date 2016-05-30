package com.expressba.express.user.address;

import android.os.Bundle;
import android.widget.TextView;

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
public class AddressReceiveFragment extends AddressFragment {
    @Override
    protected void init() {
        super.init();
        ((TextView)viewGroup.findViewById(R.id.top_bar_center_text)).setText("管理收货地址");
    }

    @Override
    protected void jump(UserAddress userAddress) {
        super.jump(userAddress);
        if(fromAndTo!=null){
            toExpressEditFragment(bundle,userAddress);
        }else{
            toEditFragment(userAddress,receiveOrSend==AddressFragment.SEND? AddressEditFragment.ADDRESS_UPDATE_SEND:AddressEditFragment.ADDRESS_UPDATE_RECEIVE);
        }
    }

    /**
     * 跳转到快递发送界面
     * @param bundle
     */
    private void toExpressEditFragment(Bundle bundle,UserAddress userAddress){
        //判断页面不同页面，不同参数
        Class toClass;
        FromAndTo fromAndTo = new FromAndTo(getClass().getName(), ExpressEditFragment.class.getName());
        bundle.putInt("receiveOrSend",RECEIVE);
        bundle.putParcelable("receiveaddress",userAddress);
        try {
            if(UIFragment.class.isAssignableFrom((toClass = Class.forName(fromAndTo.getTo())))) {
                MyFragmentManager.turnFragment(getClass(),toClass, bundle, getFragmentManager(),false);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
