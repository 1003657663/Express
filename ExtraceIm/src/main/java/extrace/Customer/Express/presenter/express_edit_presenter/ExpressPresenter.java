package extrace.Customer.Express.presenter.express_edit_presenter;

/**
 * Created by 黎明 on 2016/5/8.
 */
public interface ExpressPresenter {
    void doNewExpress(int customerId, int send_ID, int receive_ID);

    void onSuccess(String ID);

    void onFail(String errorMessage);
}
