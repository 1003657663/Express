package extrace.sorter.Package.ep_search.express_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import extrace.model.ExpressInfo;

import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/17.
 * 显示express-search-list的适配器
 *
 */
public class express_list_adapter extends BaseAdapter
{
    private List<ExpressInfo> elist;
    private LayoutInflater mInflater;
    public express_list_adapter(Context context, List<ExpressInfo> data)
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
            convertView=mInflater.inflate(R.layout.express_item1,null);
            view.ID=(TextView)convertView.findViewById(R.id.ID);
            view.gettime=(TextView)convertView.findViewById(R.id.gettime);
            view.Radd=(TextView)convertView.findViewById(R.id.Radd);
            view.Sadd=(TextView)convertView.findViewById(R.id.Sadd);
            convertView.setTag(view);
        }
        else
        {
            view=(viewHolder)convertView.getTag();
        }
        view.ID.setText(elist.get(position).getID());
        view.gettime.setText(elist.get(position).getGetTime());
        view.Radd.setText(elist.get(position).getRadd());
        view.Sadd.setText(elist.get(position).getSadd());

        return convertView;
    }
    class viewHolder
    {
        public TextView ID,gettime,Radd,Sadd;
    }
}

