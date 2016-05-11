package com.expressba.express.main;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import com.expressba.express.myelement.MyFragmentManager;
import com.expressba.express.R;

public class MainActivity extends Activity implements MainView {

    FragmentManager fm;
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenterImpl(this);
        if(savedInstanceState == null) {//保存状态，防止重建
            fm = getFragmentManager();
            setDefaultFragment();//设置第一个主布局的fragment
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setDefaultFragment() {
        MyFragmentManager.turnFragment(null,MainFragment.class,null,getFragmentManager());
        /*FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //SorterIndexFragment indexFragment = new SorterIndexFragment();
        MainFragment indexFragment = new MainFragment();
        transaction.replace(R.id.fragment_container_layout, indexFragment);
        transaction.commit();*/
    }

}
