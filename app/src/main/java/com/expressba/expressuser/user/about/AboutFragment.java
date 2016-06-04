package com.expressba.expressuser.user.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.expressba.expressuser.R;
import com.expressba.expressuser.main.UIFragment;
import com.expressba.expressuser.myelement.MyFragmentManager;

/**
 * Created by songchao on 16/5/25.
 */
public class AboutFragment extends UIFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_soft,container,false);
        ((TextView)view.findViewById(R.id.top_bar_center_text)).setText("关于软件");
        ((ImageView)view.findViewById(R.id.top_bar_left_img)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });
        return view;
    }

    @Override
    protected void onBack() {
        MyFragmentManager.popFragment(getClass(),null,null,getFragmentManager());
    }
}
