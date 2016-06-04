package com.expressba.expressuser.user.address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.expressba.expressuser.R;
import com.expressba.expressuser.express.ExpressEditFragment;
import com.expressba.expressuser.main.UIFragment;
import com.expressba.expressuser.model.FromAndTo;
import com.expressba.expressuser.model.UserAddress;
import com.expressba.expressuser.myelement.MyFragmentManager;

/**
 * Created by songchao on 16/5/30.
 */
public class AddressReceiveFragment extends AddressFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        ((TextView)view.findViewById(R.id.top_bar_center_text)).setText("收件地址");
        return view;
    }

    @Override
    protected void handlerIfBundle(Bundle bundle) {
        super.handlerIfBundle(bundle);
        this.bundle = bundle;
        fromAndTo = bundle.getParcelable("fromandto");
    }

    @Override
    public void handlerEveryInit() {
        init(RECEIVE);
    }

    @Override
    protected void jump(UserAddress userAddress) {
        super.jump(userAddress);
        if(fromAndTo!=null){
            toExpressEditFragment();
        }
    }

    protected void toExpressEditFragment(){
        Class toClass;
        FromAndTo fromAndTo = new FromAndTo(getClass().getName(), ExpressEditFragment.class.getName());
        try {
            if(UIFragment.class.isAssignableFrom((toClass = Class.forName(fromAndTo.getTo())))) {
                MyFragmentManager.turnFragment(getClass(),toClass, bundle, getFragmentManager(),false);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
