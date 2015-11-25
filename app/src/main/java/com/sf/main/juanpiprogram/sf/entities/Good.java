package com.sf.main.juanpiprogram.sf.entities;

import com.alibaba.fastjson.annotation.JSONField;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2015/11/21.
 */
@Table(name="Entity",execAfterTableCreated = "")
public class Good {
    @Id(column = "id")
    @JSONField(name = "id")
    private String id;
    @Column(column = "goods_id")
    @JSONField(name = "goods_id")
    private String goods_id;
    @Column(column = "num")
    private int num;
    @Column(column = "title")
    @JSONField(name = "title")
    private String title;
    @Column(column = "oprice")
    @JSONField(name = "oprice")
    private String oprice;
    @Column(column = "cprice")
    @JSONField(name = "cprice")
    private String cprice;
    @Column(column = "point")
    @JSONField(name = "point")
    private String point;
    @Column(column = "about_juanpi_url")
    @JSONField(name = "about_juanpi_url")
    private String about_juanpi_url;

    public Good() {
    }

    public Good(String id, String goods_id, int num, String title, String oprice, String cprice, String point, String about_juanpi_url) {
        this.id = id;
        this.goods_id = goods_id;
        this.num = num;
        this.title = title;
        this.oprice = oprice;
        this.cprice = cprice;
        this.point = point;
        this.about_juanpi_url = about_juanpi_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getAbout_juanpi_url() {
        return about_juanpi_url;
    }

    public void setAbout_juanpi_url(String about_juanpi_url) {
        this.about_juanpi_url = about_juanpi_url;
    }
}
