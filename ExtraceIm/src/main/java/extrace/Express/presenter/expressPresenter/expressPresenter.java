package extrace.Express.presenter.expressPresenter;


/**
 * Created by 黎明 on 2016/4/16.
 */
public interface expressPresenter
{
     void onSuccess(String ID);
     void onFail();
     void doNewExpress(int send_ID, int receive_ID);

}
