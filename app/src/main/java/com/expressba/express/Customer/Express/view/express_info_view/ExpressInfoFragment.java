package com.expressba.express.Customer.Express.view.express_info_view;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.expressba.express.model.ExpressInfo;
import com.expressba.express.Customer.Express.presenter.express_info_presenter.ExpressInfoPresenter;
import com.expressba.express.Customer.Express.presenter.express_info_presenter.ExpressInfoPresenterImpl;
import com.expressba.express.R;

/**
 * Created by 黎明 on 2016/4/19.
 * 用户查询出的list，点击后显示的快件详细信息
 */
public class ExpressInfoFragment extends Fragment implements ExpressInfoFragmentView {
    private ImageButton back;
    private TextView title;
    private ExpressInfoPresenter ExpressInfoPresenter;
    private Button target;
    private TextView ID, sname, stel, sadd, saddinfo, rname, rtel, radd, raddinfo, weight;
    private TextView Acc1, Acc2, GetTime, OutTime, InsuFee, TranFee;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.express_item_info, container, false);
        back = (ImageButton) view.findViewById(R.id.top_bar_left_img);
        title = (TextView) view.findViewById(R.id.top_bar_center_text);
        ID = (TextView) view.findViewById(R.id.ID);
        sname = (TextView) view.findViewById(R.id.sname);
        stel = (TextView) view.findViewById(R.id.stel);
        sadd = (TextView) view.findViewById(R.id.sadd);
        saddinfo = (TextView) view.findViewById(R.id.saddinfo);
        rname = (TextView) view.findViewById(R.id.rname);
        rtel = (TextView) view.findViewById(R.id.rtel);
        radd = (TextView) view.findViewById(R.id.radd);
        raddinfo = (TextView) view.findViewById(R.id.raddinfo);
        weight = (TextView) view.findViewById(R.id.weight);
        InsuFee = (TextView) view.findViewById(R.id.InsuFee);
        TranFee = (TextView) view.findViewById(R.id.TranFee);
        GetTime = (TextView) view.findViewById(R.id.GetTime);
        OutTime = (TextView) view.findViewById(R.id.OutTime);
        Acc1 = (TextView) view.findViewById(R.id.Acc1);
        Acc2 = (TextView) view.findViewById(R.id.Acc2);
        target = (Button) view.findViewById(R.id.target);
        ExpressInfoPresenter = new ExpressInfoPresenterImpl(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        title.setText("订单详情");

        Acc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Acc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (getArguments() != null) {
            ExpressInfo expressInfo = (ExpressInfo) getArguments().getParcelable("1");
            ID.setText(expressInfo.getID());
            sname.setText(expressInfo.getSname());
            stel.setText(expressInfo.getStel());
            sadd.setText(expressInfo.getSadd());
            saddinfo.setText(expressInfo.getSaddinfo());
            rname.setText(expressInfo.getRname());
            rtel.setText(expressInfo.getRtel());
            radd.setText(expressInfo.getRadd());
            raddinfo.setText(expressInfo.getRaddinfo());
            weight.setText(String.valueOf(expressInfo.getWeight()));
            GetTime.setText(expressInfo.getGetTime());
            OutTime.setText(expressInfo.getOutTime());
            TranFee.setText(String.valueOf(expressInfo.getTranFee()));
            InsuFee.setText(String.valueOf(expressInfo.getInsuFee()));
        }

        return view;
    }

    @Override
    public Activity getTheActivity() {
        return getActivity();
    }

    @Override
    public void onFail() {
        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(ExpressInfo expressInfo) {
        /*target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpressTargetFragment fragment=new ExpressTargetFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ID",expressInfo.getID());
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.express_item_info, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });*/

    }
}
