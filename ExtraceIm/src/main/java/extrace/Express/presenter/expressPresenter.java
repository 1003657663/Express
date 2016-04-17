package extrace.Express.presenter;

/**
 * Created by 黎明 on 2016/4/16.
 */
public interface expressPresenter
{
    public void onSuccess();
    public void onFail();
    public void doNewExpress(int send_ID, int receive_ID);
}
