package com.cucr.myapplication.bean.Hyt;

import java.util.List;

/**
 * Created by cucr on 2018/2/28.
 */

public class HytMembers {

    /**
     * errorMsg :
     * rows : [{"hytId":3,"id":4,"integral":51,"roleId":2,"sort":null,"startId":33,"user":{"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"Yin","phone":"18672342353","realName":"殷文其","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/591dae54-5f45-4d6f-8a44-df0db919f987.png"}}]
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
         * hytId : 3
         * id : 4
         * integral : 51
         * roleId : 2
         * sort : null
         * startId : 33
         * user : {"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"Yin","phone":"18672342353","realName":"殷文其","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/591dae54-5f45-4d6f-8a44-df0db919f987.png"}
         */

        private int hytId;
        private int id;
        private int integral;
        private int roleId;
        private Object sort;
        private int startId;
        private UserBean user;
        private boolean isSel;

        public boolean isSel() {
            return isSel;
        }

        public void setSel(boolean sel) {
            isSel = sel;
        }

        public int getHytId() {
            return hytId;
        }

        public void setHytId(int hytId) {
            this.hytId = hytId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public int getStartId() {
            return startId;
        }

        public void setStartId(int startId) {
            this.startId = startId;
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
             * id : 31
             * msgRegId : 190e35f7e079f5a7e78
             * name : Yin
             * phone : 18672342353
             * realName : 殷文其
             * signName :
             * startCost : null
             * userHeadPortrait : /static/yuanshi_image/591dae54-5f45-4d6f-8a44-df0db919f987.png
             */

            private String belongCompany;
            private int id;
            private String msgRegId;
            private String name;
            private String phone;
            private String realName;
            private String signName;
            private Object startCost;
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

            public Object getStartCost() {
                return startCost;
            }

            public void setStartCost(Object startCost) {
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
