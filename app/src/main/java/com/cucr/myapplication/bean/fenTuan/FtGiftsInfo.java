package com.cucr.myapplication.bean.fenTuan;

import java.util.List;

/**
 * Created by cucr on 2017/11/7.
 */

public class FtGiftsInfo {

    /**
     * errorMsg :
     * rows : [{"id":0,"name":"星币","picUrl":"","proportion":1},{"id":1,"name":"道具1","picUrl":"","proportion":1},{"id":2,"name":"道具2","picUrl":"","proportion":1},{"id":3,"name":"道具3","picUrl":"","proportion":1}]
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
         * id : 0
         * name : 星币
         * picUrl :
         * proportion : 1
         */

        private int id;
        private String name;
        private String picUrl;
        private int proportion;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public int getProportion() {
            return proportion;
        }

        public void setProportion(int proportion) {
            this.proportion = proportion;
        }
    }
}
