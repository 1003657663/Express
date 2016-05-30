package com.expressba.express.toolbox;

/**
 * Created by songchao on 16/5/30.
 */
public class Tools {

    public static String handlerTimeWithZero(String time){
        if(time.substring(time.length()-2,time.length()).equals(".0")){
            time = time.substring(0,time.length()-2);
        }
        return time;
    }
}
