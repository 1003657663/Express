package extrace.sorter.open.ep_search.package_list;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import extrace.model.Package;

import extrace.sorter.open.ep_search.express_list.ExpressListFragment;
import extrace.sorter.SorterIndex.SorterIndexFragment;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/26.
 * 查看此包裹中的packagelist
 */
public class PackageListFragment extends Fragment implements PackageListFragmentView {
    private TextView choose_package, choose_express;
    private ListView listView;
    private TextView title, save;
    private ImageButton back;
    private static String packageID = "";
    private PackageListPresenter PackageListPresenter;
    private PackageListAdapter adp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ep_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        title = (TextView) view.findViewById(R.id.top_bar_center_text);
        title.setText("信息查看");
        back = (ImageButton) view.findViewById(R.id.top_bar_left_img);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        PackageListPresenter = new PackageListPresenterImpl(this);
        save = (TextView) view.findViewById(R.id.top_bar_right_text);
        save.setVisibility(View.VISIBLE);
        save.setText("拆包");
        if (getArguments() != null) {
            packageID = getArguments().getString("packageID");
            PackageListPresenter.onSearchPByPackageID(packageID);
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击save 确认拆包
                PackageListPresenter.onOpen(packageID);
            }
        });
        choose_express = (TextView) view.findViewById(R.id.choose_express);
        choose_package = (TextView) view.findViewById(R.id.choose_package);
        choose_package.setBackgroundColor(getResources().getColor(R.color.text_color));
        choose_express.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpressListFragment fragment1 = new ExpressListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("packageID", packageID);
                fragment1.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.remove(PackageListFragment.this);
                transaction.add(R.id.fragment_container_layout, fragment1);
                // / transaction.replace(R.id.fragment_container_layout,fragment1);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    @Override
    public Activity getTheActivity() {
        return getActivity();
    }

    @Override
    public void onFail() {
        Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(List<Package> list) {
        adp = new PackageListAdapter(getActivity(), list);
        listView.setAdapter(adp);
    }

    @Override
    public void Success() {
        //Toast.makeText(getActivity(),"拆包成功",Toast.LENGTH_LONG).show();
        Dialog dialog1 = new AlertDialog.Builder(getActivity()).setIcon(
                android.R.drawable.btn_star).setTitle("确认").setMessage(
                "拆包成功").setPositiveButton("确认", new DialogInterface.OnClickListener() {
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
