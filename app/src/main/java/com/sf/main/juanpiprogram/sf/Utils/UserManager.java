package com.sf.main.juanpiprogram.sf.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用于用户管理的
 */
public class UserManager {

    private Context context;
    private SharedPreferences share;
    private String user_name = null;
    private boolean state;
    public UserManager(Context context) {
        this.context = context;
        share = BaseApplication.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        //得到共享参数对象
         user_name = share.getString("userName","");
         state = share.getBoolean("state", false);
    }

    /**
     * 判断用户是否登录
     */
    public boolean isLogin() {
        if (state) {
           return true;
        }else {
            return false;
        }

    }

    /**
     * 返回用户名
     */
    public String getUser_name() {

        return user_name;
    }

    /**
     * 用户退出
     */
    public void userExit() {
        SharedPreferences share = BaseApplication.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        //得到共享参数编辑对象
        SharedPreferences.Editor edit = share.edit();
        //使用共享参数编辑对象存储数据
        edit.putBoolean("state", false);
        //提交
        edit.commit();
    }

    /**
     * 将数据存储到共享参数里面
     */
    public void saveSharePrefrence(String userName,String phoneNum,boolean state) {

        SharedPreferences share = BaseApplication.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        //得到共享参数编辑对象
        SharedPreferences.Editor edit = share.edit();
        //使用共享参数编辑对象存储数据

        edit.putString("userName", userName);
        edit.putString("phoneNum", phoneNum);
        edit.putBoolean("state", state);
        //提交
        edit.commit();
    }

}
