package com.expressba.express.sorter.open.package_search;

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

import com.expressba.express.main.UIFragment;
import com.expressba.express.model.PackageInfo;
import com.expressba.express.sorter.open.ep_search.package_list.PackageListFragment;
import com.expressba.express.R;

/**
 * Created by 黎明 on 2016/4/25.
 * 包裹拆包
 * 扫码选择包裹
 * 查看包裹信息
 * 确认转入 PackageListFragment
 */
public class PackageSearchFragment extends UIFragment implements PackageSearchFragmentView {
    private TextView title, package_from, package_to, EmployeesID, EmployeesName, closetime, ID;
    private PackageSearchPresenter PackageSearchPresenter;
    private Button open;
    private ImageButton back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.packageinfo, container, false);
        package_from = (TextView) view.findViewById(R.id.package_from);
        package_to = (TextView) view.findViewById(R.id.package_to);
        EmployeesID = (TextView) view.findViewById(R.id.EmployeesID);
        EmployeesName = (TextView) view.findViewById(R.id.EmployeesName);
        closetime = (TextView) view.findViewById(R.id.closetime);
        ID = (TextView) view.findViewById(R.id.ID);
        title = (TextView) view.findViewById(R.id.top_bar_center_text);
        title.setText("包裹信息");
        open = (Button) view.findViewById(R.id.open);
        back = (ImageButton) view.findViewById(R.id.top_bar_left_img);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到eplist页面
                PackageListFragment fragment = new PackageListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("packageID", ID.getText().toString());
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.fragment_container_layout, fragment);
                transaction.addToBackStack("PackageSearchFragment");
                transaction.commit();
            }
        });
        if (getArguments() != null) {
            String packageID = getArguments().getString("ID");
            PackageSearchPresenter = new PackageSearchPresenterImpl(this);
            PackageSearchPresenter.onopenPackage(packageID);
        }
        return view;
    }

    @Override
    public Activity getTheActivity() {
        return getActivity();
    }

    @Override
    public void Fail(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void Success(PackageInfo packageInfo) {
        package_from.setText(packageInfo.getPackageFrom());
        package_to.setText(packageInfo.getPackageTo());
        EmployeesName.setText(packageInfo.getEmployeesName());
        EmployeesID.setText(String.valueOf(packageInfo.getEmployeesID()));
        closetime.setText(packageInfo.getCloseTime());
        ID.setText(packageInfo.getId());
    }

}
