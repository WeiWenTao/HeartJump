package com.cucr.myapplication.bean.Hyt;

import java.util.List;

/**
 * Created by cucr on 2018/3/1.
 */

public class HytLockList {

    /**
     * errorMsg :
     * rows : [{"userHeadPortrait":"/static/yuanshi_image/591dae54-5f45-4d6f-8a44-df0db919f987.png","name":"Yin","time":"2018-02-28 16:16:17","userId":31}]
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

    public static class RowsBean {
        /**
         * userHeadPortrait : /static/yuanshi_image/591dae54-5f45-4d6f-8a44-df0db919f987.png
         * name : Yin
         * time : 2018-02-28 16:16:17
         * userId : 31
         */

        private String userHeadPortrait;
        private String name;
        private String time;
        private int userId;
        private boolean isSel;

        public boolean isSel() {
            return isSel;
        }

        public void setSel(boolean sel) {
            isSel = sel;
        }

        public String getUserHeadPortrait() {
            return userHeadPortrait;
        }

        public void setUserHeadPortrait(String userHeadPortrait) {
            this.userHeadPortrait = userHeadPortrait;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
