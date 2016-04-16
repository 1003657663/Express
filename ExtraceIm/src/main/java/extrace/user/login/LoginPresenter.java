package extrace.user.login;

/**
 * Created by chao on 2016/4/16.
 * //带有on开头的是回掉，不带on的是直接调用
 */
public interface LoginPresenter {
    void onLoginSuccess();
    void onLoginFail();
    void onLoginRepeat();//登陆重复
    void startLogin(String tel,String password);//开始登陆
    void startRegister(String tel,String password);//开始注册
}
