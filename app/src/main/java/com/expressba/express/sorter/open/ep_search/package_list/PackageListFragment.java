package com.expressba.express.sorter.open.ep_search.package_list;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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

import com.expressba.express.model.Package;
import com.expressba.express.sorter.open.ep_search.express_list.ExpressListFragment;
import com.expressba.express.sorter.SorterIndex.SorterIndexFragment;
import com.expressba.express.R;

/**
 * Created by 黎明 on 2016/4/26.
 * 查看此包裹中的packagelist
 */
public class PackageListFragment extends Fragment implements PackageListFragmentView {
    private TextView choose_package, choose_express;
    private ListView listView;
    private ImageButton back;
    private TextView title;
    private Button save;
    private static String packageID;
    private PackageListPresenter PackageListPresenter;
    private OpenPackagePresenter OpenPackagePresenter;
    private PackageListAdapter adp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ep_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        choose_express = (TextView) view.findViewById(R.id.choose_express);
        choose_package = (TextView) view.findViewById(R.id.choose_package);
        choose_package.setBackgroundColor(getResources().getColor(R.color.white));
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
        OpenPackagePresenter = new OpenPackagePresenterImpl(getActivity(), this);
        save = (Button) view.findViewById(R.id.chai);
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
                OpenPackagePresenter.onOpenPackage(packageID);
            }
        });

        choose_express.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_package.setBackgroundColor(getResources().getColor(R.color.whitesmoke));
                ExpressListFragment fragment1 = new ExpressListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("packageID", packageID);
                fragment1.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.fragment_container_layout, fragment1);
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
    public void onPackageSuccess(List<Package> list) {
        adp = new PackageListAdapter(getActivity(), list);
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
    public void onFail(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

    public class PackageListAdapter extends BaseAdapter {
        private List<Package> elist;
        private LayoutInflater mInflater;

        public PackageListAdapter(Context context, List<Package> data) {
            elist = data;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {

            return elist.size();

        }

        @Override
        public Object getItem(int position) {
            if (elist != null)
                return elist.get(position);
            else return null;
        }

        @Override
        public long getItemId(int position) {
            if (elist != null)
                return position;
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            viewHolder view = null;
            if (convertView == null) {
                view = new viewHolder();
                convertView = mInflater.inflate(R.layout.package_item, null);
                view.ID = (TextView) convertView.findViewById(R.id.id);
                view.EmployeesID = (TextView) convertView.findViewById(R.id.EmployeesID);
                view.info = (ImageButton) convertView.findViewById(R.id.info);
                convertView.setTag(view);
            } else {
                view = (viewHolder) convertView.getTag();
            }
            view.ID.setText(elist.get(position).getId());
            view.EmployeesID.setText(String.valueOf(elist.get(position).getEmployeesId()));
            view.info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //查看包裹详情
                    PackageListFragment fragment = new PackageListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("packageID", elist.get(position).getId());
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    // transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.replace(R.id.fragment_container_layout, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
            return convertView;
        }

        class viewHolder {
            public TextView ID, EmployeesID;
            public ImageButton info;
        }

    }


}
