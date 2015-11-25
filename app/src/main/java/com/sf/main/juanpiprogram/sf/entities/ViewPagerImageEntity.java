package com.sf.main.juanpiprogram.sf.entities;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 15-11-16.
 */
public class ViewPagerImageEntity {

    @JSONField(name="id")
    private String id;
    @JSONField(name="url")
    private String url;
    @JSONField(name="pic")
    private String pic;

    public ViewPagerImageEntity() {
    }

    public ViewPagerImageEntity(String id, String url, String pic) {
        this.id = id;
        this.url = url;
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "ViewPagerImageEntity{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
