package com.cucr.myapplication.model.fenTuan;

import java.util.List;

/**
 * Created by cucr on 2017/11/7.
 */

public class FtGiftsInfo {

    /**
     * errorMsg :
     * rows : [{"actionCode":"ideaMoney","groupFild":null,"id":15,"keyFild":"1","remark":"虚拟货币1","sort":null,"valueFild":"20"},{"actionCode":"ideaMoney","groupFild":null,"id":16,"keyFild":"2","remark":"虚拟货币1","sort":null,"valueFild":"30"},{"actionCode":"ideaMoney","groupFild":null,"id":17,"keyFild":"3","remark":"虚拟货币1","sort":null,"valueFild":"40"}]
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
         * actionCode : ideaMoney
         * groupFild : null
         * id : 15
         * keyFild : 1
         * remark : 虚拟货币1
         * sort : null
         * valueFild : 20
         */

        private String actionCode;
        private Object groupFild;
        private int id;
        private String keyFild;
        private String remark;
        private Object sort;
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

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public String getValueFild() {
            return valueFild;
        }

        public void setValueFild(String valueFild) {
            this.valueFild = valueFild;
        }

        @Override
        public String toString() {
            return "RowsBean{" +
                    "actionCode='" + actionCode + '\'' +
                    ", groupFild=" + groupFild +
                    ", id=" + id +
                    ", keyFild='" + keyFild + '\'' +
                    ", remark='" + remark + '\'' +
                    ", sort=" + sort +
                    ", valueFild='" + valueFild + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FtGiftsInfo{" +
                "errorMsg='" + errorMsg + '\'' +
                ", success=" + success +
                ", total=" + total +
                ", rows=" + rows +
                '}';
    }
}
