package extrace.user.address;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.HashMap;

import extrace.model.UserAddress;
import extrace.ui.main.R;

/**
 * 用户地址部分的适配器
 * Created by songchao on 16/4/24.
 */
public class AddressListApapter extends BaseAdapter{

    Context context;
    HashMap<Integer,UserAddress> addressMap;
    AddressFragment addressFragment;
    public AddressListApapter(AddressFragment addressFragment, HashMap<Integer,UserAddress> addressMap){
        this.context = addressFragment.getActivity();
        this.addressMap = addressMap;
        this.addressFragment = addressFragment;
    }

    @Override
    public int getCount() {
        return addressMap.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public static class ViewHolder{
        public TextView nameText;
        public TextView telephoneText;
        public TextView provinceText;
        public TextView cityText;
        public TextView areaText;
        public TextView isDefaultText;
        public TextView addressText;
        public ImageView editButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.user_address_one,null);
            viewHolder.nameText = (TextView) convertView.findViewById(R.id.user_name_textView);
            viewHolder.telephoneText = (TextView) convertView.findViewById(R.id.user_tel_textView);
            viewHolder.provinceText = (TextView) convertView.findViewById(R.id.user_province_text);
            viewHolder.cityText = (TextView) convertView.findViewById(R.id.user_city_text);
            viewHolder.areaText = (TextView) convertView.findViewById(R.id.user_area_text);
            viewHolder.isDefaultText = (TextView) convertView.findViewById(R.id.user_address_isDefault);
            viewHolder.addressText = (TextView) convertView.findViewById(R.id.user_address_textView);
            viewHolder.editButton = (ImageView) convertView.findViewById(R.id.edit_address_button);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        System.err.println("position:"+position);
        final UserAddress userAddress = addressMap.get(position);
        viewHolder.nameText.setText(userAddress.getName());
        viewHolder.telephoneText.setText(userAddress.getTelephone());
        viewHolder.provinceText.setText(userAddress.getProvince());
        viewHolder.cityText.setText(userAddress.getCity());
        viewHolder.areaText.setText(userAddress.getRegion());
        viewHolder.addressText.setText(userAddress.getAddress());

        viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressFragment.toEditFragment(userAddress);
            }
        });
        if(userAddress.getRank()==0){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0,0);
            params.setMargins(0,0,0,0);
            viewHolder.isDefaultText.setLayoutParams(params);
            viewHolder.isDefaultText.setPadding(0,0,0,0);
            viewHolder.isDefaultText.setText("");
        }
        return convertView;
    }
}