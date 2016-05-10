package com.expressba.express.user.expresshistory;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.expressba.express.main.UIFragment;
import com.expressba.express.model.ExpressInfo;
import com.expressba.express.R;

/**
 * 快件详情
 * Created by songchao on 16/5/8.
 */
public class ExpressHistoryDetailFragment extends UIFragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_express_history_detail,container,false);
        init(view);
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        return view;
    }

    /**
     * 静态工厂方法实例化fragment，保证所需bundle参数不会出错
     * @param expressInfo
     * @param sendOrReceive
     * @return
     */
    public static ExpressHistoryDetailFragment newInstance(ExpressInfo expressInfo,int sendOrReceive){
        ExpressHistoryDetailFragment detailFragment = new ExpressHistoryDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("expressinfo",expressInfo);
        bundle.putInt("sendorreceive",sendOrReceive);
        detailFragment.setArguments(bundle);

        return detailFragment;
    }

    private void init(View view){
        Bundle bundle = getArguments();
        ExpressInfo expressInfo = bundle.getParcelable("expressinfo");
        int sendOrReceive = bundle.getInt("sendorreceive");
        if(sendOrReceive == ExpressHistoryPresenterImpl.HISTORY_SEND){
            ((TextView)view.findViewById(R.id.top_bar_center_text)).setText("发件详情");
        }else{
            ((TextView)view.findViewById(R.id.top_bar_center_text)).setText("收件详情");
        }
        ((TextView)view.findViewById(R.id.express_detail_id)).setText(expressInfo.getID());
        ((TextView)view.findViewById(R.id.express_detail_send_name)).setText(expressInfo.getSname());
        ((TextView)view.findViewById(R.id.express_detail_send_tel)).setText(expressInfo.getStel());
        ((TextView)view.findViewById(R.id.express_detail_send_address)).setText(expressInfo.getSadd()+" "+expressInfo.getSaddinfo());
        ((TextView)view.findViewById(R.id.express_detail_receive_name)).setText(expressInfo.getRname());
        ((TextView)view.findViewById(R.id.express_detail_receive_tel)).setText(expressInfo.getRtel());
        ((TextView)view.findViewById(R.id.express_detail_receive_address)).setText(expressInfo.getRadd()+" "+expressInfo.getRaddinfo());
        ((TextView)view.findViewById(R.id.express_detail_get_time)).setText(expressInfo.getGetTime());
        ((TextView)view.findViewById(R.id.express_detail_out_time)).setText(expressInfo.getOutTime());
        ((TextView)view.findViewById(R.id.express_detail_weight)).setText(expressInfo.getWeight()+"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_bar_left_img:
                getFragmentManager().popBackStack();
                break;

        }
    }
}
