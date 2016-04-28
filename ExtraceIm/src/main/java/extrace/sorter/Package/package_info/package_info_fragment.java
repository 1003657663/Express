package extrace.sorter.Package.package_info;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/28.
 */
public class package_info_fragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.packageinfo_edit,container,false);
        return view;
    }
}
