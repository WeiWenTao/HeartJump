package com.cucr.myapplication.bean.login;

import java.io.Serializable;

/**
 * Created by cucr on 2018/2/2.
 */

public class ThirdPlaformInfo implements Serializable {

    private String loginType;
    private String openId;
    private String name;
    private String gender;
    private String iconurl;


    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public ThirdPlaformInfo( String loginType, String openId, String name, String gender, String iconurl) {
        this.loginType = loginType;
        this.openId = openId;
        this.name = name;
        this.gender = gender;
        this.iconurl = iconurl;
    }
}
