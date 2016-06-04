package com.expressba.expressuser.user.search;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.expressba.expressuser.main.UIFragment;
import com.expressba.expressuser.map.MyBaiduMapFragment;
import com.expressba.expressuser.model.ExpressSearchInfo;
import com.expressba.expressuser.R;
import com.expressba.expressuser.myelement.MyDialog;
import com.expressba.expressuser.myelement.MyFragmentManager;

/**
 * Created by songchao on 16/5/1.
 *
 */
public class SearchExpressFragment extends UIFragment implements View.OnClickListener,SearchExpressView{

    //1 未揽收,2 揽收,3 派送,4 寄送,5 签收
    private final int NO_TAKE = 1;
    private final int HAS_TAKEv= 2;
    private final int IS_SEND = 3;
    private final int HAS_RECEIVE = 5;

    public static int QIAN_SHOW = 10;
    public static int LAN_SHOW = 11;



    private String searchID;
    private TextView searchText;
    private ImageView leftArrow;
    private LinearLayout searchContain;
    private RelativeLayout leftContain;

    private SearchExpressPresenter expressPresenter;
    private MyDialog myDialog;
    private int nowState;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.user_express_search,container,false);

        searchText = (TextView) view.findViewById(R.id.express_search_result_id);
        leftArrow = (ImageView) view.findViewById(R.id.express_search_left_image);
        leftContain = (RelativeLayout) view.findViewById(R.id.express_search_arrow);
        searchContain = (LinearLayout) view.findViewById(R.id.express_search_containt);

        view.findViewById(R.id.express_search_result_tomap).setOnClickListener(this);
        expressPresenter = new SearchExpressPresenterImpl(getActivity(),this);
        myDialog = new MyDialog(getActivity());
        return view;
    }

    @Override
    protected void onBack() {
        MyFragmentManager.popFragment(getClass(),null,null,getFragmentManager());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.express_search_result_tomap:
                toBaiduMap();
                break;
        }
    }



    /**
     * 跳转到百度地图位置实时监测
     */
    private void toBaiduMap(){
        Bundle bundle = new Bundle();
        bundle.putInt("nowstate",nowState);
        MyFragmentManager.turnBaiduFragment(SearchMainFragment.class, MyBaiduMapFragment.class,null,searchID,getFragmentManager());
    }

    /**
     * 获取bundle并且读取其中的信息
     */
    @Override
    protected void handlerIfBundle(Bundle bundle) {
        super.handlerIfBundle(bundle);
        searchID = bundle.getString("searchID");//获取运单号码
    }

    @Override
    protected void handlerEveryInit() {
        super.handlerEveryInit();
        if(searchID!=null) {
            searchText.setText(searchID);
            getSearchInfo();
        }
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
     * 揽收图片部分没有添加，待签收部分测试之后再说
     */
    @Override
    public void onRequestSuccess(ArrayList<ExpressSearchInfo> expressSearchInfos) {
        int size = expressSearchInfos.size();
        for(int i=size-1;i>=0;i--) {//循环读取每一条数据
            RelativeLayout relativeLayout = (RelativeLayout) LinearLayout.inflate(getActivity(), R.layout.user_express_search_item, null);
            TextView pointText = (TextView) relativeLayout.findViewById(R.id.express_address_point);
            TextView timeText = (TextView) relativeLayout.findViewById(R.id.express_point_date);

            ExpressSearchInfo expressSearchInfo = expressSearchInfos.get(i);
            if(i==size-1){
                nowState = expressSearchInfo.getState();
            }
            //判断如果是已经签收，那么提示可以点击看签收图片
            if(expressSearchInfo.getState() == HAS_RECEIVE){
                String text = expressSearchInfo.getInfo();
                text = text+"点击查看签收图片";
                SpannableString spannableString = new SpannableString(text);
                spannableString.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        getExpressImage(QIAN_SHOW);
                    }
                },text.length()-8,text.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                pointText.setText(spannableString);
            }else {
                pointText.setText(expressSearchInfo.getInfo());
            }
            timeText.setText(expressSearchInfo.getTime());
            if(i==size-1) {
                pointText.setTextColor(getResources().getColor(R.color.black));
                timeText.setTextColor(getResources().getColor(R.color.black));
            }
            searchContain.addView(relativeLayout);
        }

        initAni().execute();//初始化箭头动画的异步类并启动
    }


    private void getExpressImage(int whichImage){
        if(whichImage == LAN_SHOW) {
            expressPresenter.startGetExpressImage(searchID, LAN_SHOW);
        }else{
            expressPresenter.startGetExpressImage(searchID, QIAN_SHOW);
        }
    }


    @Override
    public void onGetExpressImageSuccess(int whichImage,Bitmap bitmap) {
        if(whichImage == LAN_SHOW){
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageBitmap(bitmap);
            myDialog.showViewDialog(imageView, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }else{
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageBitmap(bitmap);
            myDialog.showViewDialog(imageView, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }
    }

    private AsyncTask initAni(){
        //开始动画
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
        return asyncTask;
    }

    /**
     * 开始箭头动画
     */
    private void startAni(int height){
        LeftArrow leftArrowNew = new LeftArrow(leftArrow);
        ObjectAnimator animator = ObjectAnimator.ofInt(leftArrowNew,"height",height).setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
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
}
