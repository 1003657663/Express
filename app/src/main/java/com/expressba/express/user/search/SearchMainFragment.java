package com.expressba.express.user.search;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.expressba.express.R;
import com.expressba.express.main.UIFragment;
import com.expressba.express.myelement.MyFragmentManager;
import com.expressba.express.toolbox.CheckInput;

/**
 * Created by songchao on 16/5/25.
 */
public class SearchMainFragment extends UIFragment implements View.OnClickListener{

    private TextView searchTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_main,container,false);
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        view.findViewById(R.id.search_main_submit).setOnClickListener(this);

        searchTextView = (TextView) view.findViewById(R.id.search_main_text);

        searchTextView.setText("89899774542259");

        initListener();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_bar_left_img:
                getFragmentManager().popBackStack();
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
            MyFragmentManager.turnFragment(getClass(),SearchExpressFragment.class,bundle,getFragmentManager(),false);
        }else{
            searchTextView.setError("输入必须是快递单号");
        }
    }

    private void showMessage(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}
