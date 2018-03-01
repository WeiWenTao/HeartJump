package com.cucr.myapplication.bean.user;

import java.util.List;

/**
 * Created by cucr on 2018/3/1.
 */

public class XbRecord {

    /**
     * errorMsg :
     * rows : [{"accountType":0,"afterAmount":100,"beforeAmount":200,"consumptionAmount":100,"consumptionOrder":"1","consumptionTime":"2017-11-01 16:25:57","id":4,"shopName":"见面会:测试标题1","syr":null,"type":3,"user":{"id":31,"msgRegId":"13065ffa4e32b2e11e1","name":"wiki","userHeadPortrait":""}},{"accountType":0,"afterAmount":0,"beforeAmount":100,"consumptionAmount":100,"consumptionOrder":"1","consumptionTime":"2017-11-01 16:29:25","id":6,"shopName":"见面会:测试标题1","syr":null,"type":3,"user":{"id":31,"msgRegId":"13065ffa4e32b2e11e1","name":"wiki","userHeadPortrait":""}},{"accountType":0,"afterAmount":50,"beforeAmount":0,"consumptionAmount":50,"consumptionOrder":"","consumptionTime":"2017-11-02 12:16:47","id":7,"shopName":"道具1兑换星币","syr":null,"type":6,"user":{"id":31,"msgRegId":"13065ffa4e32b2e11e1","name":"wiki","userHeadPortrait":""}},{"accountType":0,"afterAmount":50,"beforeAmount":1050,"consumptionAmount":1000,"consumptionOrder":"","consumptionTime":"2017-11-02 15:39:34","id":8,"shopName":"星币提现","syr":null,"type":5,"user":{"id":31,"msgRegId":"13065ffa4e32b2e11e1","name":"wiki","userHeadPortrait":""}}]
     * success : true
     * total : 4
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
         * accountType : 0
         * afterAmount : 100
         * beforeAmount : 200
         * consumptionAmount : 100
         * consumptionOrder : 1
         * consumptionTime : 2017-11-01 16:25:57
         * id : 4
         * shopName : 见面会:测试标题1
         * syr : null
         * type : 3
         * user : {"id":31,"msgRegId":"13065ffa4e32b2e11e1","name":"wiki","userHeadPortrait":""}
         */

        private int accountType;
        private int afterAmount;
        private int beforeAmount;
        private int consumptionAmount;
        private String consumptionOrder;
        private String consumptionTime;
        private int id;
        private String shopName;
        private Object syr;
        private int type;
        private UserBean user;

        public int getAccountType() {
            return accountType;
        }

        public void setAccountType(int accountType) {
            this.accountType = accountType;
        }

        public int getAfterAmount() {
            return afterAmount;
        }

        public void setAfterAmount(int afterAmount) {
            this.afterAmount = afterAmount;
        }

        public int getBeforeAmount() {
            return beforeAmount;
        }

        public void setBeforeAmount(int beforeAmount) {
            this.beforeAmount = beforeAmount;
        }

        public int getConsumptionAmount() {
            return consumptionAmount;
        }

        public void setConsumptionAmount(int consumptionAmount) {
            this.consumptionAmount = consumptionAmount;
        }

        public String getConsumptionOrder() {
            return consumptionOrder;
        }

        public void setConsumptionOrder(String consumptionOrder) {
            this.consumptionOrder = consumptionOrder;
        }

        public String getConsumptionTime() {
            return consumptionTime;
        }

        public void setConsumptionTime(String consumptionTime) {
            this.consumptionTime = consumptionTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public Object getSyr() {
            return syr;
        }

        public void setSyr(Object syr) {
            this.syr = syr;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 31
             * msgRegId : 13065ffa4e32b2e11e1
             * name : wiki
             * userHeadPortrait :
             */

            private int id;
            private String msgRegId;
            private String name;
            private String userHeadPortrait;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMsgRegId() {
                return msgRegId;
            }

            public void setMsgRegId(String msgRegId) {
                this.msgRegId = msgRegId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUserHeadPortrait() {
                return userHeadPortrait;
            }

            public void setUserHeadPortrait(String userHeadPortrait) {
                this.userHeadPortrait = userHeadPortrait;
            }
        }
    }
}
