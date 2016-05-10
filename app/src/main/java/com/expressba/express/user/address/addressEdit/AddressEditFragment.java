package com.expressba.express.user.address.addressEdit;

import android.app.Fragment;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.expressba.express.main.MyApplication;
import com.expressba.express.main.UIFragment;
import com.expressba.express.model.UserAddress;
import com.expressba.express.model.address.City;
import com.expressba.express.model.address.Province;
import com.expressba.express.model.address.Region;
import com.expressba.express.myelement.MyDialog;
import com.expressba.express.R;
import com.expressba.express.user.address.getaddressdata.GetAddressModelImpl;

/**
 * Created by chao on 2016/4/19.
 * 编辑地址部分
 */
public class AddressEditFragment extends UIFragment implements View.OnClickListener,AddressEditView,MyDialog.SureButton{
    public static final int ADDRESS_UPDATE_SEND = 0;//发货地址更新
    public static final int ADDRESS_UPDATE_RECEIVE = 1;//收货地址更新
    public static final int ADDRESS_NEW_SEND = 2;//新增发货地址更新
    public static final int ADDRESS_NEW_RECEIVE = 3;//新增收货地址更新
    public static final int GETPRO = 4;//获取省份
    public static final int GETCITY = 5;//获取城市
    public static final int GETREGION = 6;//获取区域
    public static final int ADDRESS_DELETE = 7;

    private int editWhat;//标志上面状态的标志位

    private UserAddress userAddress;
    private EditText nameEdit,telephoneEdit,addressEdit;
    private Spinner provinceSpinner, citySpinner, regionSpinner;
    private MyDialog myDialog;
    private Button setDefaultButton;
    private MyApplication myApplication;
    private GetAddressModelImpl getAddressModel;
    private SparseArray<Object> provinceSparseArray;//存储临时地址数据
    private SparseArray<Object> citySparseArray;
    private SparseArray<Object> regionSparseArray;

