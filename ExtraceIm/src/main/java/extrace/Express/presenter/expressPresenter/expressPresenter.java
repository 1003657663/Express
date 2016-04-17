package extrace.Express.presenter.expressPresenter;

import android.app.Fragment;
import android.content.Context;
import extrace.model.ExpressSheet;
import extrace.net.IDataAdapter;

import java.util.List;

/**
 * Created by 黎明 on 2016/4/16.
 */
public interface expressPresenter
{
    public void onSuccess();
    public void onFail();
    public void doNewExpress(int send_ID, int receive_ID);

}
