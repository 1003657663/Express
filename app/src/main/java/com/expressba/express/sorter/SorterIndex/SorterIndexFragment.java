package com.expressba.express.sorter.SorterIndex;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.Toast;

import com.expressba.express.main.UIFragment;
import com.expressba.express.zxing.activity.CaptureActivity;

import com.expressba.express.model.PackageInfo;
import com.expressba.express.sorter.Expressupdate.ExpressUpdateFragment;
import com.expressba.express.sorter.SorterMe;
import com.expressba.express.sorter.open.ep_search.package_list.PackageListFragment;
import com.expressba.express.sorter.open.package_search.PackageSearchFragment;
import com.expressba.express.main.MyApplication;
import com.expressba.express.sorter.close.add_package_list.AddPackageListFragment;
import com.expressba.express.sorter.close.new_package_info.NewPackageInfoFragment;
import com.expressba.express.R;
import com.expressba.express.user.login.LoginFragment;

/**
 * Created by 黎明 on 2016/4/25.
 */
public class SorterIndexFragment extends UIFragment implements SorterIndexFragmentView {
    private Button meButton;
    private FragmentTransaction transaction;
    private ImageButton cameraButton;
    private ImageButton messageButton;
    private MyApplication myApplication;
    private SorterIndexPresenter presenter;
    private int EmployeesID;
    private static String result;
    private Button open, close;
    private EditText input;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sorter_index_fragment, container, false);
        myApplication = (MyApplication) getActivity().getApplication();
        transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        cameraButton = (ImageButton) view.findViewById(R.id.index_top_bar_camera);
        messageButton = (ImageButton) view.findViewById(R.id.index_top_bar_message);
        messageButton.setImageResource(R.mipmap.search);
        open = (Button) view.findViewById(R.id.open);
        input = (EditText) view.findViewById(R.id.index_top_bar_input);
        input.setHint("请输入快件单号");
        close = (Button) view.findViewById(R.id.close);
        EmployeesID = ((MyApplication) getActivity().getApplication()).getEmployeesInfo().getId();
        presenter = new SorterIndexPresenterImpl(getActivity(), this);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
                //1为拆包动作 此时需调用zxing执行扫码 扫码完毕后会返回一个packageID
                //拆包就是扫码-显示包裹信息-查看快件信息-确认拆包
            }
        });


        close = (Button) view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //2为打包
                Dialog dialog = new AlertDialog.Builder(getActivity()).setIcon(
                        android.R.drawable.btn_star).setTitle("包裹类型").setMessage(
                        "请选择包裹类型").setPositiveButton("中转包",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NewPackageInfoFragment fragment = new NewPackageInfoFragment();
                                transaction.replace(R.id.fragment_container_layout, fragment);
                                transaction.addToBackStack("sindex");
                                transaction.commit();
                            }
                        }).setNegativeButton("派送包", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.CreatPackage(1, 1, EmployeesID, 0);
                        //派送包fromID设为1 isSorter为0
                    }
                }).setNeutralButton("揽收包", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.CreatPackage(0, 1, EmployeesID, 0);
                        //揽收包fromID设为0 isSorter为0
                    }
                }).create();
                dialog.show();
            }
        });
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();

            }
        });
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!input.getText().toString().isEmpty())
                    sendToPackageFragment(input.getText().toString());
            }
        });
        meButton = (Button) view.findViewById(R.id.me_button);
        meButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!myApplication.getUserInfo().getLoginState()) {//没有登陆跳转登陆界面
                    toLoginFragment();
                } else {
                    //登陆后跳转"我"界面
                    toMeFragment();
                    // }
                }
            }
        });
        return view;
    }

    private void toMeFragment() {
        SorterMe meFragment = new SorterMe();
        transaction.replace(R.id.fragment_container_layout, meFragment);
        transaction.addToBackStack("SorterIndexFragment");
        transaction.commit();
    }

    private void toLoginFragment() {
        LoginFragment loginFragment = new LoginFragment();
        transaction.replace(R.id.fragment_container_layout, loginFragment);
        transaction.addToBackStack("index");
        transaction.commit();
    }

    private void startCamera() {
        startActivityForResult(new Intent(getActivity(), CaptureActivity.class), 0);
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                final Bundle bundle = data.getExtras();
                result = bundle.getString("result");
                Dialog dialog = new AlertDialog.Builder(getActivity()).setIcon(
                        android.R.drawable.btn_star).setTitle("用户类型").setMessage(
                        "请选择您的职业").setPositiveButton("分拣员",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PackageSearchFragment fragment = new PackageSearchFragment();
                                Bundle bundle1 = new Bundle();
                                bundle1.putString("ID", result);
                                fragment.setArguments(bundle1);
                                transaction.replace(R.id.fragment_container_layout, fragment);
                                transaction.addToBackStack("null");
                                transaction.commit();
                            }
                        }).setNegativeButton("快递员", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PackageListFragment fragment = new PackageListFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("packageID", result);
                        fragment.setArguments(bundle);
                        transaction.replace(R.id.fragment_container_layout, fragment);
                        transaction.addToBackStack("null");
                        transaction.commit();
                    }
                }).create();
                dialog.show();
            }
        }
    }

    @Override
    public void onSuccess(final PackageInfo packageInfo) {
        Dialog dialog = new AlertDialog.Builder(getActivity()).setIcon(
                android.R.drawable.btn_star).setTitle("通知").setMessage(
                "创建包裹成功，包裹ID为" + packageInfo.getId() + "是否向包裹中添加快件？").setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddPackageListFragment fragment = new AddPackageListFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("packageID", packageInfo.getId());
                        fragment.setArguments(bundle);
                        transaction.replace(R.id.fragment_container_layout, fragment);
                        transaction.addToBackStack("sindex");
                        transaction.commit();
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SorterIndexFragment fragment = new SorterIndexFragment();
                transaction.replace(R.id.fragment_container_layout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }).create();
        dialog.show();
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    public void sendToPackageFragment(String input) {
        ExpressUpdateFragment fragment = new ExpressUpdateFragment();
        transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("ID", input);
        fragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container_layout, fragment);
        transaction.addToBackStack("SorterIndexFragment");
        transaction.commit();
    }
}
