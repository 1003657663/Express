package extrace.Express.presenter.express_search_presenter;

import java.util.List;

import extrace.Express.model.express_info_model.ExpressInfo;

/**
 * Created by 黎明 on 2016/4/16.
 */
public interface expressListPresenter
{
    public void onSuccess(List<ExpressInfo> list);
    public void onFail();
    public void doSearchExpress(String ID);
    //调用model查询
}
