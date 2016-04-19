package extrace.Express.view.express_search_view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/19.
 */
public class ExpressFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.express,container,false);
        if(getArguments()!=null)
        {
            String ID = getArguments().getString("ID");
            /*
            * 显示快件路径信息
            * */
        }
        return view;
    }
}
