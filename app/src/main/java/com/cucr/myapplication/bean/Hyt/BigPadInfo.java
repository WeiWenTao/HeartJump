package com.cucr.myapplication.bean.Hyt;

import java.util.List;

/**
 * Created by cucr on 2018/1/26.
 */

public class BigPadInfo {

    /**
     * errorMsg :
     * rows : [{"address":"雄楚大道虎泉街交汇处","id":1,"picUrl":"/static/yuanshi_image/1f995cdb-2106-4ee3-ba61-795b55bff20b.jpg","price":"1001/天","purpose":"生日应援/纪念日应援","sid":null,"spec":"18.4米X12.28米（260m2）"}]
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
         * address : 雄楚大道虎泉街交汇处
         * id : 1
         * picUrl : /static/yuanshi_image/1f995cdb-2106-4ee3-ba61-795b55bff20b.jpg
         * price : 1001/天
         * purpose : 生日应援/纪念日应援
         * sid : null
         * spec : 18.4米X12.28米（260m2）
         */

        private String address;
        private int id;
        private String picUrl;
        private String price;
        private String purpose;
        private Object sid;
        private String spec;
        private boolean isSel;

        public String getJtll() {
            return Jtll;
        }

        public void setJtll(String jtll) {
            Jtll = jtll;
        }

        private String Jtll;

        public boolean isSel() {
            return isSel;
        }

        public void setSel(boolean sel) {
            isSel = sel;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPurpose() {
            return purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public Object getSid() {
            return sid;
        }

        public void setSid(Object sid) {
            this.sid = sid;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }
    }
}
