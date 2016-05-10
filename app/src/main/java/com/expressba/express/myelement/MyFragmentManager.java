package com.expressba.express.myelement;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.expressba.express.R;
import com.expressba.express.main.UIFragment;

/**
 * Created by songchao on 16/5/11.
 */
public class MyFragmentManager {
    /**
     * 用于fragment之间进行切换,默认不pop直接切换
     * @param fromFragmentClass
     * @param toFragmentClass
     * @param args
     * @param fm
     */
    public static void turnFragment(Class<? extends UIFragment> fromFragmentClass, Class<? extends UIFragment> toFragmentClass, Bundle args, FragmentManager fm){

        FragmentTransaction ft = fm.beginTransaction();
        //被切换的fragment标签
        String fromTag = null;
        if(fromFragmentClass!=null) {
            fromTag = fromFragmentClass.getSimpleName();
        }
        //切换到fragment标签
        String toTag = toFragmentClass.getSimpleName();

        UIFragment fromFragment = (UIFragment) fm.findFragmentByTag(fromTag);
        UIFragment toFragment = (UIFragment) fm.findFragmentByTag(toTag);
        //如果要切换的fragment不存在，那么创建
        if(toFragment == null){
            try {
                toFragment = toFragmentClass.newInstance();
                toFragment.setArguments(args);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }else {
            if (args != null && !args.isEmpty()) {
                //toFragment.getArguments().putAll(args);
                toFragment.setBundle(args);
            }
        }
        //设置fragment切换效果
        ft.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_left,R.animator.slide_in_right,R.animator.slide_out_right);
        //如果tofragment已经添加到栈里面，那么隐藏现在的fragment显示要切换的fragment，
        //如果没有添加，那么隐藏现在的，添加要切换的fragment
        if(toFragment!=null) {
            if(fromFragment!=null) {
                if (!toFragment.isAdded()) {
                    ft.hide(fromFragment).add(R.id.fragment_container_layout, toFragment, toTag);
                } else {
                    ft.hide(fromFragment).show(toFragment);
                }
            }else{
                if(!toFragment.isAdded()){
                    ft.add(R.id.fragment_container_layout,toFragment,toTag);
                }else{
                    ft.show(toFragment);
                }
            }
            //添加到回退栈
            ft.addToBackStack(fromTag);
            //不保留状态提交事务
            ft.commitAllowingStateLoss();
        }else{

        }
    }


    /**
     * 用于fragment之间进行切换,pop出栈传值
     * @param toFragmentClass
     * @param args
     * @param fm
     */


    public static void popFragment(Class<? extends UIFragment> toFragmentClass,Bundle args,FragmentManager fm){
        FragmentTransaction ft = fm.beginTransaction();
        //设置fragment切换效果
        ft.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_left,R.animator.slide_in_right,R.animator.slide_out_right);
        //切换到fragment标签
        String toTag = toFragmentClass.getSimpleName();
        UIFragment toFragment = (UIFragment) fm.findFragmentByTag(toTag);
        //如果要切换的fragment不存在，那么创建
        if(toFragment != null){
            if (args != null && !args.isEmpty()) {
                //toFragment.getArguments().putAll(args);
                toFragment.setBundle(args);
            }
        }else {
            try {
                toFragment = toFragmentClass.newInstance();
                toFragment.setArguments(args);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        fm.popBackStack();
    }

}
