package extrace.sorter.close.add_package_list;
import android.app.Activity;
import org.json.JSONException;
import org.json.JSONObject;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/30.
 */
public class AddPackageListPresenterImpl extends VolleyHelper implements AddPackageListPresenter
{
    private AddPackageListFragmentView add_packageListFragmentView;
    String url="";
    public AddPackageListPresenterImpl(Activity activity, AddPackageListFragmentView AddPackageListFragmentView)
    {
        super(activity);
        this.add_packageListFragmentView = AddPackageListFragmentView;
       // url=activity.getResources().getString(R.string.base_url)+activity.getResources().getString(R.string.);
    }

    @Override
    public void loadIntoPackage(String packageID,String ID,int isPackage) {
        url+="REST/Domain/loadIntoPackage/packageId/"+packageID+"/id/"+ID+"/isPackage/"+isPackage;
        JSONObject object=new JSONObject();
        try {
            doJson(url,VolleyHelper.GET,object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataReceive(Object jsonOrArray)
    {
        add_packageListFragmentView.Success();
    }

    @Override
    public void onError(String errorMessage) {
        add_packageListFragmentView.Fail(errorMessage);
    }

    public void onOpen(String packageID)
    {
        url+=packageID;
        try {
            doJson(url,VolleyHelper.GET,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}