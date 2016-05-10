package com.expressba.express.sorter.work;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import com.expressba.express.main.MyApplication;
import com.expressba.express.main.UIFragment;
import com.expressba.express.model.ExpressEntity;
import com.expressba.express.sorter.ReceiverInfo.ReceiverInfoFragment;
import com.expressba.express.R;

/**
 * Created by 黎明 on 2016/5/7.
 */
public class SearchWorkFragment extends UIFragment implements SearchWorkFragmentView, View.OnClickListener {

    private ExpressListAdapter adapter;
    private ListView listView;
    private EditText input;
    private ImageButton search;
    private SearchWorkPresenter presenter;
    private int EmployeesID;
    private static int DAY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_work, container, false);
        listView = (ListView) view.findViewById(R.id.listview);
        input = (EditText) view.findViewById(R.id.index_top_bar_input);
        input.setHint("请输入查询天数");
        search = (ImageButton) view.findViewById(R.id.index_top_bar_message);
        search.setImageResource(R.mipmap.search);
        search.setOnClickListener(this);
        presenter = new SearchWorkPresenterImpl(getActivity(), this);
        EmployeesID = ((MyApplication) getActivity().getApplication()).getEmployeesInfo().getId();
        if (getArguments() != null) {
            String text = getArguments().getString("key");
            presenter.searchWork(EmployeesID, text, DAY);
        }
        return view;
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(List<ExpressEntity> list) {
        adapter = new ExpressListAdapter(getActivity(), list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getFragmentManager().popBackStack();
                break;
            case R.id.index_top_bar_message:
                if (input.getText().toString() != null) {
                    DialogFragment fragment = new DateDialog();
                    try {
                        DAY = Integer.parseInt(input.getText().toString());
                        fragment.show(getFragmentManager(), "datePicker");
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "输入不合法", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public class ExpressListAdapter extends BaseAdapter {
        private List<ExpressEntity> elist;
        private LayoutInflater mInflater;

        public ExpressListAdapter(Context context, List<ExpressEntity> data) {
            elist = data;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (elist != null)
                return elist.size();
            else return 0;
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
            else return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            viewHolder view = null;
            if (convertView == null) {
                view = new viewHolder();
                convertView = mInflater.inflate(R.layout.search_work_item, null);
                view.ID = (TextView) convertView.findViewById(R.id.ID);
                view.gettime = (TextView) convertView.findViewById(R.id.gettime);
                view.outtime = (TextView) convertView.findViewById(R.id.outtime);
                view.info = (ImageButton) convertView.findViewById(R.id.info);
                convertView.setTag(view);
            } else {
                view = (viewHolder) convertView.getTag();
            }
            view.ID.setText(elist.get(position).getId());
            view.gettime.setText(elist.get(position).getGetTime());
            view.gettime.setText(elist.get(position).getOutTime());
            view.info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ReceiverInfoFragment fragment = new ReceiverInfoFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("express", elist.get(position));
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.fragment_container_layout, fragment);
                    transaction.addToBackStack("ExpressListFragment");
                    transaction.commit();
                }
            });
            return convertView;
        }

        class viewHolder {
            public TextView ID, gettime, outtime;
            public ImageButton info;
        }
    }
}
