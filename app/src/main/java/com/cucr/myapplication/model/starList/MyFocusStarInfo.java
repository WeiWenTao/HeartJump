package com.cucr.myapplication.model.starList;

import java.util.List;

/**
 * Created by cucr on 2017/9/6.
 */

public class MyFocusStarInfo {

    /**
     * errorMsg :
     * rows : [{"followTime":"2017-08-24 14:15:05","startId":5,"startName":"微文滔","startPicUrl":"/static/ys_image/c9856e8c-1a5b-4730-9262-603116346cd3.jpg","userId":14}]
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
         * followTime : 2017-08-24 14:15:05
         * startId : 5
         * startName : 微文滔
         * startPicUrl : /static/ys_image/c9856e8c-1a5b-4730-9262-603116346cd3.jpg
         * userId : 14
         */

        private String followTime;
        private int startId;
        private String startName;
        private String startPicUrl;
        private int userId;

        public String getFollowTime() {
            return followTime;
        }

        public void setFollowTime(String followTime) {
            this.followTime = followTime;
        }

        public int getStartId() {
            return startId;
        }

        public void setStartId(int startId) {
            this.startId = startId;
        }

        public String getStartName() {
            return startName;
        }

        public void setStartName(String startName) {
            this.startName = startName;
        }

        public String getStartPicUrl() {
            return startPicUrl;
        }

        public void setStartPicUrl(String startPicUrl) {
            this.startPicUrl = startPicUrl;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
