package com.sf.main.juanpiprogram.sf.entities;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 用于搜索的历史数据
 */
@Table(name = "SearchHistoryData", execAfterTableCreated="INSERT INTO SearchHistoryData (id,searchData) VALUES (1,'三只松鼠');")
public class SearchHistoryData {
    @Id(column = "id")
    private int id;

    @Column(column = "searchData")
    private String searchData;

    public SearchHistoryData() {
    }

    public SearchHistoryData(String searchData) {
        this.searchData = searchData;
    }

    public String getSearchData() {
        return searchData;
    }

    public void setSearchData(String searchData) {
        this.searchData = searchData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
