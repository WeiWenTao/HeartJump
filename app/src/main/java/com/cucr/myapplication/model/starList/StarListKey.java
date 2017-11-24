package com.cucr.myapplication.model.starList;

import java.util.List;

/**
 * Created by cucr on 2017/11/23.
 */

public class StarListKey {

    /**
     * errorMsg :
     * rows : [{"actionCode":"userType","groupFild":null,"id":2,"keyFild":"1","remark":"","sort":1,"valueFild":"演员"},{"actionCode":"userType","groupFild":null,"id":3,"keyFild":"2","remark":"","sort":2,"valueFild":"歌手"},{"actionCode":"userType","groupFild":null,"id":4,"keyFild":"3","remark":"","sort":3,"valueFild":"主持"},{"actionCode":"userType","groupFild":null,"id":5,"keyFild":"4","remark":"","sort":4,"valueFild":"模特"},{"actionCode":"userType","groupFild":null,"id":6,"keyFild":"5","remark":"","sort":5,"valueFild":"相声"},{"actionCode":"userType","groupFild":null,"id":7,"keyFild":"6","remark":"","sort":6,"valueFild":"其他"}]
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
         * actionCode : userType
         * groupFild : null
         * id : 2
         * keyFild : 1
         * remark :
         * sort : 1
         * valueFild : 演员
         */

        private String actionCode;
        private Object groupFild;
        private int id;
        private String keyFild;
        private String remark;
        private int sort;
        private String valueFild;

        public String getActionCode() {
            return actionCode;
        }

        public void setActionCode(String actionCode) {
            this.actionCode = actionCode;
        }

        public Object getGroupFild() {
            return groupFild;
        }

        public void setGroupFild(Object groupFild) {
            this.groupFild = groupFild;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKeyFild() {
            return keyFild;
        }

        public void setKeyFild(String keyFild) {
            this.keyFild = keyFild;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getValueFild() {
            return valueFild;
        }

        public void setValueFild(String valueFild) {
            this.valueFild = valueFild;
        }
    }
}
