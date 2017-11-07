package com.cucr.myapplication.model.fenTuan;

import java.util.List;

/**
 * Created by cucr on 2017/11/7.
 */

public class FtBackpackInfo {

    /**
     * errorMsg :
     * rows : [{"balance":100,"id":1,"userAccountType":1,"userId":31}]
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
        @Override
        public String toString() {
            return "RowsBean{" +
                    "balance=" + balance +
                    ", id=" + id +
                    ", userAccountType=" + userAccountType +
                    ", userId=" + userId +
                    '}';
        }

        /**
         * balance : 100
         * id : 1
         * userAccountType : 1
         * userId : 31
         */

        private int balance;
        private int id;
        private int userAccountType;
        private int userId;

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserAccountType() {
            return userAccountType;
        }

        public void setUserAccountType(int userAccountType) {
            this.userAccountType = userAccountType;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

    @Override
    public String toString() {
        return "FtBackpackInfo{" +
                "errorMsg='" + errorMsg + '\'' +
                ", success=" + success +
                ", total=" + total +
                ", rows=" + rows +
                '}';
    }
}
