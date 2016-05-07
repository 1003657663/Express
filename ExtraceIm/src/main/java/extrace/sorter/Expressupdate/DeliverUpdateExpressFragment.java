package extrace.sorter.Expressupdate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import extrace.model.ExpressEntity;
import extrace.sorter.SorterIndex.SorterIndexFragment;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/5/3.
 */
public class DeliverUpdateExpressFragment extends Fragment implements DeliverUpdateExpressFragmentView
{
    private DeliverUpdateExpressPresenter presenter;
    private EditText weight,insufee,transfee;
    private Button submit;
    private TextView ID;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.deliver_update_express_first,container,false);
        presenter=new DeliverUpdateExpressPresenterImpl(getActivity(),this);
        ID=(TextView)view.findViewById(R.id.deliver_update_express_first_ID);
        weight=(EditText)view.findViewById(R.id.deliver_update_express_first_weight);
        insufee=(EditText)view.findViewById(R.id.deliver_update_express_first_insufee);
        transfee=(EditText)view.findViewById(R.id.deliver_update_express_first_transfee);
        submit=(Button)view.findViewById(R.id.submit_deliver);
        if (getArguments()!=null)
        {
            ID.setText(getArguments().getString("ID").toString());
        }else getFragmentManager().popBackStack();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weight.getText()==null||insufee.getText()==null||transfee.getText()==null)
                {

                    ExpressEntity expressEntity=new ExpressEntity();
                    expressEntity.setId(ID.getText().toString());
                    expressEntity.setWeight(Float.parseFloat(weight.getText().toString()));
                    expressEntity.setTranFee(Float.parseFloat(transfee.getText().toString()));
                    expressEntity.setInsuFee(Float.parseFloat(insufee.getText().toString()));
                    presenter.updateExpress(expressEntity);}

                else Toast.makeText(getActivity(),"请将信息补充完整",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onFail(String errorMessage) {
        Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        Dialog dialog1 = new AlertDialog.Builder(getActivity()).setIcon(
                android.R.drawable.btn_star).setTitle("确认").setMessage(
                "更新成功").setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               SorterIndexFragment fragment=new SorterIndexFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.fragment_container_layout, fragment);
                transaction.addToBackStack("DeliverUpdateExpressFragment");
                transaction.commit();
            }
        }).create();
        dialog1.show();

    }
}
