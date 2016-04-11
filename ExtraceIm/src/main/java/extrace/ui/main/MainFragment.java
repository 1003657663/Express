package extrace.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import extrace.ui.domain.ExpressEditActivity;
import extrace.ui.misc.CustomerListActivity;

public class MainFragment  extends Fragment {
	
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MainFragment newInstance() {
    	MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //fragment_main中的操作激发
        rootView.findViewById(R.id.action_ex_receive_icon).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						StartReceiveExpress();
					}
				});//快件揽收
        rootView.findViewById(R.id.action_ex_receive).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						StartReceiveExpress();
					}
				});//快件揽收
        rootView.findViewById(R.id.action_ex_transfer_icon).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						StartQueryExpress();
					}
				});//快件派送
        rootView.findViewById(R.id.action_ex_transfer).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						StartQueryExpress();
					}
				});//快件派送


        //包裹拆包和打包在这里

        rootView.findViewById(R.id.action_cu_mng_icon).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						StartCustomerList();
					}
				});//客户管理
        rootView.findViewById(R.id.action_cu_mng).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						StartCustomerList();
					}
				});//客户管理

        rootView.findViewById(R.id.action_ex_qur_icon).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						StartQueryExpress();
					}
				});//快件查询
        rootView.findViewById(R.id.action_ex_qur).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						StartQueryExpress();
					}
				});//快件查询

        return rootView;
    }
    
    void StartReceiveExpress()
    {
		Intent intent = new Intent();
		intent.putExtra("Action","New");
		intent.setClass(this.getActivity(), ExpressEditActivity.class);
		startActivityForResult(intent, 0);  	
    }

    void StartQueryExpress()
    {
		Intent intent = new Intent();
		intent.putExtra("Action","Query");
		intent.setClass(this.getActivity(), ExpressEditActivity.class);
		startActivityForResult(intent, 0);  	
    }

    void StartCustomerList()
    {
		Intent intent = new Intent();
		intent.putExtra("Action","None");
		intent.setClass(this.getActivity(), CustomerListActivity.class);
		startActivityForResult(intent, 0);
    }

}
