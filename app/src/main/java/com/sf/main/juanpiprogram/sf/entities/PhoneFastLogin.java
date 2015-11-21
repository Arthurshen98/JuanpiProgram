package com.sf.main.juanpiprogram.sf.entities;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 15-11-21.
 */
public class PhoneFastLogin extends BmobObject {
    private String phoneNum;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
