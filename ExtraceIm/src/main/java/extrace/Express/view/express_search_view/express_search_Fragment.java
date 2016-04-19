package extrace.Express.view.express_search_view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.ListView;

import android.widget.Toast;
import extrace.Express.presenter.expressListPresenter.expressListPresenter;

import extrace.model.ExpressSheet;
import extrace.net.IDataAdapter;
import extrace.ui.main.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 黎明 on 2016/4/17.
 */
public class express_search_Fragment extends ListFragment implements express_search_FragmentView,IDataAdapter<List<ExpressSheet>>
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

   private expressListPresenter expressListPresenter;
   IDataAdapter<List<ExpressSheet>> adapter;
    express_search_adapter adp;
    List<ExpressSheet> list;
    private ListView expresslist;
    private ImageButton back,enter;
    private EditText text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.express_search,container,false);
        expresslist=(ListView)view.findViewById(android.R.id.list);
        back=(ImageButton)view.findViewById(R.id.back);
        enter=(ImageButton)view.findViewById(R.id.enter);
        text=(EditText)view.findViewById(R.id.index_top_bar_input);
        list=new ArrayList<ExpressSheet>();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        if(text.getText()!=null) {
            enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expressListPresenter.doSearchExpress(text.getText().toString());
                    list = adapter.getData();
                  /* ExpressSheet expressSheet=new ExpressSheet();
                    expressSheet.setType(1);
                    expressSheet.setID("123");
                    expressSheet.setStatus(1);
                    list.add(expressSheet);
                    ExpressSheet expressSheet2=new ExpressSheet();
                    expressSheet2.setType(1);
                    expressSheet2.setID("234");
                    expressSheet2.setStatus(2);
                    list.add(expressSheet2);
                    */
                    adp=new express_search_adapter(getActivity(),list);
                   // setListAdapter(adp);
                    //new出显示list的adapter
                   // setListAdapter(adp);
                    expresslist.setAdapter(adp);
                }

            });
        }
        if(getArguments()!=null)
        {
            //从上个页面传参ID
            String ID=getArguments().getString("ID");
            //获得ID 调用presenter通过presenter调用model 实现根据ID查询快件 并将list放入adapter
            expressListPresenter.doSearchExpress(ID);
            list=adapter.getData();
            adp=new express_search_adapter(getActivity(),list);
            // setListAdapter(adp);
            //new出显示list的adapter
            // setListAdapter(adp);
            expresslist.setAdapter(adp);
        }
       // expressListPresenter=new expressListPresenterImpl(this,adapter);

        return view;
    }
    @Override
    public Activity getTheActivity() {
        return getActivity();
    }

    @Override
    public void onToastFail() {
        Toast.makeText(getActivity(), "sorry,search fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onToastSuccess() {

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
                 super.onListItemClick(l, v, position, id);
                Fragment fragment = new ExpressFragment();
                ExpressSheet expressSheet=(ExpressSheet) adp.getItem(position);
                Bundle bundle=new Bundle();
                bundle.putString("ID",expressSheet.getID());
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                //transaction.remove(express_search_Fragment.this);
                transaction.replace(android.R.id.list,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

    }

