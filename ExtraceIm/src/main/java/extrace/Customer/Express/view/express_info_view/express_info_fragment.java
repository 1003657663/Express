package extrace.Customer.Express.view.express_info_view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import extrace.model.ExpressInfo;
import extrace.Customer.Express.presenter.express_info_presenter.express_info_presenter;
import extrace.Customer.Express.presenter.express_info_presenter.express_info_presenterImpl;
import extrace.Customer.Express.view.express_edit_view.express_target_Fragment;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/19.
 * 用户查询出的list，点击后显示的快件详细信息
 *
 */
public class express_info_fragment extends Fragment implements express_info_fragmentView
{
    private ImageButton back;
    private TextView title;
    private express_info_presenter express_info_presenter;
    private Button target;
    private TextView ID,sname,stel,sadd,saddinfo,rname,rtel,radd,raddinfo,weight;
    private TextView Acc1,Acc2,GetTime,OutTime,InsuFee,TranFee;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.express_item_info,container,false);
        back=(ImageButton)view.findViewById(R.id.top_bar_left_img);
        title=(TextView)view.findViewById(R.id.top_bar_center_text);
        ID=(TextView)view.findViewById(R.id.ID);
        sname=(TextView)view.findViewById(R.id.sname);
        stel=(TextView)view.findViewById(R.id.stel);
        sadd=(TextView)view.findViewById(R.id.sadd);
        saddinfo=(TextView)view.findViewById(R.id.saddinfo);
        rname=(TextView)view.findViewById(R.id.rname);
        rtel=(TextView)view.findViewById(R.id.rtel);
        radd=(TextView)view.findViewById(R.id.radd);
        raddinfo=(TextView)view.findViewById(R.id.raddinfo);
        weight=(TextView)view.findViewById(R.id.weight);
       InsuFee=(TextView)view.findViewById(R.id.InsuFee);
        TranFee=(TextView)view.findViewById(R.id.TranFee);
        GetTime=(TextView)view.findViewById(R.id.GetTime);
        OutTime=(TextView)view.findViewById(R.id.OutTime);
        Acc1=(TextView)view.findViewById(R.id.Acc1);
        Acc2=(TextView)view.findViewById(R.id.Acc2);
        target=(Button)view.findViewById(R.id.target);
        express_info_presenter=new express_info_presenterImpl(this);
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
        if(getArguments()!=null)
        {
            String id = getArguments().getString("ID");
            express_info_presenter.onfindInfoByID(id);
        }

        return view;
    }

    @Override
    public Activity getTheActivity() {
        return getActivity();
    }

    @Override
    public void onFail() {
        Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(final ExpressInfo expressInfo) {

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

        target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                express_target_Fragment fragment=new express_target_Fragment();
                Bundle bundle=new Bundle();
                bundle.putString("ID",expressInfo.getID());
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.express_item_info, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }
}
