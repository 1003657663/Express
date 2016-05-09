package com.expressba.express.sorter.close.add_package_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import com.expressba.express.model.PackageInfo;
import com.expressba.express.R;

/**
 * Created by 黎明 on 2016/4/17.
 * 显示express-search-list的适配器
 */
public class AddPackageListAdapter extends BaseAdapter {
    private List elist;
    private LayoutInflater mInflater;

    public AddPackageListAdapter(Context context, List data) {
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (elist != null)
            return position;
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder view = null;
        if (convertView == null) {
            view = new viewHolder();
            convertView = mInflater.inflate(R.layout.item, null);
            view.ID = (TextView) convertView.findViewById(R.id.id);
            view.info=(ImageButton)convertView.findViewById(R.id.info) ;
            convertView.setTag(view);
        } else {
            view = (viewHolder) convertView.getTag();
        }
        view.ID.setText(elist.get(position).toString());
        view.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    class viewHolder {
        public TextView ID;
        public ImageButton info;
    }
}

