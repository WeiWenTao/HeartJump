package com.cucr.myapplication.model.starJourney;

import java.util.List;

/**
 * Created by cucr on 2017/9/15.
 */

public class StarJourneyList {

    /**
     * errorMsg :
     * rows : [{"id":2,"place":"武汉","title":"演唱会","tripTime":"2017-08-24 16:28:18","userId":17,"userName":"周杰伦"},{"id":3,"place":"长沙","title":"演唱会","tripTime":"2017-07-24 16:28:31","userId":17,"userName":"周杰伦"}]
     * success : true
     * total : 0
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

    public static class RowsBean {
        /**
         * id : 2
         * place : 武汉
         * title : 演唱会
         * tripTime : 2017-08-24 16:28:18
         * userId : 17
         * userName : 周杰伦
         */

        private int id;
        private String place;
        private String title;
        private String tripTime;
        private int userId;
        private String userName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTripTime() {
            return tripTime;
        }

        public void setTripTime(String tripTime) {
            this.tripTime = tripTime;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
