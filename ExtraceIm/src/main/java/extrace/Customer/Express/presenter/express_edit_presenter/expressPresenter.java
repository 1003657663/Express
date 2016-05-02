package extrace.Customer.Express.presenter.express_edit_presenter;


/**
 * Created by 黎明 on 2016/4/16.
 */
public interface Expresspresenter
{
     void onSuccess(String ID);
     void onFail(String message);
     void doNewExpress(int customerId, int send_ID, int receive_ID);

}
