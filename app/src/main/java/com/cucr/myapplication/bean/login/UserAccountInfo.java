package com.cucr.myapplication.bean.login;

/**
 * Created by cucr on 2017/12/25.
 */

public class UserAccountInfo {
    private String userName;
    private String passWord;
    private String userAddress;
    private String nickName;
    private int userId;
    private String sign;
    private int roleId;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public UserAccountInfo(int userId, String sign, String userName, String passWord, String userAddress, String nickName, int roleId) {
        this.userName = userName;
        this.passWord = passWord;
        this.userAddress = userAddress;
        this.nickName = nickName;
        this.userId = userId;
        this.sign = sign;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    @Override
    public String toString() {
        return "UserAccountInfo{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