    private Integer provincePosition = 0;//存放用户选中地址所在spinner的position
    private Integer cityPosition = 0;
    private Integer regionPosition = 0;
    private Boolean isdefault = true;//判断用户是否修改了地址spinner

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_address_edit,container,false);
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        view.findViewById(R.id.user_address_edit_submit_button).setOnClickListener(this);
        setDefaultButton = (Button) view.findViewById(R.id.user_address_setdefault);
        setDefaultButton.setOnClickListener(this);
        myApplication = (MyApplication) getActivity().getApplication();
        getAddressModel = new GetAddressModelImpl(getActivity(),this);

        Bundle bundle = getArguments();//获取地址信息
        userAddress = bundle.getParcelable("userAddress");
        editWhat = bundle.getInt("editWhat");

        init(view);

        setAddressInfo(view);//设置初始信息
        myDialog = new MyDialog(getActivity());
        myDialog.setSureButton(this);
        return view;
    }

    private void init(View view){
        //判断如果是新增地址，那么设置标题，同时默认加载北京市信息
        if(userAddress == null) {
            if(editWhat == ADDRESS_NEW_RECEIVE) {
                ((TextView) view.findViewById(R.id.top_bar_center_text)).setText("新增收货地址");
            }else if(editWhat == ADDRESS_NEW_SEND){
                ((TextView) view.findViewById(R.id.top_bar_center_text)).setText("新增发货地址");
            }
            //设置初始省份地址，北京，初始字段设置
            userAddress = new UserAddress();
            userAddress.setRank(1);
            userAddress.setCustomerid(myApplication.getUserInfo().getId());
            userAddress.setProvince("北京市");
            //-------!!!
            userAddress.setCustomerid(myApplication.getUserInfo().getId());
        }else {
            if(editWhat == ADDRESS_UPDATE_RECEIVE) {
                ((TextView) view.findViewById(R.id.top_bar_center_text)).setText("修改收货地址");
            }else {
                ((TextView) view.findViewById(R.id.top_bar_center_text)).setText("修改发货地址");
            }
            setRightDelIcon(view);//如果不是新增地址，那么设置右边的删除按钮
            preRank = userAddress.getRank();//存储rank数据
            if(preRank == 0){
                setDefaultButton.setText("默认");
            }else {
                setDefaultButton.setText("设为默认地址");
            }
        }
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
            case R.id.user_address_edit_submit_button:
                //提交修改的地址
                submitChangeAddress();
                break;
            case R.id.user_address_setdefault:
                setDefault();
                break;
            default:
                break;
        }
    }

    /**
     * 设为默认 按钮 被点击后调用
     */
    private int preRank;
    private void setDefault(){
        MyDialog myDialog = new MyDialog(getActivity());
        if(preRank == 0){
            myDialog.showDialogWithSure("已经是默认地址，您可以选择其它地址设为默认地址","好的");
        }else{
            if(userAddress.getRank() == 0){
                setDefaultButton.setText("设为默认地址");
                userAddress.setRank(1);
            }else {
                setDefaultButton.setText("默认");
                userAddress.setRank(0);
            }
        }
    }
    /**
     * 提交新地址
     */
    private void submitChangeAddress(){
        UserAddress userAddress = new UserAddress();
        userAddress.setName(nameEdit.getText().toString());
        userAddress.setTelephone(telephoneEdit.getText().toString());
        userAddress.setRank(this.userAddress.getRank());
        userAddress.setAid(this.userAddress.getAid());
        userAddress.setCustomerid(this.userAddress.getCustomerid());
        userAddress.setAddress(addressEdit.getText().toString());
        userAddress.setProvinceid(((Province)provinceSparseArray.valueAt(provincePosition)).getPid());
        userAddress.setCityid(((City)citySparseArray.valueAt(cityPosition)).getCid());
        userAddress.setRegionid(((Region)regionSparseArray.valueAt(regionPosition)).getId());

        if(editWhat == ADDRESS_UPDATE_SEND){
            getAddressModel.submitUpdateSendAddress(userAddress);
        }else if(editWhat == ADDRESS_UPDATE_RECEIVE){
            getAddressModel.submitUpdateReceiveAddress(userAddress);
        }else if(editWhat == ADDRESS_NEW_SEND){
            getAddressModel.submitNewSendAddress(userAddress);
        }else if(editWhat == ADDRESS_NEW_RECEIVE){
            getAddressModel.submitNewReceiveAddress(userAddress);
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

        //获取省份地址信息
        getAddressModel.getProvince();
        //设置开始监听事件
        setFirstListener();
    }

    /**
     * 设置spinner监听
     */
    private void setListener(Integer whichListener){
        switch (whichListener) {
            case GETPRO:
                provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        getAddressModel.getCityByPro(((Province) provinceSparseArray.valueAt(position)).getPid());
                        provincePosition = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
            case GETCITY:
                citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        getAddressModel.getRegionByCity(((City) citySparseArray.valueAt(position)).getCid());
                        cityPosition = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
            case GETREGION:
                regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        regionPosition = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
            default:
                break;
        }
    }

    private void setFirstListener(){
        provinceSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isdefault = false;
                provinceSpinner.setOnTouchListener(null);
                return false;
            }
        });
        citySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isdefault = false;
                citySpinner.setOnTouchListener(null);
                return false;
            }
        });
        regionSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isdefault = false;
                regionSpinner.setOnTouchListener(null);
                return false;
            }
        });
        setListener(GETREGION);//设置区域部分的监听函数
    }

    /**
     * 点击确认删除按钮执行删除操作
     */
    @Override
    public void sureButtonDo() {
        //删除地址代码---------
        getAddressModel.addressDelete(userAddress);
        myDialog.hideProgressDialog();
    }

    /**
     * 处理网络请求获得的地址信息
     * @param dataArray
     * @param whichGet
     */
    @Override
    public void onDataReceive(SparseArray<Object> dataArray,Integer whichGet) {
        String[] datas = new String[dataArray.size()];
        ArrayAdapter<String> arrayAdapter = null;
        switch (whichGet){
            case GETPRO:
                provinceSparseArray = dataArray;
                for(int i=0;i<dataArray.size();i++){
                    Province province = (Province) dataArray.valueAt(i);
                    datas[i] = province.getPname();
                    if(isdefault) {
                        if (datas[i].equals(userAddress.getProvince())) {
                            provincePosition = i;
                        }
                    }
                }
                arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,datas);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                provinceSpinner.setAdapter(arrayAdapter);
                if(isdefault) {
                    provinceSpinner.setSelection(provincePosition);
                }
                setListener(whichGet);
                break;
            case GETCITY:
                citySparseArray = dataArray;
                for(int i=0;i<dataArray.size();i++){
                    City city = (City) dataArray.valueAt(i);
                    datas[i] = city.getCname();
                    if(isdefault) {
                        if (datas[i].equals(userAddress.getCity())) {
                            cityPosition = i;
                        }
                    }
                }
                arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,datas);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySpinner.setAdapter(arrayAdapter);
                if(isdefault) {
                    citySpinner.setSelection(cityPosition);
                }
                setListener(whichGet);
                break;
            case GETREGION:
                regionSparseArray = dataArray;
                for(int i=0;i<dataArray.size();i++){
                    Region region = (Region) dataArray.valueAt(i);
                    datas[i] = region.getArea();
                    if(isdefault) {
                        if (datas[i].equals(userAddress.getRegion())) {
                            regionPosition = i;
                        }
                    }
                }
                arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,datas);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                regionSpinner.setAdapter(arrayAdapter);
                if(isdefault) {
                    regionSpinner.setSelection(regionPosition);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取数据成功后回调
     */
    @Override
    public void onSubmitSuccess() {
        Toast.makeText(getActivity(),"提交成功",Toast.LENGTH_SHORT).show();
        getFragmentManager().popBackStack();
    }

    @Override
    public void onDeleteSuccess() {
        Toast.makeText(getActivity(),"删除地址成功",Toast.LENGTH_SHORT).show();
        getFragmentManager().popBackStack();
    }
}
