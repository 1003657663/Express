package extrace.sorter.Package.ep_search.package_list;

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


import java.util.List;

import extrace.model.Package;

import extrace.sorter.Package.ep_search.express_list.Express_list_fragment;
import extrace.sorter.Sorter_Index_Fragment;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/26.
 */
public class Package_list_Fragment extends Fragment implements Package_list_FragmentView
{
    private TextView choose_package,choose_express;
    private ListView listView;
    private TextView title,save;
    private ImageButton back;
    private static String packageID="";
    private Package_list_presenter Package_list_presenter;
    private Package_list_adapter adp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.ep_fragment,container,false);
       listView=(ListView)view.findViewById(R.id.listView);
        title=(TextView)view.findViewById(R.id.top_bar_center_text);
        title.setText("信息查看");
        back=(ImageButton)view.findViewById(R.id.top_bar_left_img);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        Package_list_presenter =new Package_list_presenterImpl(this);
        save=(TextView)view.findViewById(R.id.top_bar_right_text);
        if(getArguments()!=null) {
            packageID=getArguments().getString("packageID");
            //Toast.makeText(getActivity(),packageID,Toast.LENGTH_LONG).show();
            Package_list_presenter.onSearchPByPackageID(packageID);
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击save 确认拆包
               Package_list_presenter.onOpen(packageID);
            }
        });
        choose_express=(TextView) view.findViewById(R.id.choose_express);
        choose_package=(TextView) view.findViewById(R.id.choose_package);
        choose_package.setBackgroundColor(getResources().getColor(R.color.text_color));
       choose_express.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Express_list_fragment fragment1=new Express_list_fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("packageID",packageID);
                fragment1.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                 transaction.replace(R.id.fragment_container_layout,fragment1);
                transaction.addToBackStack("Package_list_Fragment");
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

    }

    @Override
    public void onSuccess(List<Package> list) {
        adp=new Package_list_adapter(getActivity(),list);
        listView.setAdapter(adp);
    }

    @Override
    public void Success() {
        //Toast.makeText(getActivity(),"拆包成功",Toast.LENGTH_LONG).show();
        Dialog dialog1 = new AlertDialog.Builder(getActivity()).setIcon(
                android.R.drawable.btn_star).setTitle("确认").setMessage(
                "拆包成功").setPositiveButton("确认",new DialogInterface.OnClickListener() {
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
}
