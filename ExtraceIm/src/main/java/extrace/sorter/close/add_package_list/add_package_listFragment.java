package extrace.sorter.close.add_package_list;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import java.util.ArrayList;

import extrace.sorter.Sorter_Index_Fragment;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/30.
 */
public class add_package_listFragment extends ListFragment implements add_package_listFragmentView
{
    private add_package_listPresenter presenter;
    private ListView listView;
    private ArrayList plist=new ArrayList();
    private ArrayList elist=new ArrayList();
    private static String DpackageID;
   private ArrayList list=new ArrayList();
    private ImageButton scan,search;
    private EditText input;
    private add_package_list_adapter adapter;
    private Button closebutton;
    private TextView delete;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.add_package_list,container,false);
        listView=(ListView)view.findViewById(android.R.id.list);
        scan=(ImageButton)view.findViewById(R.id.top_bar_left_img);
        adapter=new add_package_list_adapter(getActivity(),list);
        presenter=new add_package_listPresenterImpl(getActivity(),this);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new AlertDialog.Builder(getActivity()).setIcon(
                        android.R.drawable.btn_star).setTitle("扫码类型").setMessage(
                        "请选择快件或包裹").setPositiveButton("快件", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      
                        startActivityForResult(new Intent(getActivity(),CaptureActivity.class),0);
                    }
                }).setNegativeButton("包裹", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivityForResult(new Intent(getActivity(),CaptureActivity.class),1);
                    }
                }).create();
                dialog.show();
            }
        });
        presenter=new add_package_listPresenterImpl(getActivity(),this);
       if(getArguments().getString("packageID")!=null)
        {
            DpackageID=getArguments().getString("packageID");
            elist.add(DpackageID);
            plist.add(DpackageID);
        }
        closebutton = (Button) view.findViewById(R.id.close_button);
        closebutton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               presenter.addPackage(plist);
            }
        });
        search=(ImageButton)view.findViewById(R.id.enter);
        input=(EditText)view.findViewById(R.id.index_top_bar_input);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input.getText()!=null)
                {
                    Dialog dialog1 = new AlertDialog.Builder(getActivity()).setIcon(
                            android.R.drawable.btn_star).setTitle("添加类型").setMessage(
                            "请选择快件或包裹").setPositiveButton("快件", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            elist.add(input);
                            list.add(input);
                            listView.setAdapter(adapter);
                        }
                    }).setNegativeButton("包裹", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            plist.add(input);
                            list.add(input);
                            listView.setAdapter(adapter);
                        }
                    }).create();
                    dialog1.show();
                }
            }
        });
        return view;
    }

    @Override
    public void Fail() {
        Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void Success() {
        presenter.addExpress(elist);
    }
    public void pSuccess()
    {
        //Toast.makeText(getActivity(),"打包成功",Toast.LENGTH_LONG).show();
        Dialog dialog1 = new AlertDialog.Builder(getActivity()).setIcon(
                android.R.drawable.btn_star).setTitle("确认").setMessage(
                "打包成功").setPositiveButton("确认",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Sorter_Index_Fragment fragment=new Sorter_Index_Fragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_layout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }).create();
        dialog1.show();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK) {
            if (requestCode == 1)
            {
               Bundle bundle = data.getExtras();
                String result = bundle.getString("result");
                plist.add(result);
                list.add(result);
                listView.setAdapter(adapter);

            }
            else if(requestCode==0)
            {
                Bundle bundle = data.getExtras();
                String result = bundle.getString("result");
                elist.add(result);
                list.add(result);
                listView.setAdapter(adapter);
            }
        }
    }
}
