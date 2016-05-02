package extrace.Customer.Express.model.express_search_model;

/**
 * Created by 黎明 on 2016/4/17.
 */
public interface Express_list_model
{
    void searchByTel(String ID);
    //根据 手机号查询express-list
    void searchByCID(int ID);
    //根据 customerID查询express-list
    void searchByID(String ID);
    //根据 ID查询expressinfo

}
