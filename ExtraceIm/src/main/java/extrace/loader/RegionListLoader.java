package extrace.loader;

import java.util.List;

import android.app.Activity;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import extrace.misc.model.CodeNamePair;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam.RETURN_STATUS;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;

public class RegionListLoader extends HttpAsyncTask{

	String url;
	IDataAdapter<List<CodeNamePair>> adapter;
	
	public RegionListLoader(IDataAdapter<List<CodeNamePair>> adpt, Activity context) {
		super(context);
		adapter = adpt;
		url = ((ExTraceApplication)context.getApplication()).getMiscServiceUrl();
	}

	@Override
	public void onDataReceive(String class_data, String json_data) {
		List<CodeNamePair> rg = JsonUtils.fromJson(json_data, new TypeToken<List<CodeNamePair>>(){});
		adapter.setData(rg);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		// TODO Auto-generated method stub
		Log.i("onStatusNotify", "onStatusNotify: " + str_response);
	}
	
	public void LoadProvinceList()
	{
		url += "getProvinceList?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void LoadCityList(String rCode)
	{
		url += "getCityList/"+ rCode.substring(0, 2) + "0000?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void LoadTownList(String rCode)
	{
		url += "getTownList/"+ rCode.substring(0, 4) +"00?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
