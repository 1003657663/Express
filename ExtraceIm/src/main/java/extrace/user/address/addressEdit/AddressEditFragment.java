package extrace.user.address.addressEdit;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import extrace.model.UserAddress;
import extrace.myelement.MyDialog;
import extrace.ui.main.R;

/**
 * Created by chao on 2016/4/19.
 * 编辑地址部分
 */
public class AddressEditFragment extends Fragment implements View.OnClickListener,AddressEditView,MyDialog.SureButton{
    UserAddress userAddress;
    EditText nameEdit,telephoneEdit,addressEdit;
    TextView provinceText,cityText,regionText;
    MyDialog myDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_address_edit,container,false);
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        setRightDelIcon(view);//设置右边的删除按钮
        ((TextView)view.findViewById(R.id.top_bar_center_text)).setText("修改地址");
        Bundle bundle = getArguments();//获取地址信息
        userAddress = bundle.getParcelable("userAddress");
        setAddressInfo(view);//设置初始信息
        myDialog = new MyDialog(getActivity());
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_bar_left_img:
                getFragmentManager().popBackStack();
                break;
            case R.id.top_bar_right_img:
                //删除当前地址,弹出警告框
                myDialog.showDialogWithSureAndNo("确认删除此地址？","确定","取消");
                break;
            default:
                break;
        }
    }

    @Override
    public void setRightDelIcon(View v) {
        ImageView delButton = (ImageView) v.findViewById(R.id.top_bar_right_img);
        delButton.setOnClickListener(this);
        delButton.setImageResource(R.mipmap.delete);
    }

    @Override
    public void setAddressInfo(View v) {
        nameEdit = (EditText) v.findViewById(R.id.user_address_edit_name);
        telephoneEdit = (EditText) v.findViewById(R.id.user_address_edit_telephone);
        addressEdit = (EditText) v.findViewById(R.id.user_address_edit_address);
        provinceText = (TextView) v.findViewById(R.id.user_address_edit_province);
        cityText = (TextView) v.findViewById(R.id.user_address_edit_city);
        regionText = (TextView) v.findViewById(R.id.user_address_edit_region);

        nameEdit.setText(userAddress.getName());
        telephoneEdit.setText(userAddress.getTelephone());
        addressEdit.setText(userAddress.getAddress());
        provinceText.setText(userAddress.getProvince());
        cityText.setText(userAddress.getCity());
        regionText.setText(userAddress.getRegion());
    }

    @Override
    public void sureButtonDo() {
        //删除地址代码---------
        myDialog.hideProgressDialog();
    }
}
