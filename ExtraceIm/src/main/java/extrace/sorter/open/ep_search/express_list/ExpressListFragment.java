package extrace.sorter.open.ep_search.express_list;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;

import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import extrace.model.ExpressInfo;
import extrace.model.Package;
import extrace.sorter.ReceiverInfo.ReceiverInfoFragment;
import extrace.sorter.open.ep_search.package_list.OpenPackagePresenter;
import extrace.sorter.open.ep_search.package_list.OpenPackagePresenterImpl;
import extrace.sorter.open.ep_search.package_list.PackageListFragment;
import extrace.sorter.SorterIndex.SorterIndexFragment;
import extrace.sorter.open.ep_search.package_list.PackageListFragmentView;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/26.
 * 查看此包裹中的expresslist
 * 确认拆包发送packageID将包拆开
 */
public class ExpressListFragment extends Fragment implements ExpressListFragmentView,PackageListFragmentView {
    private TextView choose_package, choose_express;
    private ListView listView;
    private ExpressListPresenter presenter;
    private OpenPackagePresenter packagePresenter;
    private static String packageID;
    private ExpressListAdapter adp;
    private TextView title;
    private ImageButton back;
    private Button save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ep_fragment, container, false);
        presenter = new ExpressListPresenterImpl(this);
        title = (TextView) view.findViewById(R.id.top_bar_center_text);
        title.setText("信息查看");
        save=(Button)view.findViewById(R.id.chai);
        back = (ImageButton) view.findViewById(R.id.top_bar_left_img);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        listView = (ListView) view.findViewById(R.id.listView);
        choose_express = (TextView) view.findViewById(R.id.choose_express);
        choose_package = (TextView) view.findViewById(R.id.choose_package);
        packagePresenter=new OpenPackagePresenterImpl(getActivity(),this);
        choose_express.setBackgroundColor(getResources().getColor(R.color.white));
        if (getArguments() != null) {
            packageID = getArguments().getString("packageID");
            presenter.onSearchEByPackageID(packageID);
        }
        choose_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_express.setBackgroundColor(getResources().getColor(R.color.whitesmoke));
                PackageListFragment fragment = new PackageListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("packageID", packageID);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
               // transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.fragment_container_layout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              packagePresenter.onOpenPackage(packageID);
            }
        });
        return view;
    }

    @Override
    public Activity getTheActivity() {
        return getActivity();
    }

    @Override
    public void onExpressListFail(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(List<ExpressInfo> list) {
        adp = new ExpressListAdapter(getActivity(), list);
        listView.setAdapter(adp);
    }
    @Override
    public void OpenSuccess() {
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

    @Override
    public void onPackageSuccess(List<Package> list) {

    }
    public class ExpressListAdapter extends BaseAdapter
    {
        private List<ExpressInfo> elist;
        private LayoutInflater mInflater;

        public ExpressListAdapter(Context context, List<ExpressInfo> data) {
            elist = data;
            mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            if(elist!=null)
                return elist.size();
            else return 0;
        }
        @Override
        public Object getItem(int position) {
            if(elist!=null)
                return elist.get(position);
            else return null;
        }

        @Override
        public long getItemId(int position) {
            if(elist!=null)
                return position;
            else return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            viewHolder view=null;
            if(convertView==null)
            {
                view=new viewHolder();
                convertView=mInflater.inflate(R.layout.express_item1,null);
                view.ID=(TextView)convertView.findViewById(R.id.ID);
                view.gettime=(TextView)convertView.findViewById(R.id.gettime);
                view.info=(ImageButton)convertView.findViewById(R.id.info);
                convertView.setTag(view);
            }
            else
            {
                view=(viewHolder)convertView.getTag();
            }
            view.ID.setText(elist.get(position).getID());
            view.gettime.setText(elist.get(position).getGetTime());
            view.info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ReceiverInfoFragment fragment=new ReceiverInfoFragment();
                    FragmentTransaction transaction=getFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("express",elist.get(position));
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.fragment_container_layout,fragment);
                    transaction.addToBackStack("ExpressListFragment");
                    transaction.commit();
                }
            });
            return convertView;
        }
        class viewHolder
        {
            public TextView ID,gettime;
            public ImageButton info;
        }
    }
}
