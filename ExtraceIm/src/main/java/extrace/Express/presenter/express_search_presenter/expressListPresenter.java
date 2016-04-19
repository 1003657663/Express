package extrace.Express.presenter.express_search_presenter;

/**
 * Created by 黎明 on 2016/4/16.
 */
public interface expressListPresenter
{
    public void onSuccess();
    public void onFail();
    public void doSearchExpress(String ID);
    //调用model查询
}
