package com.cucr.myapplication.bean.dabang;

import java.util.List;

/**
 * Created by cucr on 2017/10/28.
 */

public class BangDanInfo {

    /**
     * errorMsg :
     * rows : [{"id":5,"realName":"笨笨的考拉","userHeadPortrait":"/static/ys_image/3956c17e-6884-4854-91aa-e6078dacc76e.jpg","userMoney":9970,"userPicCover":"/static/ys_image/bcd38244-a2d5-42bb-b225-e75d3b5b0bcd.gif"},{"id":17,"realName":"殷文其","userHeadPortrait":"/static/ys_image/933dd507-e2a2-4e33-a573-3271cbcbfedf.png","userMoney":0,"userPicCover":"/static/ys_image/08ab4f9e-0981-4cc3-a0e7-c11f76d30f1a.png"},{"id":22,"realName":"111","userHeadPortrait":null,"userMoney":0,"userPicCover":null}]
     * success : true
     * total : 3
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
         * id : 5
         * realName : 笨笨的考拉
         * userHeadPortrait : /static/ys_image/3956c17e-6884-4854-91aa-e6078dacc76e.jpg
         * userMoney : 9970
         * userPicCover : /static/ys_image/bcd38244-a2d5-42bb-b225-e75d3b5b0bcd.gif
         */

        private int id;
        private String realName;
        private String userHeadPortrait;
        private double userMoney;
        private String userPicCover;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getUserHeadPortrait() {
            return userHeadPortrait;
        }

        public void setUserHeadPortrait(String userHeadPortrait) {
            this.userHeadPortrait = userHeadPortrait;
        }

        public double getUserMoney() {
            return userMoney;
        }

        public void setUserMoney(double userMoney) {
            this.userMoney = userMoney;
        }

        public String getUserPicCover() {
            return userPicCover;
        }

        public void setUserPicCover(String userPicCover) {
            this.userPicCover = userPicCover;
        }
    }
}
