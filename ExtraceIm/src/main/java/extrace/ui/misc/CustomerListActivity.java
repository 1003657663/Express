package extrace.ui.misc;

import java.util.ArrayList;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.support.v4.widget.SearchViewCompat.OnQueryTextListenerCompat;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SearchView;
import extrace.loader.CustomerListLoader;
import extrace.model.CustomerInfo;
import extrace.ui.main.*;

public class CustomerListActivity extends ActionBarActivity {
	
	PlaceholderFragment list_fg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();

        // Create the list fragment and add it as our sole content.
        if (fm.findFragmentById(android.R.id.content) == null) {
        	list_fg = new PlaceholderFragment();
            fm.beginTransaction().add(android.R.id.content, list_fg).commit();
        }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		list_fg.onActivityResult(requestCode,resultCode,data);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends ListFragment {
		private CustomerListAdapter mAdapter;
		private CustomerListLoader mLoader;

		private CustomerInfo selectItem;
		private int selectPosition;

		Intent mIntent;  

		public PlaceholderFragment() {
		}

		@Override public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            // Give some text to display if there is no data.  In a real
            // application this would come from a resource.
            setEmptyText("查找客户!");

            // We have a menu item to show in action bar.
            setHasOptionsMenu(true);

            // Create an empty adapter we will use to display the loaded data.
            mAdapter = new CustomerListAdapter(new ArrayList<CustomerInfo>(), this.getActivity());
            setListAdapter(mAdapter);

            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE); 

            registerForContextMenu(getListView());	//getListView().setLongClickable(true);

            // Start out with a progress indicator.
            //setListShown(false);
            
