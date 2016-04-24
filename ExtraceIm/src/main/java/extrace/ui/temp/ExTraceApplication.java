package extrace.ui.temp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import extrace.model.UserInfo;
//---chao---从本地存储，键值对中读取所需的url，用户和设置信息等

public class ExTraceApplication extends Application {
	//private static final String PREFS_NAME = "ExTrace.cfg";
    SharedPreferences settings;// = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
//	String mServerUrl;
//	String mMiscService,mDomainService;
    UserInfo userInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        settings = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        //SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        //临时造一个用户----测试数据
        if(userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setID(12);
            userInfo.setTelephone("15039985698");
            /*userInfo.setReceivePackageID("1111112222");
            userInfo.setTransPackageID("1111115555");
            userInfo.setDelivePackageID("1111113333");*/
        }
    }

    public String getServerUrl() {  
        return settings.getString("ServerUrl", "");  
    }  
    public String getMiscServiceUrl() {  
        return getServerUrl() + settings.getString("MiscService", ""); 
    }  
    public String getDomainServiceUrl() {  
        return getServerUrl() + settings.getString("DomainService", ""); 
    }  
  
    public UserInfo getLoginUser(){
    	return userInfo;
    }
      
    public void onTerminate() {  
        super.onTerminate();  
          
        //save data of the map  
    }  
}
