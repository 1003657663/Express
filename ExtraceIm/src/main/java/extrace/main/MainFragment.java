package extrace.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.app.Activity;
import android.widget.Toast;


import com.xys.libzxing.zxing.activity.CaptureActivity;

import extrace.Customer.Express.view.express_edit_view.express_edit_Fragment;
import extrace.Customer.Express.view.express_search_view.express_search_Fragment;
import extrace.ui.main.R;
import extrace.user.login.LoginFragment;
import extrace.user.me.MeFragment;
import extrace.user.search.SearchExpressFragment;


/**
 * Created by songchao on 16/4/4.
 */
public class MainFragment extends Fragment implements View.OnClickListener{

    private Button meButton;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private ImageButton cameraButton;
    private ImageButton messageButton;
    private MyApplication myApplication;
    private Button send,search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_fragment,container,false);
        myApplication = (MyApplication) getActivity().getApplication();
        cameraButton = (ImageButton) view.findViewById(R.id.index_top_bar_camera);
        messageButton = (ImageButton) view.findViewById(R.id.index_top_bar_message);
        send=(Button)view.findViewById(R.id.send);
        search=(Button)view.findViewById(R.id.search);
        meButton = (Button) view.findViewById(R.id.me_button);

        meButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);
        messageButton.setOnClickListener(this);
        send.setOnClickListener(this);
        search.setOnClickListener(this);

        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.me_button:
                if(!myApplication.getUserInfo().getLoginState()) {//没有登陆跳转登陆界面
                    toLoginFragment();
                }else {
                    toMeFragment();//登陆后跳转"我"界面
                }
                break;
            case R.id.index_top_bar_camera:
                startCamera();
                break;
            case R.id.index_top_bar_message:
                toSearchResult();
                break;
            case R.id.send:
                toSendFragment();
                break;
            case R.id.search:
                toSearchFragment();
                break;
        }
    }

    /**
     * 跳转到搜索后的界面，测试用
     */
    private void toSearchResult(){
        SearchExpressFragment searchExpressFragment = new SearchExpressFragment();
        transaction.replace(R.id.fragment_container_layout,searchExpressFragment);
        transaction.addToBackStack("index");
        transaction.commit();
    }
    /**
     * 跳转到“我”界面
     */
    private void toMeFragment(){
        MeFragment meFragment = new MeFragment();
        transaction.replace(R.id.fragment_container_layout, meFragment);
        transaction.addToBackStack("index");
        transaction.commit();
    }


    /**
     * 跳转到搜索界面
     */
    private void toSearchFragment(){
        express_search_Fragment fragment = new express_search_Fragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_layout, fragment);
        transaction.addToBackStack("index");
        transaction.commit();
    }


    /**
     * 跳转到寄快递界面
     */
    private void toSendFragment(){
        express_edit_Fragment fragment = new express_edit_Fragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_layout, fragment);
        transaction.addToBackStack("index");
        transaction.commit();
    }

    /**
     * 跳转到登陆界面
     */
    private void toLoginFragment(){
        LoginFragment loginFragment = new LoginFragment();
        transaction.replace(R.id.fragment_container_layout, loginFragment);
        transaction.addToBackStack("index");
        transaction.commit();
    }

    /**
     * 跳转到扫码界面
     */
    private void startCamera(){
        startActivityForResult(new Intent(getActivity(),CaptureActivity.class),0);
    }


    /**
     * 获取上个界面返回的值
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==Activity.RESULT_OK)
        {
            Bundle bundle=data.getExtras();
            String result=bundle.getString("result");
            Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();
            express_search_Fragment fragment=new express_search_Fragment();
            Bundle bundle1=new Bundle();
            bundle1.putString("ID",result);
            fragment.setArguments(bundle1);
            transaction.replace(R.id.fragment_container_layout, fragment);
            transaction.addToBackStack("index");
            transaction.commit();
        }
    }
}
