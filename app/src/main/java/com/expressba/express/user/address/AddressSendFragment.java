package com.expressba.express.user.address;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.expressba.express.R;
import com.expressba.express.express.ExpressEditFragment;
import com.expressba.express.main.UIFragment;
import com.expressba.express.model.FromAndTo;
import com.expressba.express.model.UserAddress;
import com.expressba.express.myelement.MyFragmentManager;

/**
 * Created by songchao on 16/5/30.
 */
public class AddressSendFragment extends AddressFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        ((TextView)view.findViewById(R.id.top_bar_center_text)).setText("发件地址");
        return view;
    }

    @Override
    protected void handlerIfBundle(Bundle bundle) {
        super.handlerIfBundle(bundle);
        this.bundle = bundle;
        fromAndTo = bundle.getParcelable("fromandto");
        if(fromAndTo!=null && fromAndTo.getTo().equals(AddressReceiveFragment.class.getName())){
            Toast.makeText(getActivity(), "请选择发货地址", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void handlerEveryInit() {
        init(SEND);
    }



    @Override
    protected void jump(UserAddress userAddress) {
        super.jump(userAddress);
        if(fromAndTo!=null){
            toAddressReceiveFragment(bundle,userAddress);
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
