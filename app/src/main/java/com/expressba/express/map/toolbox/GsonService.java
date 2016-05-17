package com.expressba.express.map.toolbox;

import com.google.gson.Gson;

/**
 * Created by songchao on 16/4/28.
 * 转换取得的历史轨迹数据，成json,一个通配转换json类
 */

public class GsonService {

    public static <T> T parseJson(String jsonString, Class<T> clazz) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, clazz);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("解析json失败");
        }
        return t;

    }
}

