package com.cucr.myapplication.bean.Hyt;

import java.util.List;

/**
 * Created by cucr on 2018/1/30.
 */

public class YyhdSupports {

    /**
     * errorMsg :
     * rows : [{"activeId":7,"amount":1,"id":1,"user":{"belongCompany":"","id":1,"msgRegId":"140fe1da9e91a1918ed","name":"超级管理员","phone":"","realName":"","signName":"1","startCost":null,"userHeadPortrait":""}},{"activeId":7,"amount":10,"id":2,"user":{"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"wiki","phone":"18672342353","realName":"","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"}},{"activeId":7,"amount":10,"id":3,"user":{"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"wiki","phone":"18672342353","realName":"","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"}}]
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
         * activeId : 7
         * amount : 1
         * id : 1
         * user : {"belongCompany":"","id":1,"msgRegId":"140fe1da9e91a1918ed","name":"超级管理员","phone":"","realName":"","signName":"1","startCost":null,"userHeadPortrait":""}
         */

        private int activeId;
        private int amount;
        private int id;
        private UserBean user;
        private String createTime;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getActiveId() {
            return activeId;
        }

        public void setActiveId(int activeId) {
            this.activeId = activeId;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * belongCompany :
             * id : 1
             * msgRegId : 140fe1da9e91a1918ed
             * name : 超级管理员
             * phone :
             * realName :
             * signName : 1
             * startCost : null
             * userHeadPortrait :
             */

            private String belongCompany;
            private int id;
            private String msgRegId;
            private String name;
            private String phone;
            private String realName;
            private String signName;
            private int startCost;
            private String userHeadPortrait;

            public String getBelongCompany() {
                return belongCompany;
            }

            public void setBelongCompany(String belongCompany) {
                this.belongCompany = belongCompany;
            }

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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getSignName() {
                return signName;
            }

            public void setSignName(String signName) {
                this.signName = signName;
            }

            public int getStartCost() {
                return startCost;
            }

            public void setStartCost(int startCost) {
                this.startCost = startCost;
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
