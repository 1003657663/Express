package com.expressba.expressuser.user.search;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.expressba.expressuser.R;
import com.expressba.expressuser.main.UIFragment;
import com.expressba.expressuser.toolbox.CheckInput;

/**
 * Created by songchao on 16/5/25.
 */
public class SearchMainFragment extends UIFragment implements View.OnClickListener{

    private TextView searchTextView;
    private String expressID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_search_main,container,false);
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        view.findViewById(R.id.search_main_submit).setOnClickListener(this);
        searchTextView = (TextView) view.findViewById(R.id.search_main_text);
        initListener();
        return view;
    }

    @Override
    protected void handlerIfBundle(Bundle bundle) {
        expressID = bundle.getString("expressid");
    }

    @Override
    protected void handlerEveryInit() {
        super.handlerEveryInit();
        if(expressID!=null) {
            searchTextView.setText(expressID);
        }else{
            searchTextView.setText("42776244229381");
        }
    }

    @Override
    protected void onBack() {
        getFragmentManager().popBackStackImmediate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_bar_left_img:
                onBack();
                break;
            case R.id.search_main_submit:
                toSearchExpressFragment();
                break;
        }
    }

    /**
     * 初始化监听
     */
    private void initListener(){
        searchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    toSearchExpressFragment();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 跳转fragment
     */
    private void toSearchExpressFragment(){
        String searchText = searchTextView.getText().toString();
        if(CheckInput.checkNumber(searchText)){
            Bundle bundle = new Bundle();
            bundle.putString("searchID",searchText);
            SearchExpressFragment sef = new SearchExpressFragment();
            sef.setArguments(bundle);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.search_main_framelayout,sef);
            ft.commitAllowingStateLoss();

        }else{
            searchTextView.setError("输入必须是快递单号");
        }
    }

    private void showMessage(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}
