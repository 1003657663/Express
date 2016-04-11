package extrace.loader;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import extrace.misc.model.CustomerInfo;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam.RETURN_STATUS;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;

public class CustomerEditLoader  extends HttpAsyncTask{

	String url;
	IDataAdapter<CustomerInfo> adapter;
	private Activity context;

	public CustomerEditLoader(IDataAdapter<CustomerInfo> adpt, Activity context) {
		super(context);
		this.context = context;
		adapter = adpt;
		url = ((ExTraceApplication)context.getApplication()).getMiscServiceUrl();
	}

	@Override
	public void onDataReceive(String class_data, String json_data) {
		if(class_data.equals("CustomerInfo"))
		{
			CustomerInfo ci = JsonUtils.fromJson(json_data, new TypeToken<CustomerInfo>(){});
			adapter.setData(ci);
			adapter.notifyDataSetChanged();
		}
		else if(class_data.equals("R_CustomerInfo"))		//保存完成
		{
			CustomerInfo ci = JsonUtils.fromJson(json_data, new TypeToken<CustomerInfo>(){});
			adapter.getData().setID(ci.getID());
			adapter.getData().onSave();
			adapter.notifyDataSetChanged();
			Toast.makeText(context, "客户信息保存完成!", Toast.LENGTH_SHORT).show();
		}
		else
		{
		}
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		Log.i("onStatusNotify", "onStatusNotify: " + str_response);
	}

	public void LoadCustomer(int id)
	{
		url += "getCustomerInfo/"+ id + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void SaveCustomer(int id, String name, String rgCode, String addr, String dpt, int pCode, String telCode)
	{
		CustomerInfo cstm = new CustomerInfo();
		
		cstm.setID(id);
		cstm.setName(name);
		cstm.setRegionCode(rgCode);
		cstm.setAddress(addr);
		cstm.setDepartment(dpt);
		cstm.setPostCode(pCode);
		cstm.setTelCode(telCode);

		SaveCustomer(cstm);
	}

	public void SaveCustomer(CustomerInfo cstm)
	{
		String jsonObj = JsonUtils.toJson(cstm, true);
		url += "saveCustomerInfo";
		try {
			execute(url, "POST", jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
