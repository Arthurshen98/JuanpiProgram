package com.sf.main.juanpiprogram.sf.entities;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 15-11-18.
 */
//详情页面的WebView
public class Information {
    @JSONField(name = "id")
    private String id;
    @JSONField(name = "h5_url")
    private String h5_url;

    public Information() {
    }

    public Information(String id, String h5_url) {
        this.id = id;
        this.h5_url = h5_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getH5_url() {
        return h5_url;
    }

    public void setH5_url(String h5_url) {
        this.h5_url = h5_url;
    }
}
