package com.expressba.express.sorter.Expressupdate;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.expressba.express.main.UIFragment;
import com.expressba.express.model.ExpressInfo;
import com.expressba.express.R;


/**
 * Created by 黎明 on 2016/4/30.
 * 更新express信息 主要用于快递员上门取件 更新订单
 * 暂不考虑用户未下订单的情况
 * 一打开就是用户填好的订单 只需确认并更新
 */
public class ExpressUpdateFragment extends UIFragment implements ExpressUpdateFragmentView, View.OnClickListener {
    private ExpressUpdatePresenter presenter;
    private LinearLayout send_address, receive_address;
    private Button submit;
    private TextView title, sname, stel, saddress, saddressinfo, rname, rtel, raddress, raddressinfo;
    private ImageView back;
    public static String ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new ExpressUpdatePresenterImpl(getActivity(), this);
        if (getArguments().getString("ID") != null) {
            ID = getArguments().getString("ID");
            presenter.getExpressInfoByID(ID);
        } else getFragmentManager().popBackStack();
        View view = inflater.inflate(R.layout.express_send_edit, container, false);
        title = (TextView) view.findViewById(R.id.top_bar_center_text);
        title.setText("快件信息");
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_bar_left_img:
                //点击后退按钮
                onback();
                break;
            case R.id.submit:
                //确认用户信息之后跳转到下一界面 更新价格之类的信息
                DeliverUpdateExpressFragment fragment = new DeliverUpdateExpressFragment();
                Bundle bundle = new Bundle();
                bundle.putString("ID", ID);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.fragment_container_layout, fragment);
                transaction.addToBackStack("ExpressUpdateFragment");
                transaction.commit();
                break;
            default:
                break;
        }
    }

    public void onback() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onFail(String errorMessage) {
        getFragmentManager().popBackStack();
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(ExpressInfo expressInfo) {
        sname.setText(expressInfo.getSname());
        rname.setText(expressInfo.getRname());
        saddress.setText(expressInfo.getSadd());
        raddress.setText(expressInfo.getRadd());
        saddressinfo.setText(expressInfo.getSaddinfo());
        raddressinfo.setText(expressInfo.getRaddinfo());
        stel.setText(expressInfo.getStel());
        rtel.setText(expressInfo.getRtel());
    }
}
