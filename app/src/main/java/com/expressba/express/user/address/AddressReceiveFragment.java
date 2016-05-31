package com.expressba.express.user.address;
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
    public void getMyBundle() {
        bundle = getBundle();
        if(bundle==null){
            bundle = getArguments();
        }
        if(bundle!=null){
            fromAndTo = bundle.getParcelable("fromandto");
        }
        receiveOrSend = RECEIVE;
        presenter = new AddressPresenterImpl(getActivity(), this);
        getAddress();//每次跳转到这个页面都会获取一次地址信息
    }

    @Override
    protected void jump(UserAddress userAddress) {
        super.jump(userAddress);
        if(fromAndTo!=null){
            toExpressEditFragment();
        }
    }

    /**
     * 跳转到快递发送界面
     */
    public void toExpressEditFragment(){
        //判断页面不同页面，不同参数
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
