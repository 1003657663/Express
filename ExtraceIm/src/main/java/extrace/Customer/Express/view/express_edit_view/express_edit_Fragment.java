package extrace.Customer.Express.view.express_edit_view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import extrace.Customer.Express.presenter.express_edit_presenter.expressPresenterImpl;
import extrace.main.MyApplication;
import extrace.model.ExpressSheet;
import extrace.net.IDataAdapter;
import extrace.ui.main.R;
import extrace.Customer.Express.presenter.express_edit_presenter.expressPresenter;
import extrace.user.address.AddressFragment;
/**
 * Created by 黎明 on 2016/4/16.
 * 快件信息编辑：填入发件人收件人的地址信息
 */
public class express_edit_Fragment extends Fragment implements View.OnClickListener, express_edit_FragmentView {
    private expressPresenter expressPresenter;
    private LinearLayout send_address, receive_address;
    private Button submit;
    public static int send_id = 1, receive_id = 2;
    private TextView title, sname, stel, saddress, saddressinfo, rname, rtel, raddress, raddressinfo;
    private IDataAdapter<ExpressSheet> adapter;
    private ImageView back;
    private CheckBox check;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.express_send_edit, container, false);
        title = (TextView) view.findViewById(R.id.top_bar_center_text);
        title.setText("快件信息");
        expressPresenter = new expressPresenterImpl(this);
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
        check=(CheckBox)view.findViewById(R.id.check);
        send_address.setOnClickListener(this);
        receive_address.setOnClickListener(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        if (getArguments() != null) {
            if (getArguments().getString("type").equals("send")) {
                sname.setText(getArguments().getString("name"));
                send_id = getArguments().getInt("ID");
                stel.setText(getArguments().getString("tel"));
                saddress.setText(getArguments().getString("address"));
                saddressinfo.setText(getArguments().getString("addressinfo"));
            } else if (getArguments().getString("type").equals("receive")) {
                receive_id = getArguments().getInt("ID");
                sname.setText(getArguments().getString("name"));
                stel.setText(getArguments().getString("tel"));
                saddress.setText(getArguments().getString("address"));
                saddressinfo.setText(getArguments().getString("addressinfo"));
            }
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_bar_left_img:
                //点击后退按钮
                onback();
                break;
            case R.id.send_address:
                //用户点击寄件人姓名，跳转至地址fragment 传入参数type=“send”
                Fragment fragment = new AddressFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("type", "send");
                fragment.setArguments(bundle);
                transaction.hide(express_edit_Fragment.this);
                transaction.add(R.id.fragment_container_layout, fragment);//id随便写了一个
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            case R.id.receive_address:

                Fragment fragment1 = new AddressFragment();
                FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                Bundle bundle1 = new Bundle();
                bundle1.putString("type", "receive");
                fragment1.setArguments(bundle1);
                transaction1.hide(express_edit_Fragment.this);
                transaction1.add(R.id.fragment_container_layout, fragment1);//id随便写了一个
                transaction1.addToBackStack(null);
                transaction1.commit();
                break;
            case R.id.submit:
                //判断ID是否为空 是否相同 toast出相应信息
                if (send_id ==0 || receive_id ==0)
                {Toast.makeText(getActivity(), "寄件人与收件人都不能为空", Toast.LENGTH_SHORT).show();}
                else if (send_id == receive_id)
                { Toast.makeText(getActivity(), "寄件人与收件人不能相同", Toast.LENGTH_SHORT).show();}
                else if(!check.isChecked())
                {
                    Toast.makeText(getActivity(), "您未同意本公司协议", Toast.LENGTH_SHORT).show();
                }
                else {
                    int customerId=((MyApplication)getActivity().getApplication()).getUserInfo().getUID();
                    expressPresenter.doNewExpress(customerId,send_id, receive_id);
                }
                break;
            default:
                break;
        }
    }

    public void onback() {
        getFragmentManager().popBackStack();
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
    public void onToastSuccess(String ID) {
        Toast.makeText(getActivity(), "submit success,Express ID is:"+ID, Toast.LENGTH_SHORT).show();
    }
}
