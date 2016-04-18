package extrace.Express.view.express_search_view;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;
import extrace.Express.presenter.expressListPresenter.expressListPresenter;
import extrace.Express.presenter.expressListPresenter.expressListPresenterImpl;
import extrace.model.ExpressSheet;
import extrace.net.IDataAdapter;
import extrace.ui.main.R;

import java.util.List;


/**
 * Created by 黎明 on 2016/4/17.
 */
public class express_search_Fragment extends Fragment implements express_search_FragmentView,IDataAdapter<List<ExpressSheet>>
{
    @Override
    public List<ExpressSheet> getData() {
        return list;
    }
    @Override
    public void setData(List<ExpressSheet> data) {
    list=data;
    }
    @Override
    public void notifyDataSetChanged() {

    }
    expressListPresenter expressListPresenter;
   IDataAdapter<List<ExpressSheet>> adapter;
    express_search_adapter adp;
    List<ExpressSheet> list;
    private ListView expresslist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.express_search,container,false);
        expressListPresenter=new expressListPresenterImpl(this,adapter);
        expresslist=(ListView)view.findViewById(R.id.list);
        if(getArguments()!=null)
        {
            //从上个页面传参ID
            String ID=getArguments().getString("ID");
            //获得ID 调用presenter通过presenter调用model 实现根据ID查询快件 并将list放入adapter
            expressListPresenter.doSearchExpress(ID);
            adp=new express_search_adapter(getActivity(),list);
            //new出显示list的adapter
            expresslist.setAdapter(adp);
            //加载适配器
        }
        return view;
    }
    @Override
    public Activity getTheActivity() {
        return getActivity();
    }

    @Override
    public void onToastFail() {
        Toast.makeText(getActivity(), "sorry,submit fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onToastSuccess() {
        Toast.makeText(getActivity(), "submit success", Toast.LENGTH_LONG).show();}
}
