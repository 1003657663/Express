package com.expressba.expressuser.main;

import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

/**
 * Created by songchao on 16/5/11.
 */
public abstract class UIFragment extends Fragment {
    Bundle bundle;
    View.OnKeyListener keyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                onBack();
                return true;
            }
            return false;
        }
    };

    /**
     * 每次进入这个fragment都会执行这一部分
     */
    private void runEvery() {
        if (getBundle() != null) {
            handlerIfBundle(bundle);
            handlerEveryInit();
        } else {
            handlerEveryInit();
        }
        View view = getView();
        if (view != null) {
            view.setOnKeyListener(keyListener);
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        runEvery();
        onStartHandlerBundle();
        if(getView()!=null) {
            onStartHandlerView(getView());
        }
    }

    protected abstract void onBack();//每个继承者都要实现onback接口

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
        runEvery();
    }

    public Bundle getBundle() {
        if (bundle == null) {
            bundle = getArguments();
            return bundle;
        } else {
            return this.bundle;
        }
    }

    /**
     * 当获取的bundle不为空的时候执行，每次进入页面都会重新获取bundle，从bundle获取数据的代码放在里面
     * @param bundle
     * @return
     */
    protected void handlerIfBundle(Bundle bundle) {

    }

    /**
     * 每次进入的时候执行，执行顺序在hanglerIfBundle后面，bundle为空或者不为空都执行
     */
    protected void handlerEveryInit() {

    }


    /**
     * 在onStart执行，可以获取bundle,这两个onStart的执行都在上面的handler函数后面
     */
    protected void onStartHandlerBundle(){

    }

    /**
     * 在onStart执行，执行和视图有关的处理部分，只有view不为空才执行
     * @param view
     */
    protected void onStartHandlerView(View view){

    }
}
