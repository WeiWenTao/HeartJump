package com.cucr.myapplication.model.fuli;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cucr on 2017/8/30.
 */

public class ActiveInfo implements Serializable {

    /**
     * errorMsg :
     * rows : [{"activeName":"测试福利活动","createTime":"2017-08-16 15:18:07","createUserId":1,"createUserName":"超级管理员","endTime":"2017-08-19 15:17:56","id":6,"locationUrl":"http://www.baidu.com","picUrl":"/static/ys_image/3c39490d-4a36-40fc-9f4f-7fcdbd005979.jpg","startTime":"2017-08-16 15:17:54"}]
     * success : true
     * total : 1
     */

    private String errorMsg;
    private boolean success;
    private int total;
    private List<RowsBean> rows;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean implements Serializable{
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
}
