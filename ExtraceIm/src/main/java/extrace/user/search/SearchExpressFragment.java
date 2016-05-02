package extrace.user.search;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import extrace.ui.main.R;

/**
 * Created by songchao on 16/5/1.
 */
public class SearchExpressFragment extends Fragment implements View.OnClickListener,SearchExpressView{

    private String searchID;
    private TextView searchText;
    private ImageView leftArrow;
    private LinearLayout searchContain;
    private RelativeLayout leftContain;
    private Text topbarTitle;
    private ImageView topbarRight;

    private SearchExpressPresenter expressPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_express_search,container,false);
        searchText = (TextView) view.findViewById(R.id.express_search_id);
        leftArrow = (ImageView) view.findViewById(R.id.express_search_left_image);
        leftContain = (RelativeLayout) view.findViewById(R.id.express_search_arrow);
        searchContain = (LinearLayout) view.findViewById(R.id.express_search_containt);
        topbarTitle = (Text) view.findViewById(R.id.top_bar_center_text);
        topbarRight = (ImageView) view.findViewById(R.id.top_bar_right_img);
        topbarRight.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.map));

        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        expressPresenter = new SearchExpressPresenterImpl(getActivity(),this);

        getBundle();

        onRequestSuccess();
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_bar_left_img:
                getFragmentManager().popBackStack();
                break;
            case R.id.top_bar_right_img:
                break;
            case R.id.top_bar_right_text:
                break;
        }
    }

    /**
     * 获取bundle并且读取其中的信息
     */
    @Override
    public void getBundle() {
        Bundle bundle = getArguments();
        if(bundle!=null) {
            searchID = bundle.getString("searchID");//获取运单号码
        }
    }

    @Override
    public void init() {
        searchText.setText(searchID);
        topbarTitle.setTextContent(searchID+"物流信息");
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
        int i=10;
        for(i = 10;i>0;i--) {//循环读取每一条数据
            RelativeLayout relativeLayout = (RelativeLayout) LinearLayout.inflate(getActivity(), R.layout.user_express_search_item, null);
            TextView pointText = (TextView) relativeLayout.findViewById(R.id.express_address_point);
            TextView timeText = (TextView) relativeLayout.findViewById(R.id.express_point_date);



            searchContain.addView(relativeLayout);
        }

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Thread.sleep(500);
                    publishProgress();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                int height = leftContain.getMeasuredHeight();
                startAni(height);
            }
        };
        asyncTask.execute();
    }

    /**
     * 开始箭头动画
     */
    private void startAni(int height){
        LeftArrow leftArrowNew = new LeftArrow(leftArrow);
        ObjectAnimator.ofInt(leftArrowNew,"height",height).setDuration(2000).start();

    }

    /**
     * 重写一个有getter和setter的类，实现动画效果
     */
    class LeftArrow{
        ImageView leftArrow;
        LeftArrow(ImageView leftArrow){
            this.leftArrow = leftArrow;
        }
        public int getHeight(){
            return leftArrow.getLayoutParams().height;
            //return leftArrow.getMeasuredHeight();
        }
        public void setHeight(int height){
            leftArrow.getLayoutParams().height = height;
            leftArrow.requestLayout();
        }
    }

    private void arrowAnimation(){

    }
}
