package extrace.Customer.Express.view.express_edit_view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/19.
 * 快件追踪信息：地图+转运站点
 */
public class express_target_Fragment extends Fragment implements express_target_FragmentView
{
    private ImageButton back;
    private TextView title;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.express_target, container, false);
        back = (ImageButton) view.findViewById(R.id.top_bar_left_img);
        title = (TextView) view.findViewById(R.id.top_bar_center_text);
        title.setText("快件追踪信息");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        //title的简单处理
        if(getArguments()!=null)
        {
            //拿到ID 调用presenter 得到需要显示的信息
        }
        return view;
    }



}
