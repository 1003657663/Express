package extrace.ui.user.login;

/**
 * Created by chao on 2016/4/16.
 * //带有on开头的是回掉，不带on的是直接调用
 */
public interface LoginPresenter {
    public void onLoginSuccess();
    public void onLoginFail();
    public void startLogin();
}
