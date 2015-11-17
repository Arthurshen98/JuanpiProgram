package com.sf.main.juanpiprogram.sf.Utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

/**
 * 全局application，为什么要用application，主要是因为这就像一个session，
 * 用于临时保存各种传值，服务器url，如果用数据库或者其他的操作来保存这些
 * 数据的话管理起来就很繁琐，而且也不利于程序的运行速度。
 *
 */
public class BaseApplication extends Application {
    /**
     * 为了完全退出程序调用方法 BaseApplication.getInstance().addActivity(this);
     * BaseApplication.getInstance().exit();
     */
    private static BaseApplication instance;

    private List<Activity> activityList = new LinkedList<Activity>();

    public BaseApplication(){}

    // 单例模式获取唯一的MyApplication实例
    public static BaseApplication getIntance() {
        if (instance == null) {
            instance = new BaseApplication();
        }
        return instance;
    }


    //返回Context的对象
    public static Context getContext(){
        return instance.getApplicationContext(); //今后使用的时候只需要BaseApplication.getContext();即可得到context,
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }



}
