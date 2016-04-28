package extrace.ToolBox;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Button;

import extrace.ui.main.R;

/**
 * 倒计时工具类,制定button上，显示指定时间的倒计时
 * Created by chao on 2016/4/28.
 */
public class CountDown extends AsyncTask<Integer,String,Void>{
    Button button;
    Integer time;
    Activity activity;
    String lastText;
    public CountDown(Activity activity,Button button, Integer time,String lastText){
        this.button = button;
        this.time = time;
        this.activity = activity;
        this.lastText = lastText;
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
        button.setText(time+"");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        button.setText("重新获取验证码");
        button.setClickable(true);
        button.setBackground(activity.getResources().getDrawable(R.drawable.button_radio_with_background_color));
    }
}
