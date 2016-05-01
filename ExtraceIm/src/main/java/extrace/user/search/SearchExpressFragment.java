package extrace.user.search;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import extrace.ui.main.R;

/**
 * Created by songchao on 16/5/1.
 */
public class SearchExpressFragment extends Fragment implements View.OnClickListener,SearchExpressView{

    private String searchID;
    private TextView searchText;
    private ImageView leftArrow;
    private LinearLayout searchContain;

    private SearchExpressPresenter expressPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_express_search,container,false);
        searchText = (TextView) view.findViewById(R.id.express_search_id);
        leftArrow = (ImageView) view.findViewById(R.id.express_search_left_image);
        searchContain = (LinearLayout) view.findViewById(R.id.express_search_containt);

        expressPresenter = new SearchExpressPresenterImpl(getActivity(),this);

        getBundle();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    /**
     * 获取bundle并且读取其中的信息
     */
    @Override
    public void getBundle() {
        Bundle bundle = getArguments();
        searchID = bundle.getString("searchID");//获取运单号码
    }

    @Override
    public void init() {
        searchText.setText(searchID);
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取相应id的快递的信息
     */
    @Override
    public void getSearchInfo() {
        expressPresenter.startGetExpressInfo(searchID);
    }

    /**
     * 请求服务器返回的数据
     */
    @Override
    public void onRequestSuccess() {

    }

    private void arrowAnimation(){

    }
}
