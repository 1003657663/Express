package extrace.user.address;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import extrace.ui.main.R;
import extrace.user.address.addressEdit.AddressEditFragment;

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
        ((TextView)view.findViewById(R.id.top_bar_center_text)).setText("管理地址信息");
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
            default:
                break;
        }
    }

    @Override
    public void addAddress(String name, final String tel, String address, boolean isDefault) {
        ViewGroup viewGroup = (ViewGroup) getView();
        final RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.user_address_one,viewGroup,false);
        ((TextView)relativeLayout.findViewById(R.id.user_name_textView)).setText(name);
        ((TextView)relativeLayout.findViewById(R.id.user_tel_textView)).setText(tel);
        ((TextView)relativeLayout.findViewById(R.id.user_address_textView)).setText(address);
        TextView defaultTextView = (TextView) relativeLayout.findViewById(R.id.user_address_isDefault);
        if(!isDefault){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0,0);
            params.setMargins(0,0,0,0);
            defaultTextView.setLayoutParams(params);
            defaultTextView.setPadding(0,0,0,0);
            defaultTextView.setText("");
        }
        relativeLayout.findViewById(R.id.edit_address_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout thisLayout = (RelativeLayout) v.getParent();
                String name = ((TextView) thisLayout.findViewById(R.id.user_name_textView)).getText().toString();
                String telephone = ((TextView)thisLayout.findViewById(R.id.user_tel_textView)).getText().toString();
                toEditFragment(name,telephone);
            }
        });
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

    @Override
    public void toEditFragment(String name,String telephone) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        bundle.putString("telephone",telephone);
        AddressEditFragment addressEditFragment = new AddressEditFragment();
        addressEditFragment.setArguments(bundle);
        ft.addToBackStack("address");
        ft.replace(R.id.fragment_container_layout,addressEditFragment).commit();
    }
}
