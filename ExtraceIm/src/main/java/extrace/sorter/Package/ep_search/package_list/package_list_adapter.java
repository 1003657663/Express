package extrace.sorter.Package.ep_search.package_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import extrace.model.Package;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/17.
 * 显示express-search-list的适配器
 *
 */
public class package_list_adapter extends BaseAdapter
{
    private List<Package> elist;
    private LayoutInflater mInflater;
    public package_list_adapter(Context context, List<Package> data)
    {
        elist=data;
        mInflater=LayoutInflater.from(context);
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(elist!=null)
        return position;
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder view=null;
        if(convertView==null)
        {
            view=new viewHolder();
            convertView=mInflater.inflate(R.layout.package_item,null);
            view.ID=(TextView)convertView.findViewById(R.id.id);
            view.EmployeesID=(TextView)convertView.findViewById(R.id.EmployeesID);
            view.time=(TextView)convertView.findViewById(R.id.time);
            convertView.setTag(view);
        }
        else
        {
            view=(viewHolder)convertView.getTag();
        }
       view.ID.setText(elist.get(position).getId());
        view.EmployeesID.setText(String.valueOf(elist.get(position).getEmployeesId()));
        view.time.setText(elist.get(position).getTime());

        return convertView;
    }
    class viewHolder
    {
        public TextView ID,EmployeesID,time;
    }
}

