package com.cucr.myapplication.bean.setting;

import java.io.Serializable;

/**
 * Created by 911 on 2017/4/28.
 */

public class LocationData implements Serializable {
    private int id;
    private String code;
    private String name;
    private String pCode;

    public LocationData(int id, String code, String name, String pCode) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.pCode = pCode;
    }

    public LocationData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    @Override
    public String toString() {
        return "LocationData{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", pCode='" + pCode + '\'' +
                '}';
    }
}
