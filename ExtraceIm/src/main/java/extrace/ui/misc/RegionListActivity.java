package extrace.ui.misc;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import extrace.loader.RegionListLoader;
import extrace.misc.model.CodeNamePair;
import extrace.ui.main.*;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

@SuppressWarnings("all")
public class RegionListActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();

        // Create the list fragment and add it as our sole content.
        if (fm.findFragmentById(android.R.id.content) == null) {
        	PlaceholderFragment list = new PlaceholderFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
	}
		
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends ListFragment {
		private RegionListAdapter mAdapter;
		private RegionListLoader mLoader;

		private int opStatus;
		private String selectCode,selectString;
		Intent mIntent;  

		public PlaceholderFragment() {
		}

		@Override public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            // Give some text to display if there is no data.  In a real
            // application this would come from a resource.
            setEmptyText("无行政区域选定!");

            // We have a menu item to show in action bar.
            setHasOptionsMenu(true);

            // Create an empty adapter we will use to display the loaded data.
            mAdapter = new RegionListAdapter(new ArrayList<CodeNamePair>(), this.getActivity());
            setListAdapter(mAdapter);

            // Start out with a progress indicator.
            //setListShown(false);

	        if(mIntent.hasExtra("RegionId")){
	        	selectCode = mIntent.getStringExtra("RegionId");
	        	selectString = mIntent.getStringExtra("RegionString");
	        	this.getActivity().setTitle(selectString);
	        }

			opStatus = 0;
			RefreshList(opStatus, "");
        }

	    @Override  
	    public void onAttach(Activity activity) {  
	        super.onAttach(activity);  
	        mIntent = activity.getIntent();  
	    }

	    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			// Inflate the menu; this adds items to the action bar if it is present.
			inflater.inflate(R.menu.region_list, menu);
			MenuItem item = menu.findItem(R.id.action_reset);
			MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS
	                | MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
			item = menu.findItem(R.id.action_ok);
			MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS
	                | MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			switch(id){
			case R.id.action_ok:
				if(opStatus > 2)
				{
					SelectOk();
				}
				else{
					Toast.makeText(this.getActivity(), "请选择完整的三级行政区!", Toast.LENGTH_SHORT).show();
				}
				return true;
			case R.id.action_reset:
				opStatus = 0;
				RefreshList(opStatus, "");
				return true;
			case R.id.action_settings:
				//SettingsActivity a = new SettingsActivity();
				//a.startActivities(null);
				return true;
			}
			return super.onOptionsItemSelected(item);
		}
	    
        @Override public void onListItemClick(ListView l, View v, int position, long id) {
            // Insert desired behavior here.
        	CodeNamePair cnp = mAdapter.getItem(position);
        	selectCode = cnp.getCode();
        	switch(opStatus){
        	case 0:
            	selectString = cnp.getName();
        		opStatus++;	
            	RefreshList(opStatus, selectCode);
        		break;
        	case 1:
            	selectString += " " + cnp.getName();
        		opStatus++;	
            	RefreshList(opStatus, selectCode);
        		break;
        	case 2:
        		opStatus++;	
            	selectString += " " + cnp.getName();
            	RefreshList(opStatus, selectCode);
        		break;
       		default:
        		break;
        	}

//        	selectCode = cnp.getCode();
//        	selectString = cnp.getName();
        	this.getActivity().setTitle(selectString);
        }

		private void SelectOk()
		{
	        /*将选中的对象赋值给Intent*/ 
	        mIntent.putExtra("RegionId",selectCode);  
	        mIntent.putExtra("RegionString",selectString);  
			
			this.getActivity().setResult(RESULT_OK, mIntent);
			this.getActivity().finish();
		}

		private void RefreshList(int status, String region_code)
		{
			try {
				mLoader = new RegionListLoader(mAdapter, this.getActivity());
				switch(status)
				{
				case 0:
					mLoader.LoadProvinceList();
					break;
				case 1:
					mLoader.LoadCityList(region_code);
					break;
				case 2:
					mLoader.LoadTownList(region_code);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
