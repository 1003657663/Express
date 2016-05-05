package extrace.sorter.SorterIndex;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import extrace.model.PackageInfo;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/5/4.
 */
public class SorterIndexPresenterImpl extends VolleyHelper implements SorterIndexPresenter {
   private SorterIndexFragmentView fragmentview;
    String url,turl;
    public SorterIndexPresenterImpl(Activity activity, SorterIndexFragmentView sorterindexfragmentView)
    {
        super(activity);
        this.fragmentview=sorterindexfragmentView;
        //turl="http://192.168.1.113:8080"+activity.getResources().getString(R.string.createPackage);
        turl=activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.createPackage);
        url=turl;
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject=(JSONObject)jsonOrArray;
        PackageInfo packageInfo=new PackageInfo();
        try {
            packageInfo.setCloseTime(jsonObject.getString("closetime"));
            packageInfo.setId(jsonObject.getString("ID"));
            fragmentview.onSuccess(packageInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            url=turl;
        }
    }

    @Override
    public void onError(String errorMessage) {
        fragmentview.onError(errorMessage);
        url=turl;
    }

    @Override
    public void CreatPackage(int fromID, int toID, int EmployeesID, int isSorter) {
          url+="fromID/"+fromID+"/toID/"+toID+"/employeesID/"+EmployeesID+"/isSorter/"+isSorter;
        try {
            JSONObject object=new JSONObject();
            doJson(url,VolleyHelper.GET,object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
