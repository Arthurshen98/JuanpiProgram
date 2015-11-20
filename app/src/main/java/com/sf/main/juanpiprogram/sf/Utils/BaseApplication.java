package com.sf.main.juanpiprogram.sf.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.smssdk.SMSSDK;

/**
 * 全局application，为什么要用application，主要是因为这就像一个session，
 * 用于临时保存各种传值，服务器url，如果用数据库或者其他的操作来保存这些
 * 数据的话管理起来就很繁琐，而且也不利于程序的运行速度。
 *
 * 比如有一个全局的数据操作类用到了context，这个时候就要getApplicationContext 而不是用ACtivity，这就保证了数据库的操作与activity无关（不会一直引用Activity的资源，防止内存泄漏）
 */
public class BaseApplication extends Application {
    /**
     * 为了完全退出程序调用方法 BaseApplication.getInstance().addActivity(this);
     * BaseApplication.getInstance().exit();
     */
    private static BaseApplication instance;
    private static Context context;
    private List<Activity> activityList = new LinkedList<Activity>();

    public BaseApplication(){}
    // 单例模式获取唯一的MyApplication实例
    public static BaseApplication getIntance() {
        if (instance == null) {
            instance = new BaseApplication();
        }
        return instance;
    }




    //初始化程序
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        //初始化db
        DbHelper.init(this);
       //初始化bmob
        Bmob.initialize(this, "412b282b0b8896bf8791d8682843020d");
    }
    //返回Context的对象
    public static Context getContext()
    {
        return context;
    }


   /* //返回Context的对象
    public static Context getContext(){
        return instance.getApplicationContext(); //今后使用的时候只需要BaseApplication.getContext();即可得到context,
    }*/

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

    /**
     *
     */


}
