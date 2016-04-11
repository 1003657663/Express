package extrace.ui.domain;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import extrace.loader.ExpressListLoader;
import extrace.misc.model.ExpressSheet;
import extrace.ui.main.ExTraceApplication;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 *
 * ---chao---主界面上的切换界面中的派送任务，揽收任务，转运任务的实现fragment
 */
public class ExpressListFragment extends ListFragment {

	private static final String ARG_EX_TYPE = "ExType";
	
	// TODO: Rename and change types of parameters
	private String mExType;

	private ExpressListAdapter mAdapter;
	private ExpressListLoader mLoader;

	Intent mIntent;  
	
	private OnFragmentInteractionListener mListener;

	// TODO: Rename and change types of parameters
	public static ExpressListFragment newInstance(String ex_type) {//---chao---这个静态方法，初始化以恶ExpressListFragment，然后返回
		
		ExpressListFragment fragment = new ExpressListFragment();

		Bundle args = new Bundle();
		args.putString(ARG_EX_TYPE, ex_type);	//构造方法传入参数,使用Bundle来作为参数的容器
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ExpressListFragment() {
	}

	@Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {	//另一种读出传入参数的方式
			mExType = getArguments().getString(ARG_EX_TYPE);
		}
        // Give some text to display if there is no data.  In a real
        // application this would come from a resource.
        setEmptyText("快递列表空的!");
        
        mAdapter = new ExpressListAdapter(new ArrayList<ExpressSheet>(), this.getActivity(), mExType);
        setListAdapter(mAdapter);

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE); 
        registerForContextMenu(getListView());
        
        RefreshList();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		if (null != mListener) {
			// Notify the active callbacks interface (the activity, if the
			// fragment is attached to one) that an item has been selected.
			mListener.onFragmentInteraction(mAdapter.getItem(position).getID());
		}
		EditExpress(mAdapter.getItem(position));//---chao---点击list中的某一项后跳转到ExpressEditActivity界面
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(String id);
	}
	
	private void RefreshList()
	{
		String pkgId = null;
		switch(mExType){
		case "ExDLV":
			pkgId = ((ExTraceApplication)this.getActivity().getApplication()).getLoginUser().getDelivePackageID();
			break;
		case "ExRCV":
			pkgId = ((ExTraceApplication)this.getActivity().getApplication()).getLoginUser().getReceivePackageID();
			break;
		case "ExTAN":
			pkgId = ((ExTraceApplication)this.getActivity().getApplication()).getLoginUser().geTransPackageID();
			break;
		}
		mLoader = new ExpressListLoader(mAdapter, this.getActivity());
		mLoader.LoadExpressListInPackage(pkgId);
		//mLoader.LoadExpressList();
	}

	void EditExpress(ExpressSheet es)
    {
		Intent intent = new Intent();
		intent.putExtra("Action","Edit");
		intent.putExtra("ExpressSheet",es);
		intent.setClass(this.getActivity(), ExpressEditActivity.class);
		startActivityForResult(intent, 0);  	
    }

}
