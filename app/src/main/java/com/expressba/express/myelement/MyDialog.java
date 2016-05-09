package com.expressba.express.myelement;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.expressba.express.R;

/**
 * Created by songchao on 15/8/8.
 */
public class MyDialog {
    private Context context;
    private AlertDialog.Builder builder;
    private ProgressDialog progressDialog;
    public MyDialog(Context context){
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }

    public void showDialogWithSure(String message, String sureButtonText){
        if(progressDialog!=null)
            if(progressDialog.isShowing()){
                progressDialog.hide();
            }
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.mydialog_layout, null);
        TextView contentText = (TextView) linearLayout.findViewById(R.id.mydialog_text);
        Button sButton = (Button) linearLayout.findViewById(R.id.mydialog_sure_button);
        Button nButton = (Button) linearLayout.findViewById(R.id.mydialog_no_button);
        nButton.setVisibility(View.GONE);
        contentText.setText(message);
        sButton.setText(sureButtonText);
        builder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog;
        builder.setView(linearLayout);
        alertDialog = builder.create();
        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sureButton!=null) {
                    alertDialog.cancel();
                    sureButton.sureButtonDo();
                }
                else
                    alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    public void showDialogWithSureAndNo(String message, String sureButtonText, String noButtonText){
        if(progressDialog!=null)
            if(progressDialog.isShowing()){
                progressDialog.hide();
            }
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.mydialog_layout, null);
        TextView contentText = (TextView) linearLayout.findViewById(R.id.mydialog_text);
        Button sButton = (Button) linearLayout.findViewById(R.id.mydialog_sure_button);
        Button nButton = (Button) linearLayout.findViewById(R.id.mydialog_no_button);
        nButton.setVisibility(View.VISIBLE);
        contentText.setText(message);
        sButton.setText(sureButtonText);
        builder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog;
        builder.setView(linearLayout);
        alertDialog = builder.create();
        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sureButton!=null) {
                    alertDialog.cancel();
                    sureButton.sureButtonDo();
                }
                else
                    alertDialog.cancel();
            }
        });
        nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noButton!=null){
                    alertDialog.cancel();
                    noButton.noButtonDo();
                }else{
                    alertDialog.cancel();
                }
            }
        });
        alertDialog.show();
    }

    public void showProgressDialog(String message){
        if(progressDialog!=null) {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }
    public void hideProgressDialog(){
        if(progressDialog!=null)
            progressDialog.cancel();
    }

    public void initProgressDialog(){
        progressDialog = null;
    }
    public void getProgressDialog(){
        progressDialog = new ProgressDialog(context);
    }

    NoButton noButton;
    public void setNoButton(NoButton noButton){
        this.noButton = noButton;
    }
    public interface NoButton{
        public void noButtonDo();
    }

    SureButton sureButton;
    public void setSureButton(SureButton sureButton){
        this.sureButton = sureButton;
    }
    public interface SureButton{
        public void sureButtonDo();
    }
}
