package com.cucr.myapplication.bean.EditPersonalInfo;

/**
 * Created by 911 on 2017/8/15.
 */

public class PersonalInfo {
    private int userId;
    private String sign;
    private String name;
    private int sex;
    private String birthday;
    private String provinceId;
    private String cityId;
    private String signName;
    private String userHeadPortrait;

    public PersonalInfo(int userId, String sign, String name, int sex, String birthday, String provinceId, String cityId, String signName, String userHeadPortrait) {
        this.userId = userId;
        this.sign = sign;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday + " 00:00:00";
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.signName = signName;
        this.userHeadPortrait = userHeadPortrait;
    }

    public PersonalInfo() {
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public void setuserHeadPortrait(String userHeadPortrait) {
        this.userHeadPortrait = userHeadPortrait;
    }

    public int getUserId() {
        return userId;
    }

    public String getSign() {
        return sign;
    }

    public String getName() {
        return name;
    }

    public int getSex() {
        return sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getSignName() {
        return signName;
    }

    public String getuserHeadPortrait() {
        return userHeadPortrait;
    }

    @Override
    public String toString() {
        return "PersonalInfo{" +
                "userId='" + userId + '\'' +
                ", sign='" + sign + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", signName='" + signName + '\'' +
                ", userHeadPortrait='" + userHeadPortrait + '\'' +
                '}';
    }
}
