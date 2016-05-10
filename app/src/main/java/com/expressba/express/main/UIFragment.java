package com.expressba.express.main;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by songchao on 16/5/11.
 */
public abstract class UIFragment extends Fragment{
    Bundle bundle;
    public void setBundle(Bundle bundle){
        this.bundle = bundle;
    }
    public Bundle getBundle(){
        return bundle;
    }
}
