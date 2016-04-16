package extrace.ui.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import extrace.user.login.LoginFragment;
import zxing.util.CaptureActivity;

/**
 * Created by songchao on 16/4/4.
 */
public class MainFragment extends Fragment {

    private Button meButton;
    private FragmentManager fm;
    private ImageButton cameraButton;
    private ImageButton messageButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_fragment,container,false);

        cameraButton = (ImageButton) view.findViewById(R.id.index_top_bar_camera);
        messageButton = (ImageButton) view.findViewById(R.id.index_top_bar_message);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        fm = getFragmentManager();

        meButton = (Button) view.findViewById(R.id.me_button);
        meButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = fm.beginTransaction();

                LoginFragment loginFragment = new LoginFragment();
                transaction.replace(R.id.fragment_container_layout, loginFragment);
                transaction.addToBackStack("index");
                transaction.commit();
            }
        });
        return view;
    }

    private void startCamera(){
        Intent intent = new Intent();
        intent.putExtra("Action","Captrue");
        intent.setClass(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("");
    }
}
