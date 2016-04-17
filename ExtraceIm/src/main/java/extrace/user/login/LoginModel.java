package extrace.user.login;

/**
 * Created by chao on 2016/4/16.
 */
public interface LoginModel {
    public void onStartLogin(String tel,String password);
    public void onStartRegister(String tel,String password);
    public void onSaveUserInfo();//存储登陆或者注册成功的用户信息
}
