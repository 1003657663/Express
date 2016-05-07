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
import extrace.model.EmployeesEntity;
import extrace.model.PackageInfo;
import extrace.model.UserAddress;
import extrace.sorter.SorterIndex.SorterIndexFragment;
import extrace.sorter.close.add_package_list.AddPackageListFragment;
import extrace.ui.main.R;
import extrace.user.address.AddressFragment;

/**
 * Created by 黎明 on 2016/4/28.
 * 快递员创建包裹：确定即创建成功
 * 分拣员创建包裹：输入目的地址 创建成功
 * 显示创建时间和包裹ID
 * 选择是否添加 是：进入Add_package_listFragment
 * 否：结束
 */
public class NewPackageInfoFragment extends Fragment implements NewPackageInfoFragmentView {
    private ImageButton back;
    private ImageView toaddress;
    private TextView title, from, to, ID, employeesId, time, employeesname;
    private Button open;
    private NewPackageInfoPresenter presenter1;
    private static int fromID, toID, EmployeesID;
    private static String packageID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.packageinfo, container, false);
        back = (ImageButton) view.findViewById(R.id.top_bar_left_img);
        title = (TextView) view.findViewById(R.id.top_bar_center_text);
        from = (TextView) view.findViewById(R.id.package_from);
        toaddress = (ImageView) view.findViewById(R.id.toaddress);
        to = (TextView) view.findViewById(R.id.package_to);
        open = (Button) view.findViewById(R.id.open);
        open.setText("创建");
        employeesId = (TextView) view.findViewById(R.id.EmployeesID);
        employeesname = (TextView) view.findViewById(R.id.EmployeesName);
        time = (TextView) view.findViewById(R.id.closetime);
        ID = (TextView) view.findViewById(R.id.ID);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        title.setText("请选取目的地地址");
        EmployeesEntity employeesEntity = ((MyApplication) getActivity().getApplication()).getEmployeesInfo();
        fromID = employeesEntity.getOutletsId();
        from.setText("当前站点");
        employeesname.setText(employeesEntity.getName());
        employeesId.setText(String.valueOf(employeesEntity.getId()));
        EmployeesID = employeesEntity.getId();

        toaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressFragment fragment = new AddressFragment();
                //去address选取一个目的地地址并返回ID和address
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("receiveOrSend", 1);
                fragment.setArguments(bundle);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.hide(NewPackageInfoFragment.this);
                transaction.add(R.id.fragment_container_layout, fragment);
                transaction.addToBackStack("NewPackageInfoFragment");
                transaction.commit();
            }
        });

        if (getArguments() != null) {
            UserAddress userAddress = (UserAddress) getArguments().getParcelable("expressaddress");
            toID = userAddress.getAid();
            to.setText("" + toID);
        }
        presenter1 = new NewPackageInfoPresenterImpl(getActivity(), this);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (to.getText() != null)
                    //创建包裹
                    presenter1.newPackage(fromID, toID, EmployeesID, 1);
            }
        });

        return view;
    }

    @Override
    public void onFail() {
        Toast.makeText(getActivity(), "创建失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(PackageInfo packageInfo) {
        ID.setText(packageInfo.getId());
        time.setText(packageInfo.getCloseTime());
        packageID = packageInfo.getId();
        Dialog dialog1 = new AlertDialog.Builder(getActivity()).setIcon(
                android.R.drawable.btn_star).setTitle("确认").setMessage(
                "创建成功包裹ID为" + packageID + "是否继续添加包裹或快件?").setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddPackageListFragment fragment = new AddPackageListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("packageID", packageID);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.fragment_container_layout, fragment);
                transaction.addToBackStack("NewPackageInfoFragment");
                transaction.commit();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SorterIndexFragment fragment = new SorterIndexFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.fragment_container_layout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }).create();
        dialog1.show();
    }
}
