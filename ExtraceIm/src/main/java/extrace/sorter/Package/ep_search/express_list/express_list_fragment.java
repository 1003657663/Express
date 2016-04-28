package extrace.sorter.Package.ep_search.express_list;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import extrace.model.ExpressInfo;
import extrace.sorter.Package.ep_search.package_list.package_list_Fragment;
import extrace.sorter.Sorter_Index_Fragment;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/26.
 */
public class express_list_fragment extends Fragment implements express_list_fragmentView
{

    private TextView choose_package,choose_express;
    private ListView listView;
    private express_list_presenter presenter;
    private static String packageID;
    private express_list_adapter adp;
    private TextView save,title;
    private ImageButton back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.ep_fragment,container,false);
       presenter=new express_list_presenterImpl(this);
        title=(TextView)view.findViewById(R.id.top_bar_center_text);
        title.setText("信息查看");
        back=(ImageButton)view.findViewById(R.id.top_bar_left_img);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        save=(TextView)view.findViewById(R.id.top_bar_right_text);
        listView=(ListView)view.findViewById(R.id.listView);
        choose_express=(TextView) view.findViewById(R.id.choose_express);
        choose_package=(TextView) view.findViewById(R.id.choose_package);
        choose_express.setBackgroundColor(getResources().getColor(R.color.text_color));
        if(getArguments()!=null) {
            packageID=getArguments().getString("packageID");
            presenter.onSearchEByPackageID(packageID);
        }
        choose_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   package_list_Fragment fragment=new package_list_Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("packageID",packageID);
                     fragment.setArguments(bundle);
                     FragmentTransaction transaction = getFragmentManager().beginTransaction();
                     transaction.replace(R.id.fragment_container_layout, fragment);
                     transaction.addToBackStack("express_list_fragment");
                      transaction.commit();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              presenter.onOpen(packageID);
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
    public void onSuccess(List<ExpressInfo> list) {
        adp=new express_list_adapter(getActivity(),list);
        listView.setAdapter(adp);

    }

    @Override
    public void Success() {
        Toast.makeText(getActivity(),"拆包成功",Toast.LENGTH_LONG).show();
        Sorter_Index_Fragment fragment=new Sorter_Index_Fragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
