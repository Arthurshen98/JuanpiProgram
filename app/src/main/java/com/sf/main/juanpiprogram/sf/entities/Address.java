package com.sf.main.juanpiprogram.sf.entities;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 15-11-25.
 */
@Table(name="Address",execAfterTableCreated = "")
public class Address {

    @Id(column = "id")
    @NoAutoIncrement
    private String id;
    @Column(column = "name")
    private String name;
    @Column(column = "number")
    private String number;
    @Column(column = "address")
    private String address;

    public Address() {
    }

    public Address(String id, String name, String number, String address) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
