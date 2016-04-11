package extrace.loader;

import java.util.List;

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
//---chao---加载用户信息的loader
public class CustomerListLoader extends HttpAsyncTask {

//	private static final String PREFS_NAME = "ExTrace.cfg";
	String url;// = "http://192.168.7.100:8080/TestCxfHibernate/REST/Misc/";
	IDataAdapter<List<CustomerInfo>> adapter;
	private Activity context;
	
	public CustomerListLoader(IDataAdapter<List<CustomerInfo>> adpt, Activity context) {
		super(context);
		this.context = context;
		adapter = adpt;
		url = ((ExTraceApplication)context.getApplication()).getMiscServiceUrl();
//	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//	    url = prefs.getString("ServerUrl", "") + prefs.getString("MiscService", "");
	}
	
	@Override
	public void onDataReceive(String class_data, String json_data) {//---chao---服务器返回信息接收到并且从这里处理
		if(json_data.equals("Deleted")){
			//adapter.getData().remove(0);	//这个地方不好处理
			Toast.makeText(context, "客户信息已删除!", Toast.LENGTH_SHORT).show();
		}
		else{
			if(json_data == null || json_data.length() == 0)
			{
				Toast.makeText(context, "没有符合条件的客户信息!", Toast.LENGTH_SHORT).show();
				adapter.setData(null);
				adapter.notifyDataSetChanged();
			}
			else
			{
				List<CustomerInfo> cstm = JsonUtils.fromJson(json_data, new TypeToken<List<CustomerInfo>>(){});
				adapter.setData(cstm);
				adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		Log.i("onStatusNotify", "onStatusNotify: " + str_response);
	}
	
	public void LoadCustomerListByTelCode(String telCode)
	{
		url += "getCustomerListByTelCode/"+ telCode + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void LoadCustomerListByName(String name)
	{
		url += "getCustomerListByName/"+ name + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void DeleteCustomer(int id)
	{
		url += "deleteCustomerInfo/"+ id + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
