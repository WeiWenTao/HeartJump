package com.cucr.myapplication.bean.login;

/**
 * Created by 911 on 2017/8/14.
 */

public class LoadSuccess {

    /**
     * userId : 1
     * sign : f0ddd2a5-ab6b-4298-a835-e43c19c00f51
     */

    private int userId;
    private String sign;
    private int roleId;
    private String companyName;
    private String companyConcat;
    private String userHeadPortrait;
    private String name;
    private String phone;
    private int loginStatu;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getLoginStatu() {
        return loginStatu;
    }

    public void setLoginStatu(int loginStatu) {
        this.loginStatu = loginStatu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserHeadPortrait() {
        return userHeadPortrait;
    }

    public void setUserHeadPortrait(String userHeadPortrait) {
        this.userHeadPortrait = userHeadPortrait;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyConcat() {
        return companyConcat;
    }

    public void setCompanyConcat(String companyConcat) {
        this.companyConcat = companyConcat;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
