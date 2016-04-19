package extrace.Express.view.express_search_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import extrace.model.ExpressSheet;
import extrace.ui.main.R;

import java.util.List;

/**
 * Created by 黎明 on 2016/4/17.
 * 显示express-search-list的适配器
 *
 */
public class express_search_adapter extends BaseAdapter
{
    private List<ExpressSheet> elist;
    private LayoutInflater mInflater;
    public  express_search_adapter(Context context, List<ExpressSheet> data)
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
            convertView=mInflater.inflate(R.layout.express_item,null);
            view.ID=(TextView)convertView.findViewById(R.id.ID);
            view.type=(TextView)convertView.findViewById(R.id.type);
            view.status=(TextView)convertView.findViewById(R.id.status);
            //view.gettime=(TextView)convertView.findViewById(R.id.gettime);
            convertView.setTag(view);
        }
        else
        {
            view=(viewHolder)convertView.getTag();
        }
        view.ID.setText(elist.get(position).getID().toString());
      //  view.type.setText(elist.get(position).getType());
        view.status.setText(elist.get(position).getStatus().toString());
       // view.gettime.setText(elist.get(position).getAccepteTime().toString());
        convertView.setClickable(false);//  导致单击无效
        convertView.setFocusable(false);
        convertView.setFocusableInTouchMode(false);
        return convertView;
    }
    class viewHolder
    {
        public TextView ID,status,type;//gettime
    }
}

