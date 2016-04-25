package extrace.user.me;

/**
 * Created by songchao on 16/4/24.
 */
public interface MeView {
    void toUserInfoFragment();
    void toSendRecordFragment();
    void toAboutSoftFragment();
    void toMyComplaint();
    void toUserSendAddress();
    void toUserReceiveAddress();
    void loginOut();
}
