package com.cucr.myapplication.bean.fuli;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cucr on 2017/8/25.
 */

public class DuiHuanGoosInfo implements Serializable  {

    /**
     * rows : [{"id":3,"shopContent":"测试的","shopName":"测试商品1","shopPicUrl":"/static/ys_image/8923d563-ed3d-4a8c-9742-890c51ff3439.jpg","shopPrice":10}]
     * success : true
     * total : 0
     */

    private boolean success;
    private int total;
    private String errorMsg;

    public DuiHuanGoosInfo(boolean success, int total, String errorMsg, List<RowsBean> rows) {
        this.success = success;
        this.total = total;
        this.errorMsg = errorMsg;
        this.rows = rows;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private List<RowsBean> rows;

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

    @Override
    public String toString() {
        return "DuiHuanGoosInfo{" +
                "success=" + success +
                ", total=" + total +
                ", errorMsg='" + errorMsg + '\'' +
                ", rows=" + rows +
                '}';
    }

    public static class RowsBean implements Serializable {
        /**
         * id : 3
         * shopContent : 测试的
         * shopName : 测试商品1
         * shopPicUrl : /static/ys_image/8923d563-ed3d-4a8c-9742-890c51ff3439.jpg
         * shopPrice : 10
         */

        private int id;
        private String shopContent;
        private String shopName;
        private String shopPicUrl;
        private int shopPrice;
        private int stock;

        public void setStock(int stock) {
            this.stock = stock;
        }


        public int getStock() {
            return stock;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getShopContent() {
            return shopContent;
        }

        public void setShopContent(String shopContent) {
            this.shopContent = shopContent;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopPicUrl() {
            return shopPicUrl;
        }

        public void setShopPicUrl(String shopPicUrl) {
            this.shopPicUrl = shopPicUrl;
        }

        public int getShopPrice() {
            return shopPrice;
        }

        public void setShopPrice(int shopPrice) {
            this.shopPrice = shopPrice;
        }

        @Override
        public String toString() {
            return "RowsBean{" +
                    "id=" + id +
                    ", shopContent='" + shopContent + '\'' +
                    ", shopName='" + shopName + '\'' +
                    ", shopPicUrl='" + shopPicUrl + '\'' +
                    ", stock='" + stock + '\'' +
                    ", shopPrice=" + shopPrice +
                    '}';
        }
    }
}
