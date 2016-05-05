package extrace.sorter.ReceiverInfo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import extrace.model.ExpressInfo;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/5/3.
 */
public class ReceiverInfoFragment extends Fragment implements ReceiverInfoFragmentView,View.OnClickListener
{
    private ImageButton back;
    private TextView title;
    private TextView receiver_info_tel,receiver_info_name,receiver_info_add,receiver_info_addinfo;
   private Button receiver_info_send;
    private ReceiverInfoPresenter presenter;
    private static String ID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.receiver_info,container,false);
        back=(ImageButton)view.findViewById(R.id.top_bar_left_img);
        title=(TextView)view.findViewById(R.id.top_bar_center_text);
        title.setText("收件人地址");
        presenter=new ReceiverInfoPresenterImpl(getActivity(),this);
        receiver_info_add=(TextView)view.findViewById(R.id.receiver_info_add);
        receiver_info_addinfo=(TextView)view.findViewById(R.id.receiver_info_addinfo);
        receiver_info_name=(TextView)view.findViewById(R.id.receiver_info_name);
        receiver_info_tel=(TextView)view.findViewById(R.id.receiver_info_tel);
        receiver_info_send=(Button)view.findViewById(R.id.receiver_info_send);
        back.setOnClickListener(this);
        receiver_info_send.setOnClickListener(this);
        if(getArguments()!=null)
        {
            ExpressInfo expressInfo=(ExpressInfo) getArguments().getSerializable("expressinfo");
           receiver_info_add.setText(expressInfo.getRadd());
            receiver_info_addinfo.setText(expressInfo.getRaddinfo());
            receiver_info_name.setText(expressInfo.getRname());
            receiver_info_tel.setText(expressInfo.getRtel());
            ID=expressInfo.getID();
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.top_bar_left_img:
                getFragmentManager().popBackStack();
                break;
            case R.id.receiver_info_send:
                //拍照 完了签收成功会调用presenter
                presenter.ReceiveExpress(ID);
        }
    }

    @Override
    public void onFail(String errorMessage) {
        Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess() {
        Toast.makeText(getActivity(),"签收成功",Toast.LENGTH_LONG).show();
        receiver_info_send.setVisibility(View.INVISIBLE);
    }
}
