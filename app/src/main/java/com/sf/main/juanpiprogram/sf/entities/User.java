package com.sf.main.juanpiprogram.sf.entities;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 15-11-20.
 */
public class User extends BmobObject {

    private String userName;
    private String password;
    private String phoneNum;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
