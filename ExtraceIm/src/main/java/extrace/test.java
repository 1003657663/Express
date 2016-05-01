package extrace;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/5/1.
 */

public class test extends Fragment
{
    private ArrayList plist=new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.me_fragment,container,false);
        plist.add("1234");
        plist.add("2345");
        JSONArray jsonArray=new JSONArray();
        for(int i=0;i<plist.size();i++)
        {
            try {
                jsonArray.put(i,plist.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(getActivity(),jsonArray.toString(),Toast.LENGTH_LONG).show();
        String a=jsonArray.toString();
        return view;
    }
}