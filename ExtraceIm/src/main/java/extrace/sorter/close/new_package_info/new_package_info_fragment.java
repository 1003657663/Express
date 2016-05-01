package extrace.sorter.close.new_package_info;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import extrace.main.MyApplication;
import extrace.model.packageInfo;
import extrace.sorter.close.add_package_list.add_package_listFragment;
import extrace.ui.main.R;
import extrace.user.address.AddressFragment;

/**
 * Created by 黎明 on 2016/4/28.
 */
public class new_package_info_fragment extends Fragment implements new_package_info_fragmentView
{
    private ImageButton back;
    private ImageView toaddress;
    private TextView title,from,to,ID,employeesId,time,employeesname;
    private Button open;
    private new_package_info_presenter new_package_info_presenter;
    private static int toID,fromID,employeesID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.packageinfo,container,false);
        back=(ImageButton)view.findViewById(R.id.top_bar_left_img);
        title=(TextView)view.findViewById(R.id.top_bar_center_text);
        from=(TextView)view.findViewById(R.id.from);
        toaddress=(ImageView)view.findViewById(R.id.toaddress);
        to=(TextView)view.findViewById(R.id.to);
        open=(Button)view.findViewById(R.id.open);
        open.setText("创建");
        employeesId=(TextView)view.findViewById(R.id.EmployeesID);
        employeesname=(TextView)view.findViewById(R.id.EmployeesName);
        time=(TextView)view.findViewById(R.id.time);
        ID=(TextView)view.findViewById(R.id.closetime);
        new_package_info_presenter=new new_package_info_presenterImpl(getActivity(),this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        title.setText("请选取目的地地址");
        employeesID=((MyApplication)getActivity().getApplication()).getEmployeesInfo().getId();
        employeesId.setText(((MyApplication)getActivity().getApplication()).getEmployeesInfo().getId());
        fromID=((MyApplication)getActivity().getApplication()).getEmployeesInfo().getOutletsId();
        from.setText(fromID);
        employeesname.setText(((MyApplication)getActivity().getApplication()).getEmployeesInfo().getName());
        //从myapplication拿取employees登录信息 ，得到 employeesID employeesname并填入
        //
        toaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressFragment fragment=new AddressFragment();
                //去address选取一个目的地地址并返回ID和address
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(new_package_info_fragment.this);
                transaction.add(R.id.fragment_container_layout,fragment);
                transaction.addToBackStack("new_package_info_fragment");
                transaction.commit();
            }
        });
        if(getArguments()!=null)
        {
           toID=getArguments().getInt("ID");
            to.setText(toID);
        }
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (to.getText()!=null)
                {
                    new_package_info_presenter.newPackage(fromID,toID,employeesID);
                }
                else
                    Toast.makeText(getActivity(),"请选择目的地地址",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
    @Override
    public void onFail() {
        Toast.makeText(getActivity(),"创建失败",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(packageInfo packageInfo) {
        ID.setText(packageInfo.getID());
        time.setText(packageInfo.getClosetime());
        final String packageID=packageInfo.getID();
      //  Toast.makeText(getActivity(),"创建成功",Toast.LENGTH_LONG).show();
        Dialog dialog1 = new AlertDialog.Builder(getActivity()).setIcon(
                android.R.drawable.btn_star).setTitle("确认").setMessage(
                "创建成功").setPositiveButton("确认",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                add_package_listFragment fragment=new add_package_listFragment();
                Bundle bundle=new Bundle();
                bundle.putString("packageID",packageID);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_layout, fragment);
                transaction.addToBackStack("new_package_info_fragment");
                transaction.commit();
            }
        }).create();
        dialog1.show();
    }
}
