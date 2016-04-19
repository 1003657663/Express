package extrace.Express.view.express_info_view;

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
import extrace.Express.presenter.express_info_presenter.express_info_presenter;
import extrace.Express.presenter.express_info_presenter.express_info_presenterImpl;
import extrace.Express.view.express_edit_view.express_target_Fragment;
import extrace.model.ExpressSheet;
import extrace.net.IDataAdapter;
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
    private IDataAdapter<ExpressSheet> adapter;
    private ExpressSheet expressSheet;
    private Button target;
    private TextView ID,sname,stel,sadd,saddinfo,rname,rtel,radd,raddinfo,weight,type,status,time;
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
        type=(TextView)view.findViewById(R.id.type);
        status=(TextView)view.findViewById(R.id.status);
        time=(TextView)view.findViewById(R.id.time);
        target=(Button)view.findViewById(R.id.target);
        express_info_presenter=new express_info_presenterImpl(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        title.setText("订单详情");

        if(getArguments()!=null)
        {
            String id = getArguments().getString("ID");
            express_info_presenter.onfindInfoByID(id);
            expressSheet = adapter.getData();
            ID.setText(expressSheet.getID());
            //sname.setText(expressSheet.get);
            //全都setText
            target.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    express_target_Fragment fragment=new express_target_Fragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("ID",expressSheet.getID().toString());
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.express_item_info, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
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
}