    		if (mIntent.hasExtra("Action")) {
    			if(mIntent.getStringExtra("Action").equals("New")){
    				//mItem = new CustomerInfo();
    			}
    			else if(mIntent.getStringExtra("Action").equals("None")){
    				//mItem = new CustomerInfo();
    			}
    			else if(mIntent.getStringExtra("Action").equals("Edit")){
    				if (mIntent.hasExtra("CustomerInfo")) {
    					selectItem = (CustomerInfo) mIntent.getSerializableExtra("CustomerInfo");
    					RefreshList(selectItem.getName());
    				} else {
    					this.getActivity().setResult(RESULT_CANCELED, mIntent);//---chao---在finish这个activity周返回数据
    					this.getActivity().finish();
    				}
    			}
    			else{
    				this.getActivity().setResult(RESULT_CANCELED, mIntent);
    				this.getActivity().finish();
    			}
    		}
    		else{
    			this.getActivity().setResult(RESULT_CANCELED, mIntent);
    			this.getActivity().finish();
    		}
        }

	    @Override  
	    public void onAttach(Activity activity) {  
	        super.onAttach(activity);  
	        mIntent = activity.getIntent();
	    }

	    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			// Inflate the menu; this adds items to the action bar if it is present.
			inflater.inflate(R.menu.customer_list, menu);
			
			MenuItem item = menu.findItem(R.id.action_search);
			final SearchView searchView = (SearchView) item.getActionView();
			if (searchView != null) {

				SearchViewCompat.setOnQueryTextListener(searchView,
						new OnQueryTextListenerCompat() {
							@Override
							public boolean onQueryTextChange(String newText) {
								// Called when the action bar search text has
								// changed. Since this
								// is a simple array adapter, we can just have
								// it do the filtering.
								return true;
							}
							
							@Override
							public boolean onQueryTextSubmit(String query) {
								if (!TextUtils.isEmpty(query)) {
									RefreshList(query);
									SearchViewCompat.setQuery(searchView, null,true);
								}
								return true;
							}
						});
				SearchViewCompat.setOnCloseListener(searchView,
						new OnCloseListenerCompat() {
							@Override
							public boolean onClose() {
								if (!TextUtils.isEmpty(SearchViewCompat.getQuery(searchView))) {
									SearchViewCompat.setQuery(searchView, null,true);
								}
								return true;
							}

						});
				MenuItemCompat.setActionView(item, searchView);
			}
	    }
	    
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			int id = item.getItemId();
			switch(id){
			case android.R.id.home:
			case R.id.action_ok:
				SelectOk();	//返回给上层
				return true;
			case R.id.action_edit:
				EditItem();
				return true;
			case R.id.action_new:
				NewItem();
				return true;
			case R.id.action_search:
				return true;
			}
			return super.onOptionsItemSelected(item);
		}
		
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		    super.onCreateContextMenu(menu, v, menuInfo);
		    AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;

		    //onListItemClick(null,null,info.position,0);

		    selectItem = mAdapter.getItem(info.position);
		    selectPosition = info.position;
        	this.getActivity().setTitle(selectItem.getName());
		    menu.setHeaderTitle("客户: "+selectItem.getName());
		    menu.add(info.position, 1, 0, "选择");
		    menu.add(info.position, 2, 1, "修改");
		    menu.add(info.position, 3, 2, "删除");
		}

		@Override
		public boolean onContextItemSelected(MenuItem item) {
		    //AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		    if (item.getTitle().equals("选择")) {
				SelectOk();	//返回给上层
		   } else if (item.getTitle().equals("修改")) {
				EditItem();	//编辑客户
		   } else if (item.getTitle().equals("删除")) {
				DeleteItem();	//删除客户
		   }
		   return super.onContextItemSelected(item);
		}	   
		
        @Override public void onListItemClick(ListView l, View v, int position, long id) {
        	selectItem = mAdapter.getItem(position);
        	selectPosition = position; 
        	this.getActivity().setTitle(selectItem.getName());
        }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {	//编辑界面的回调
    		switch (resultCode) {
    		case RESULT_OK:
    			CustomerInfo customer;
    			if (data.hasExtra("CustomerInfo")) {
    				customer = (CustomerInfo) data.getSerializableExtra("CustomerInfo");
	    			if(requestCode == CustomerEditActivity.INTENT_NEW){
	    				mAdapter.getData().add(customer);
	    			}
	    			else if(requestCode == CustomerEditActivity.INTENT_EDIT){
	    				mAdapter.setItem(customer, selectPosition);
	    			}
	    			mAdapter.notifyDataSetChanged();
    			}
//    			if (data.hasExtra("CustomerInfo")) {
//    				customer = (CustomerInfo) data.getSerializableExtra("CustomerInfo");
//   					RefreshList(customer.getName());
//    			}
    			break;
    		default:
    			break;
    		}
    	}
        
		private void SelectOk()
		{
	        mIntent.putExtra("CustomerInfo",selectItem);				
			this.getActivity().setResult(RESULT_OK, mIntent);
			this.getActivity().finish();
		}

		private void EditItem()
		{
	        Intent intent = new Intent();
			intent.setClass(this.getActivity(),CustomerEditActivity.class);
			intent.putExtra("Action","Edit"); 
			intent.putExtra("CustomerInfo",selectItem); 
			this.getActivity().startActivityForResult(intent, CustomerEditActivity.INTENT_EDIT);	//激发下层编辑
		}

		private void NewItem()
		{
	        Intent intent = new Intent();
			intent.setClass(this.getActivity(),CustomerEditActivity.class);
			intent.putExtra("Action","New"); 
			this.getActivity().startActivityForResult(intent, CustomerEditActivity.INTENT_NEW);	//激发下层编辑-建立新的
		}

		private void DeleteItem()
		{
			try {
				mLoader = new CustomerListLoader(mAdapter, this.getActivity());
				mLoader.DeleteCustomer(selectItem.getID());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void RefreshList(String name)
		{
			this.getActivity().setTitle("");
			if(checkMobile(name)){	//符合电话号码的规律,按电话号码查询
				try {
					mLoader = new CustomerListLoader(mAdapter, this.getActivity());
					mLoader.LoadCustomerListByTelCode(name);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else
			{
				try {
					mLoader = new CustomerListLoader(mAdapter, this.getActivity());
					mLoader.LoadCustomerListByName(name);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		public boolean checkMobile(String str){ 
			String regex_mb = "(\\+\\d+)?1[34578]\\d{9}$";  //移动电话的正则表达式
	        String regex_ph = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";  //固定电话的正则表达式
		    Pattern pattern_mb = Pattern.compile(regex_mb); 
		    Pattern pattern_ph = Pattern.compile(regex_ph); 
		    return pattern_mb.matcher(str).matches() || pattern_ph.matcher(str).matches();    
		} 
	}
}
