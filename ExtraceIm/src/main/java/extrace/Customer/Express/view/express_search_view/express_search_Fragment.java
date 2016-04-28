package extrace.Customer.Express.view.express_search_view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.ListView;

import android.widget.Toast;

import extrace.model.ExpressInfo;
import extrace.Customer.Express.presenter.express_search_presenter.expressListPresenter;

import extrace.Customer.Express.presenter.express_search_presenter.expressListPresenterImpl;
import extrace.Customer.Express.view.express_info_view.express_info_fragment;
import extrace.model.ExpressSheet;
import extrace.ui.main.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 黎明 on 2016/4/17.
 * 快件查询：由快件单号或者电话号码得到express的list或express
 */
public class express_search_Fragment extends ListFragment implements express_search_FragmentView
{
   private expressListPresenter expressListPresenter;
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
        list=new ArrayList<>();
        expressListPresenter=new expressListPresenterImpl(this);
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
                    expressListPresenter.doSearchByID(text.getText().toString());
                }
            });
        }
        if(getArguments()!=null)
        {
            if(getArguments().getString("ID")!=null) {
                String ID = getArguments().getString("ID");
                expressListPresenter.doSearchByID(ID);
            }
            //从上个页面传参ID
          else if (getArguments().getString("CID")!=null) {
                String CID = getArguments().getString("CID");
                expressListPresenter.doSearchByCID(CID);
            }
            else if (getArguments().getString("Tel")!=null) {
                String Tel = getArguments().getString("Tel");
                expressListPresenter.doSearchByTel(Tel);
            }
            //获得ID 调用presenter通过presenter调用model 实现根据ID查询快件 并将list放入adapter
           // Toast.makeText(getActivity(),ID,Toast.LENGTH_LONG).show();

        }
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
    public void onToastSuccess(List<ExpressInfo> list)
    {
        if(list.size()>1)
        {adp=new express_search_adapter(getActivity(),list);
        expresslist.setAdapter(adp);}
        else
        {
            Fragment fragment = new express_info_fragment();
            Bundle bundle = new Bundle();
            bundle.putString("ID",list.get(0).getID());
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_layout, fragment);
            transaction.addToBackStack("search");
            transaction.commit();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
                 super.onListItemClick(l, v, position, id);
                Fragment fragment = new express_info_fragment();
                ExpressInfo expressInfo=(ExpressInfo)adp.getItem(position);
                Bundle bundle=new Bundle();
                bundle.putString("ID",expressInfo.getID());
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_layout,fragment);
                transaction.addToBackStack("search");
                transaction.commit();
            }

    }

