package extrace.user.address;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import extrace.ui.main.R;

/**
 * Created by chao on 2016/4/17.
 */
public class AddressFragment extends Fragment implements AddressView,View.OnClickListener {
    AddressPresenter presenter;
    LayoutInflater inflater;
    ViewGroup viewGroup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_address_fragment,container,false);
        this.inflater = inflater;
        this.viewGroup = container;
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        presenter = new AddressPresenterImpl(getActivity(),this);
        presenter.getAddress();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_bar_left_img:
                getFragmentManager().popBackStack();
                break;
            case R.id.edit_address_button:
                presenter.getAddress();
                break;
            default:
                break;
        }
    }

    @Override
    public void addAddress(String name, String tel, String address, boolean isDefault) {
        ViewGroup viewGroup = (ViewGroup) getView();
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.one_address,viewGroup,false);
        ((TextView)relativeLayout.findViewById(R.id.user_name_textView)).setText(name);
        ((TextView)relativeLayout.findViewById(R.id.user_tel_textView)).setText(tel);
        ((TextView)relativeLayout.findViewById(R.id.user_address_textView)).setText(address);
        relativeLayout.findViewById(R.id.edit_address_button).setOnClickListener(this);
        TextView defaultTextView = (TextView) relativeLayout.findViewById(R.id.user_address_isDefault);
        if(!isDefault){
            relativeLayout.removeView(defaultTextView);
        }

        if(viewGroup!=null) {
            viewGroup.addView(relativeLayout);
        }else {
            System.err.println("addressFragment getView to viewGroup is Error");
        }
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_LONG).show();
    }
}
