package extrace.user.address.addressEdit;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import extrace.ui.main.R;

/**
 * Created by chao on 2016/4/19.
 */
public class AddressEditFragment extends Fragment implements View.OnClickListener,AddressEditView{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_address_edit,container,false);
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        setRightDelIcon();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_bar_left_img:
                getFragmentManager().popBackStack();
                break;
            case R.id.top_bar_right_img:
                //删除当前地址
                break;

            default:
                break;
        }
    }

    @Override
    public void setRightDelIcon() {
        ImageView delButton = (ImageView) getView().findViewById(R.id.top_bar_right_img);
        delButton.setOnClickListener(this);
        delButton.setImageResource(R.mipmap.delete);
    }
}
