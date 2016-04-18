package extrace.Express.view.express_edit_view;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import extrace.Express.presenter.expressPresenter.expressPresenterImpl;
import extrace.model.ExpressSheet;
import extrace.net.IDataAdapter;
import extrace.ui.main.R;
import extrace.Express.presenter.expressPresenter.expressPresenter;

/**
 * Created by 黎明 on 2016/4/16.
 */
public class express_edit_Fragment extends Fragment implements View.OnClickListener,express_edit_FragmentView
{
    expressPresenter expressPresenter;
    private LinearLayout send_address,receive_address;
    private Button submit;
    public static int send_id=1, receive_id=2;
    private TextView sname, stel, saddress, saddressinfo, rname, rtel, raddress, raddressinfo;
private IDataAdapter<ExpressSheet> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.express_send_edit,container,false);
        expressPresenter =new expressPresenterImpl(this,adapter);
        send_address = (LinearLayout)view.findViewById(R.id.send_address);
        receive_address = (LinearLayout)view.findViewById(R.id.receive_address);
        submit = (Button) view.findViewById(R.id.submit);
        sname = (TextView) view.findViewById(R.id.sname);
        stel = (TextView) view.findViewById(R.id.stel);
        saddress = (TextView)view.findViewById(R.id.saddress);
        saddressinfo = (TextView)view. findViewById(R.id.saddressinfo);
        rname = (TextView)view. findViewById(R.id.rname);
        rtel = (TextView) view.findViewById(R.id.rtel);
        raddress = (TextView) view.findViewById(R.id.raddress);
        raddressinfo = (TextView) view.findViewById(R.id.raddressinfo);
        send_address.setOnClickListener(this);
        receive_address.setOnClickListener(this);
        submit.setOnClickListener(this);

        if(getArguments()!=null)
        {
            if(getArguments().getString("type").equals("send"))
            {
                sname.setText(getArguments().getString("name"));
                send_id=getArguments().getInt("ID");
                stel.setText(getArguments().getString("tel"));
                saddress.setText(getArguments().getString("address"));
                saddressinfo.setText(getArguments().getString("addressinfo"));
            }
            else if(getArguments().getString("type").equals("receive"))
            {
                rname.setText(getArguments().getString("name"));
                receive_id=getArguments().getInt("ID");
                rtel.setText(getArguments().getString("tel"));
                raddress.setText(getArguments().getString("address"));
                raddressinfo.setText(getArguments().getString("addressinfo"));
            }
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.send_address:
                //用户点击寄件人姓名，跳转至地址fragment 传入参数type=“send”
                //地址fragmentshow回express_edit_Fragment且返回参数type，ID,name,tel,address,addressinfo
                //因为不知道address fragment的名字 所以暂时注释掉了这一段代码
                /*
                Fragment fragment=new AddressFragment();
                FragmentTransaction transaction =getFragmentManager().beginTransaction();
                transaction.hide(express_edit_Fragment.this);
                transaction.add(R.id.fragment_container_layout,fragment);//id随便写了一个
                transaction.addToBackStack(null);
                transaction.commit();
                */
                break;

            case R.id.receive_address:
                /*
            Fragment fragment1 = new AddressFragment();
            FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
            transaction1.hide(express_edit_Fragment.this);
            transaction1.add(R.id.fragment_container_layout, fragment1);//id随便写了一个
            transaction1.addToBackStack(null);
            transaction1.commit();
            */
            break;
            case R.id.submit:
                  //判断ID是否为空 是否相同 toast出相应信息
                if(send_id==0||receive_id==0)
                    Toast.makeText(getActivity(), "寄件人与收件人都不能为空", Toast.LENGTH_SHORT).show();
                else if(send_id==receive_id)
                    Toast.makeText(getActivity(), "寄件人与收件人不能相同", Toast.LENGTH_SHORT).show();
              else
                    expressPresenter.doNewExpress(send_id, receive_id);
                break;
            default:
                break;
        }
    }
    @Override
    public Activity getTheActivity() {
        return getActivity();
    }

    @Override
    public void onToastFail() {
        Toast.makeText(getActivity(), "sorry,submit fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onToastSuccess() {
        Toast.makeText(getActivity(), "submit success", Toast.LENGTH_SHORT).show();
    }
}
