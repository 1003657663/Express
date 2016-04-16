package extrace.user.login;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;
import junit.framework.TestCase;

import extrace.ui.main.MainActivity;

/**
 * Created by chao on 2016/4/16.
 */
public class LoginPresenterImplTest extends TestCase {
    public LoginPresenterImplTest(){

    }
    Activity activity;

    @Override
    protected void setUp() throws Exception {
        ActivityInstrumentationTestCase2 activityInstrumentationTestCase2 = new ActivityInstrumentationTestCase2(MainActivity.class){
            @Override
            public Activity getActivity() {
                return new Activity();
            }
        };
        Activity activity = activityInstrumentationTestCase2.getActivity();
        this.activity = activity;
        testStartLogin();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testStartLogin() throws Exception {
        System.out.println("ok");
    }

    public void testStartRegister() throws Exception {

    }
}
