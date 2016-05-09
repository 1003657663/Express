package com.expressba.express.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * create by chao
 * 用户信息model
 */
public class UserInfo{

	private boolean loginState = false;
    private Integer id;
    private String password;
	private String name;
	private String telephone;
    private SharedPreferences spf;
    private Context context;

    private String token;

    public UserInfo(Context context){
        this.context = context;
        spf = PreferenceManager.getDefaultSharedPreferences(context);
    }


	
	public void setPassword(String value) {
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("password",value);
        editor.apply();
		this.password = value;
	}
	
	public String getPassword() {
		return password;
	}

    public String getPasswordFromPreference(){
        return spf.getString("password",password);
    }
	
	public void setName(String value) {
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("name",value);
        editor.apply();
		this.name = value;
	}
	
	public String getName() {
		return name;
	}

    public String getNameFromPreference(){
        return spf.getString("name","");
    }

	public boolean getLoginState() {
		return loginState;
	}

    public boolean getLoginStateFromPreference(){
        return spf.getBoolean("loginState",false);
    }

	public void setLoginState(boolean loginState) {
        SharedPreferences.Editor editor;
        if(!loginState){
            editor = spf.edit();
            editor.putBoolean("loginState",false);
            editor.remove("name");
            editor.remove("telephone");
            editor.remove("password");
            editor.apply();
        }
		this.loginState = loginState;
	}

	public String getTelephone() {
		return telephone;
	}

    public String getTelephoneFromPreference(){
        return spf.getString("telephone","");
    }

	public void setTelephone(String telephone) {
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("telephone",telephone);
        editor.apply();
		this.telephone = telephone;
	}


    public String toString(boolean idOnly) {

        StringBuffer sb = new StringBuffer();
        sb.append("UserInfo[ ");
        sb.append("password=").append(getPassword()).append(" ");
        sb.append("Name=").append(getName()).append(" ");
        sb.append("TelCode=").append(getTelephone()).append(" ");
        sb.append("]");
        return sb.toString();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
