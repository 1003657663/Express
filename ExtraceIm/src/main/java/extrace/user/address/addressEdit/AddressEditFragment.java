package extrace.user.address.addressEdit;

import android.app.Fragment;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import extrace.main.MyApplication;
import extrace.model.UserAddress;
import extrace.model.address.City;
import extrace.model.address.Province;
import extrace.model.address.Region;
import extrace.myelement.MyDialog;
import extrace.ui.main.R;
import extrace.user.address.getaddressdata.GetAddressModelImpl;
import extrace.user.address.getaddressdata.GetAddressPresenter;

/**
 * Created by chao on 2016/4/19.
 * 编辑地址部分
 */
public class AddressEditFragment extends Fragment implements GetAddressPresenter,View.OnClickListener,AddressEditView,MyDialog.SureButton{
    UserAddress userAddress;
    EditText nameEdit,telephoneEdit,addressEdit;
    Spinner provinceSpinner, citySpinner, regionSpinner;
    MyDialog myDialog;
    MyApplication myApplication;
    GetAddressModelImpl getAddressModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_address_edit,container,false);
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        myApplication = (MyApplication) getActivity().getApplication();
        getAddressModel = new GetAddressModelImpl(getActivity(),this);

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

    /**
     * 设置右边的删除按钮的图片和样式
     * @param v
     */
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

        provinceSpinner = (Spinner) v.findViewById(R.id.user_address_spinner_province);
        citySpinner = (Spinner) v.findViewById(R.id.user_address_spinner_city);
        regionSpinner = (Spinner) v.findViewById(R.id.user_address_spinner_region);

        nameEdit.setText(userAddress.getName());
        telephoneEdit.setText(userAddress.getTelephone());
        addressEdit.setText(userAddress.getAddress());

        String[] prodefault = {userAddress.getProvince()};
        String[] citydefault = {userAddress.getCity()};
        String[] regiondefault = {userAddress.getRegion()};

        //设置每项的默认值
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,prodefault);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSpinner.setAdapter(arrayAdapter);
        arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,citydefault);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(arrayAdapter);
        arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,regiondefault);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(arrayAdapter);

        setListener();

        //获取省份地址信息
        //getAddressModel.getProvince();
    }

    /**
     * 设置spinner监听
     */
    private void setListener(){
        provinceSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAddressModel.getProvince();
                provinceSpinner.setOnClickListener(null);
            }
        });
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void sureButtonDo() {
        //删除地址代码---------
        myDialog.hideProgressDialog();
    }

    /**
     * 处理网络请求获得的地址信息
     * @param dataArray
     * @param whichGet
     */
    @Override
    public void onDataPresenterReceive(SparseArray<Object> dataArray,Integer whichGet) {
        String[] datas = new String[dataArray.size()];
        ArrayAdapter<String> arrayAdapter = null;
        switch (whichGet){
            case GetAddressModelImpl.GETPRO:
                for(int i=0;i<dataArray.size();i++){
                    Province province = (Province) dataArray.valueAt(i);
                    datas[i] = province.getPname();
                }
                arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,datas);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                provinceSpinner.setAdapter(arrayAdapter);
                break;
            case GetAddressModelImpl.GETCITY:
                for(int i=0;i<dataArray.size();i++){
                    City city = (City) dataArray.valueAt(i);
                    datas[i] = city.getName();
                }
                arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,datas);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySpinner.setAdapter(arrayAdapter);
                break;
            case GetAddressModelImpl.GETREGION:
                for(int i=0;i<dataArray.size();i++){
                    Region region = (Region) dataArray.valueAt(i);
                    datas[i] = region.getArea();
                }
                arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,datas);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                regionSpinner.setAdapter(arrayAdapter);
                break;
            default:
                break;
        }
    }
}
