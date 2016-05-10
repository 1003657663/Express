package com.expressba.express.sorter.close.add_package_list;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.expressba.express.main.UIFragment;
import com.expressba.express.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import com.expressba.express.model.ExpressInfo;
import com.expressba.express.model.Package;
import com.expressba.express.sorter.SorterIndex.SorterIndexFragment;
import com.expressba.express.sorter.open.ep_search.express_list.ExpressListFragmentView;
import com.expressba.express.sorter.open.ep_search.express_list.ExpressListPresenter;
import com.expressba.express.sorter.open.ep_search.express_list.ExpressListPresenterImpl;
import com.expressba.express.sorter.open.ep_search.package_list.OpenPackagePresenter;
import com.expressba.express.sorter.open.ep_search.package_list.OpenPackagePresenterImpl;
import com.expressba.express.sorter.open.ep_search.package_list.PackageListFragmentView;
import com.expressba.express.sorter.open.ep_search.package_list.PackageListPresenter;
import com.expressba.express.sorter.open.ep_search.package_list.PackageListPresenterImpl;
import com.expressba.express.R;

/**
 * Created by 黎明 on 2016/4/30.
 * 查看包裹然后
 * 向包裹中添加包裹或快件
 * 接收参数为packageID
 */
public class AddPackageListFragment extends UIFragment implements PackageListFragmentView, ExpressListFragmentView, AddPackageListFragmentView, View.OnClickListener {
    private AddPackageListPresenter presenter;      //调用其load
    private ListView listView;                      //listView显示内容list
    private static String DpackageID;               //default包裹ID
    private static List IDlist = new ArrayList();     //IDlist
    private ImageButton scan, search;               //扫码查询
    private EditText input;                         //输入
    private Button open;                            //拆包
    private AddPackageListAdapter adapter;         //适配器
    private static int PACKAGE = 1, EXPRESS = 0;

    private PackageListPresenter PackageListPresenter;  //searchPackagebypackageid
    private ExpressListPresenter ExpressListPresenter;  //searchExpressBypackageid
    private OpenPackagePresenter OpenPackagePresenter;  //openpackage

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_package_list, container, false);
        IDlist.clear();
        PackageListPresenter = new PackageListPresenterImpl(this);
        ExpressListPresenter = new ExpressListPresenterImpl(this);
        OpenPackagePresenter = new OpenPackagePresenterImpl(getActivity(), this);
        presenter = new AddPackageListPresenterImpl(getActivity(), this);
        if (getArguments() != null) {
            //拿到参数packageID 根据此Id拿到packagelist显示 再根据此id拿到expresslist显示
            DpackageID = getArguments().getString("packageID");
            PackageListPresenter.onSearchPByPackageID(DpackageID);
        }

        listView = (ListView) view.findViewById(android.R.id.list);
        scan = (ImageButton) view.findViewById(R.id.index_top_bar_camera);
        search = (ImageButton) view.findViewById(R.id.index_top_bar_message);
        input = (EditText) view.findViewById(R.id.index_top_bar_input);
        open = (Button) view.findViewById(R.id.add_open);
        scan.setOnClickListener(this);
        search.setImageResource(R.mipmap.search);
        search.setOnClickListener(this);
        open.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.index_top_bar_message:
                if (input.getText() != null) {
                    //根据输入查询
                    Dialog dialog1 = new AlertDialog.Builder(getActivity()).setIcon(
                            android.R.drawable.btn_star).setTitle("添加类型").setMessage(
                            "请选择快件或包裹").setPositiveButton("快件", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            IDlist.add(input.getText().toString());
                            adapter=new AddPackageListAdapter(getActivity(),IDlist);
                            listView.setAdapter(adapter);
                            presenter.loadIntoPackage(DpackageID, input.getText().toString(), EXPRESS);//调用presenter
                        }
                    }).setNegativeButton("包裹", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            IDlist.add(input.getText().toString());
                            adapter=new AddPackageListAdapter(getActivity(),IDlist);
                            listView.setAdapter(adapter);
                            presenter.loadIntoPackage(DpackageID, input.getText().toString(), PACKAGE);//调用presenter
                        }
                    }).create();
                    dialog1.show();
                }
                break;
            case R.id.index_top_bar_camera:
                Dialog dialog1 = new AlertDialog.Builder(getActivity()).setIcon(
                        android.R.drawable.btn_star).setTitle("添加类型").setMessage(
                        "请选择快件或包裹").setPositiveButton("快件", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(getActivity(), CaptureActivity.class), 0);
                    }
                }).setNegativeButton("包裹", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(getActivity(), CaptureActivity.class), 1);
                    }
                }).create();
                dialog1.show();
                break;
            case R.id.add_open:
                Dialog dialog = new AlertDialog.Builder(getActivity()).setIcon(
                        android.R.drawable.btn_star).setTitle("确认信息").setMessage(
                        "确认拆包？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        OpenPackagePresenter.onOpenPackage(DpackageID);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
                dialog.show();
                break;
        }
    }
    @Override
    public void onExpressListFail(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(List<ExpressInfo> list) {
        //获取expresslist成功的通知
        for (int i = 0; i < list.size(); i++) {
            String expressID = list.get(i).getID();
            IDlist.add(expressID);
            adapter = new AddPackageListAdapter(getActivity(), IDlist);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onFail(String errorMessage) {
        //onpackagefail的话
        ExpressListPresenter.onSearchEByPackageID(DpackageID);
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPackageSuccess(List<Package> list) {
        //获取packagelist成功的通知
        for (int i = 0; i < list.size(); i++) {
            String packageID = list.get(i).getId();
            IDlist.add(packageID);
            adapter = new AddPackageListAdapter(getActivity(), IDlist);
            listView.setAdapter(adapter);
        }
        ExpressListPresenter.onSearchEByPackageID(DpackageID);
    }

    @Override
    public Activity getTheActivity() {
        return getActivity();
    }

    @Override
    public void Fail(String errorMessage) {
        if (IDlist.size() == 0)
            Toast.makeText(getActivity(), "此包为空", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            IDlist.remove(IDlist.size() - 1);
            adapter = new AddPackageListAdapter(getActivity(), IDlist);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void Success() {

        Toast.makeText(getActivity(), "操作成功", Toast.LENGTH_SHORT).show();
    }

    //扫码返回值
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                //所扫为快件
                Bundle bundle = data.getExtras();
                String result = bundle.getString("result");
                //调用presenter
                IDlist.add(result);
                listView.setAdapter(adapter);
                presenter.loadIntoPackage(DpackageID, result, EXPRESS);//调用presenter
            } else if (requestCode == 1) {
                //所扫为包裹
                Bundle bundle = data.getExtras();
                String result = bundle.getString("result");
                //调用presenter
                IDlist.add(result);
                listView.setAdapter(adapter);
                presenter.loadIntoPackage(DpackageID, result, PACKAGE);//调用presenter
            }
        }
    }

    @Override
    public void OpenSuccess() {
        //拆包成功的通知
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
