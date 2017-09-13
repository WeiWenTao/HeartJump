package com.cucr.myapplication.model.login;

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
