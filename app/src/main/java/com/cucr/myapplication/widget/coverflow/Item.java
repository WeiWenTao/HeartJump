package com.cucr.myapplication.widget.coverflow;

import java.io.Serializable;

/**
 * Created by zhouweilong on 15/12/14.
 */
public class Item implements Serializable {
    /**
     * activeName : 测试福利活动
     * createTime : 2017-08-16 15:18:07
     * createUserId : 1
     * createUserName : 超级管理员
     * endTime : 2017-08-19 15:17:56
     * id : 6
     * locationUrl : http://www.baidu.com
     * picUrl : /static/ys_image/3c39490d-4a36-40fc-9f4f-7fcdbd005979.jpg
     * startTime : 2017-08-16 15:17:54
     */

    private String activeName;
    private String createTime;
    private int createUserId;
    private String createUserName;
    private String endTime;
    private int id;
    private String locationUrl;
    private String picUrl;
    private String startTime;
    private int position;

    @Override
    public String toString() {
        return "Item{" +
                "activeName='" + activeName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createUserId=" + createUserId +
                ", createUserName='" + createUserName + '\'' +
                ", endTime='" + endTime + '\'' +
                ", id=" + id +
                ", locationUrl='" + locationUrl + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", startTime='" + startTime + '\'' +
                ", position=" + position +
                '}';
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
