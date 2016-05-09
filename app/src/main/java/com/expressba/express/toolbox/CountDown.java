package com.expressba.express.toolbox;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.Button;

import com.expressba.express.R;

/**
 * 倒计时工具类,制定button上，显示指定时间的倒计时
 * Created by chao on 2016/4/28.
 */
public class CountDown extends AsyncTask<Integer,String,Void>{
    Button button;
    Integer time;
    Activity activity;
    String lastText;
    Drawable lastDrawable;
    public CountDown(Activity activity,Button button, Integer time,String lastText,Drawable lastDrawable){
        this.button = button;
        this.time = time;
        this.activity = activity;
        this.lastText = lastText;
        this.lastDrawable = lastDrawable;
    }

    @Override
    protected Void doInBackground(Integer... params) {
        Integer time = params[0];
        while (time>0){
            time--;
            publishProgress(time+"");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        button.setText((values[0]));
    }

    @Override
    protected void onPreExecute() {
        button.setClickable(false);
        button.setText(time+"");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        button.setText("重新获取验证码");
        button.setClickable(true);
        button.setBackground(lastDrawable);
    }
}
