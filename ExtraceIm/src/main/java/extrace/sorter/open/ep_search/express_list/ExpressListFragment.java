package extrace.sorter.open.ep_search.express_list;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;

import android.app.ListFragment;
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

import extrace.model.ExpressInfo;
import extrace.sorter.ReceiverInfo.ReceiverInfoFragment;
import extrace.sorter.open.ep_search.package_list.PackageListFragment;
import extrace.sorter.SorterIndex.SorterIndexFragment;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/26.
 * 查看此包裹中的expresslist
 * 确认拆包发送packageID将包拆开
 */
public class ExpressListFragment extends Fragment implements ExpressListFragmentView {
    private TextView choose_package, choose_express;
    private ListView listView;
    private ExpressListPresenter presenter;
    private static String packageID;
    private ExpressListAdapter adp;
    private TextView save, title;
    private ImageButton back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ep_fragment, container, false);
        presenter = new ExpressListPresenterImpl(this);
        title = (TextView) view.findViewById(R.id.top_bar_center_text);
        title.setText("信息查看");
        back = (ImageButton) view.findViewById(R.id.top_bar_left_img);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        save = (TextView) view.findViewById(R.id.top_bar_right_text);
        save.setVisibility(View.VISIBLE);
        listView = (ListView) view.findViewById(R.id.listView);
        choose_express = (TextView) view.findViewById(R.id.choose_express);
        choose_package = (TextView) view.findViewById(R.id.choose_package);
        choose_express.setBackgroundColor(getResources().getColor(R.color.text_color));
        if (getArguments() != null) {
            packageID = getArguments().getString("packageID");
            presenter.onSearchEByPackageID(packageID);
        }
        choose_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageListFragment fragment = new PackageListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("packageID", packageID);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.fragment_container_layout, fragment);
                transaction.addToBackStack(null);
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
    public void onFail(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSuccess(List<ExpressInfo> list) {
        adp = new ExpressListAdapter(getActivity(), list);
        listView.setAdapter(adp);
    }

    @Override
    public void Success() {
        Dialog dialog1 = new AlertDialog.Builder(getActivity()).setIcon(
                android.R.drawable.btn_star).setTitle("确认").setMessage(
                "拆包成功").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SorterIndexFragment fragment = new SorterIndexFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_layout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }).create();
        dialog1.show();
    }
    //点击某快件 进入收件人详情页面

}
