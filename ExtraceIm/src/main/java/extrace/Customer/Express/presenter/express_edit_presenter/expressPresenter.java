package extrace.Customer.Express.presenter.express_edit_presenter;


/**
 * Created by 黎明 on 2016/4/16.
 */
public interface expressPresenter
{
     void onSuccess(String ID);
     void onFail();
     void doNewExpress(int customerId, int send_ID, int receive_ID);

}
