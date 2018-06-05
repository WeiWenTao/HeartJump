package com.cucr.myapplication.bean.user;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by cucr on 2018/3/24.
 */
@Entity
public class LoadUserInfos {
    @Id
    private Long id;

    private int userId;
    private int roleId;
    private int loginStatu;
    private String phone;
    private String sign;
    private String name;
    private String userHeadPortrait;
    private String token;
    private String companyName;
    private String companyConcat;
    private String passWord;

    public String getPassWord() {
        return this.passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public String getCompanyConcat() {
        return this.companyConcat;
    }
    public void setCompanyConcat(String companyConcat) {
        this.companyConcat = companyConcat;
    }
    public String getCompanyName() {
        return this.companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getUserHeadPortrait() {
        return this.userHeadPortrait;
    }
    public void setUserHeadPortrait(String userHeadPortrait) {
        this.userHeadPortrait = userHeadPortrait;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSign() {
        return this.sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getLoginStatu() {
        return this.loginStatu;
    }
    public void setLoginStatu(int loginStatu) {
        this.loginStatu = loginStatu;
    }
    public int getRoleId() {
        return this.roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public int getUserId() {
        return this.userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1628445337)
    public LoadUserInfos(Long id, int userId, int roleId, int loginStatu,
            String phone, String sign, String name, String userHeadPortrait,
            String token, String companyName, String companyConcat, String passWord) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
        this.loginStatu = loginStatu;
        this.phone = phone;
        this.sign = sign;
        this.name = name;
        this.userHeadPortrait = userHeadPortrait;
        this.token = token;
        this.companyName = companyName;
        this.companyConcat = companyConcat;
        this.passWord = passWord;
    }
    @Generated(hash = 1992145849)
    public LoadUserInfos() {
    }

    @Override
    public String toString() {
        return "LoadUserInfos{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + roleId +
                ", loginStatu=" + loginStatu +
                ", phone='" + phone + '\'' +
                ", sign='" + sign + '\'' +
                ", name='" + name + '\'' +
                ", userHeadPortrait='" + userHeadPortrait + '\'' +
                ", token='" + token + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyConcat='" + companyConcat + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
