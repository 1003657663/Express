package com.expressba.express.toolbox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检查用户输入类
 * Created by chao on 2016/4/28.
 */
public class CheckInput {

    /**
     * 只用来检测手机号的正确性
     * @return
     */
    public static boolean checkTel(String tel){
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(tel);
        if(tel.length()!=11 || !matcher.matches()) {
            return false;
        }
        return true;
    }
}
