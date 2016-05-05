package extrace.sorter.close.add_package_list;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.ArrayList;

import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/30.
 * 向包裹中添加包裹或快件
 * 接收参数为packageID
 */
public class AddPackageListFragment extends ListFragment implements AddPackageListFragmentView, View.OnClickListener {
    private AddPackageListPresenter presenter;
    private ListView listView;                   //listView显示内容list
    private static String DpackageID;            //default包裹ID
    private ArrayList list = new ArrayList();      //内容list
    private ImageButton scan, search;
    private EditText input;
    private Button open;
    private LinearLayout item;
    private TextView IDtext;
    private AddPackageListAdapter adapter;    //适配器
    private static int PACKAGE = 1, EXPRESS = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_package_list, container, false);
        listView = (ListView) view.findViewById(android.R.id.list);
        scan = (ImageButton) view.findViewById(R.id.index_top_bar_camera);
        search = (ImageButton) view.findViewById(R.id.index_top_bar_message);
        input = (EditText) view.findViewById(R.id.index_top_bar_input);
        item=(LinearLayout)view.findViewById(R.id.item);
        IDtext=(TextView)view.findViewById(R.id.id);
        open = (Button) view.findViewById(R.id.add_open);
        item.setOnClickListener(this);
        IDtext.setOnClickListener(this);
        scan.setOnClickListener(this);
        search.setOnClickListener(this);
        open.setOnClickListener(this);
        adapter = new AddPackageListAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        presenter = new AddPackageListPresenterImpl(getActivity(), this);
        if (getArguments() != null) {
            DpackageID = getArguments().getString("packageID");
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.index_top_bar_message:
                if (input.getText() != null) {
                    Dialog dialog1 = new AlertDialog.Builder(getActivity()).setIcon(
                            android.R.drawable.btn_star).setTitle("添加类型").setMessage(
                            "请选择快件或包裹").setPositiveButton("快件", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            list.add(input.getText().toString());
                            listView.setAdapter(adapter);
                            presenter.loadIntoPackage(DpackageID, input.getText().toString(), EXPRESS);//调用presenter
                        }
                    }).setNegativeButton("包裹", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            list.add(input.getText().toString());
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
                        presenter.onOpen(DpackageID);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
                dialog.show();
                break;
            case R.id.item:
            {
                String ID=IDtext.getText().toString();
                Toast.makeText(getActivity(),ID,Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void Fail(String errorMessage) {
        if (list.size() == 0)
            Toast.makeText(getActivity(), "此包为空", Toast.LENGTH_SHORT).show();
        else {
            list.remove(list.size() - 1);
            listView.setAdapter(adapter);
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
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
                list.add(result);
                listView.setAdapter(adapter);
                presenter.loadIntoPackage(DpackageID, result, EXPRESS);//调用presenter
            } else if (requestCode == 1) {
                //所扫为包裹
                Bundle bundle = data.getExtras();
                String result = bundle.getString("result");
                //调用presenter
                list.add(result);
                listView.setAdapter(adapter);
                presenter.loadIntoPackage(DpackageID, result, PACKAGE);//调用presenter
            }
        }
    }

}
