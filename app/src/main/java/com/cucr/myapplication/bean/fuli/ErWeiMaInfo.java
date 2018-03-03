package com.cucr.myapplication.bean.fuli;

/**
 * Created by cucr on 2018/3/2.
 */

public class ErWeiMaInfo {
    private String name;
    private String number;

    public ErWeiMaInfo(String name, String number) {
        this.name = name;
        this.number = number;
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
}
