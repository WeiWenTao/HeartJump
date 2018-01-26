package com.cucr.myapplication.bean.login;

/**
 * Created by cucr on 2017/12/25.
 */

public class UserAccountInfo {
    private String userName;
    private String passWord;
    private String userAddress;
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public UserAccountInfo(String userName, String passWord, String userAddress, String nickName) {
        this.userName = userName;
        this.passWord = passWord;
        this.userAddress = userAddress;
        this.nickName = nickName;
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
