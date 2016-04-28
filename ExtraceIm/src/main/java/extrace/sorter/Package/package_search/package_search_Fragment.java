package extrace.sorter.Package.package_search;

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

import extrace.model.packageInfo;
import extrace.sorter.Package.ep_search.package_list.package_list_Fragment;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/25.
 */
public class package_search_Fragment extends Fragment implements package_search_FragmentView
{
    private TextView title,package_from,package_to,EmployeesID,EmployeesName,closetime,ID;
    private package_search_presenter package_search_presenter;
    private Button open;
    private ImageButton back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.packageinfo,container,false);
        package_from=(TextView)view.findViewById(R.id.package_from);
        package_to=(TextView)view.findViewById(R.id.packageto);
        EmployeesID=(TextView)view.findViewById(R.id.EmployeesID);
        EmployeesName=(TextView)view.findViewById(R.id.EmployeesName);
        closetime=(TextView)view.findViewById(R.id.closetime);
        ID=(TextView)view.findViewById(R.id.ID);
        title=(TextView)view.findViewById(R.id.top_bar_center_text);
        title.setText("包裹信息");
        open=(Button)view.findViewById(R.id.open);
        back=(ImageButton)view.findViewById(R.id.top_bar_left_img);
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
                package_list_Fragment fragment = new package_list_Fragment();
                Bundle bundle = new Bundle();
               // Toast.makeText(getActivity(),ID.getText().toString(),Toast.LENGTH_LONG).show();
                bundle.putString("packageID",ID.getText().toString());
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_layout, fragment);
                transaction.addToBackStack("package_search_Fragment");
               transaction.commit();
            }
        });
        if(getArguments()!=null)
        {
            String packageID=getArguments().getString("packageID");
           // Toast.makeText(getActivity(),packageID,Toast.LENGTH_LONG).show();
            package_search_presenter=new package_search_presenterImpl(this);
            package_search_presenter.onopenPackage(packageID);
        }
        return view;
    }

    @Override
    public Activity getTheActivity() {
        return getActivity();
    }

    @Override
    public void Fail() {
        Toast.makeText(getActivity(),"sorry,search failed",Toast.LENGTH_LONG).show();
    }

    @Override
    public void Success(packageInfo packageInfo) {
        package_from.setText(packageInfo.getPackagefrom());
        package_to.setText(packageInfo.getPackageto());
        EmployeesName.setText(packageInfo.getEmployeesName());
        EmployeesID.setText(String.valueOf(packageInfo.getEmployeesID()));
        closetime.setText(packageInfo.getClosetime());
        ID.setText(packageInfo.getID());
    }

}
