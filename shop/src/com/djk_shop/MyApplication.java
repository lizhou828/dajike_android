package com.djk_shop;

import android.app.Application;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2014/12/30.
 * 只需要调用Context的 getApplicationContext或者Activity的getApplication方法来获得一个application对象
 */
public class MyApplication extends Application {

    //用于在Activity之间传递复杂类型的参数
    private Map<String ,Object> data;

    private Map<String ,Object> cache;

    //安卓应用启动真正的入口点。
    @Override
    public void onCreate() {
        super.onCreate();
        data = new HashMap<String,Object>();
        cache = new HashMap<String,Object>();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getCache() {
        return cache;
    }

    public void setCache(Map<String, Object> cache) {
        this.cache = cache;
    }
}
