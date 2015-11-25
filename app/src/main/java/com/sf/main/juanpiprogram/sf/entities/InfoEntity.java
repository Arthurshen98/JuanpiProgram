package com.sf.main.juanpiprogram.sf.entities;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 15-11-17.
 */

public class InfoEntity {

    @JSONField(name = "id")
    private int id;

    @JSONField(name = "brand_id")
    private String brand_id;

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "pic_url")
    private String pic_url;

    @JSONField(name = "oprice")
    private String oprice;

    @JSONField(name = "cprice")
    private String cprice;

    @JSONField(name = "time_left")
    private String time_left;

    @JSONField(name = "goods_type_name")
    private String goods_type_name;

    @JSONField(name = "pinpai_icon")
    private String pinpai_icon;

    @JSONField(name = "goods_type")
    private String goods_type;

    @JSONField(name = "coupon_tips")
    private String coupon_tips;
    @JSONField(name = "tabname")
    private String tabname;
    public InfoEntity() {
    }

    public InfoEntity(int id, String brand_id, String title, String pic_url, String oprice, String cprice, String time_left, String goods_type_name, String pinpai_icon, String goods_type, String coupon_tips, String tabname) {
        this.id = id;
        this.brand_id = brand_id;
        this.title = title;
        this.pic_url = pic_url;
        this.oprice = oprice;
        this.cprice = cprice;
        this.time_left = time_left;
        this.goods_type_name = goods_type_name;
        this.pinpai_icon = pinpai_icon;
        this.goods_type = goods_type;
        this.coupon_tips = coupon_tips;
        this.tabname = tabname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getOprice() {
        return oprice;
    }

    public void setOprice(String oprice) {
        this.oprice = oprice;
    }

    public String getCprice() {
        return cprice;
    }

    public void setCprice(String cprice) {
        this.cprice = cprice;
    }

    public String getTime_left() {
        return time_left;
    }

    public void setTime_left(String time_left) {
        this.time_left = time_left;
    }

    public String getGoods_type_name() {
        return goods_type_name;
    }

    public void setGoods_type_name(String goods_type_name) {
        this.goods_type_name = goods_type_name;
    }

    public String getPinpai_icon() {
        return pinpai_icon;
    }

    public void setPinpai_icon(String pinpai_icon) {
        this.pinpai_icon = pinpai_icon;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getCoupon_tips() {
        return coupon_tips;
    }

    public void setCoupon_tips(String coupon_tips) {
        this.coupon_tips = coupon_tips;
    }

    public String getTabname() {
        return tabname;
    }

    public void setTabname(String tabname) {
        this.tabname = tabname;
    }


}
