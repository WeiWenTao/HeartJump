package com.cucr.myapplication.bean.fuli;

import java.util.List;

/**
 * Created by cucr on 2018/3/1.
 */

public class MyActives {

    /**
     * errorMsg :
     * rows : [{"active":{"activeName":"测试福利活动报名","cansignUp":true,"createTime":"2017-12-14 15:17:24","createUserId":1,"createUserName":"超级管理员","detailsPic":"/static/ys_image/b51c0edb-ef74-4d82-9851-dace0deed7bc.jpg","endTime":"2017-12-15 15:15:14","id":13,"locationUrl":"","picUrl":"/static/ys_image/880a3b31-c4ad-449f-90c9-b5a9f4bafaef.jpg","startTime":"2017-12-14 15:15:09"},"id":9,"orderNo":"4536457657","signUpTime":"2018-01-02 15:03:03","user":{"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"wiki","phone":"18672342353","realName":"","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"}},{"active":{"activeName":"心跳互娱","cansignUp":true,"createTime":"2017-12-21 12:07:30","createUserId":1,"createUserName":"超级管理员","detailsPic":"/static/yuanshi_image/244a989a-5064-4b41-adcb-5407c738cb6e.jpg","endTime":"2017-12-30 12:06:39","id":15,"locationUrl":"","picUrl":"/static/yuanshi_image/1238d220-cdc4-4354-a217-dc893f0040ec.png","startTime":"2017-12-21 12:06:35"},"id":8,"orderNo":"4123543654654","signUpTime":"2018-01-02 15:02:54","user":{"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"wiki","phone":"18672342353","realName":"","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"}}]
     * success : true
     * total : 2
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
         * active : {"activeName":"测试福利活动报名","cansignUp":true,"createTime":"2017-12-14 15:17:24","createUserId":1,"createUserName":"超级管理员","detailsPic":"/static/ys_image/b51c0edb-ef74-4d82-9851-dace0deed7bc.jpg","endTime":"2017-12-15 15:15:14","id":13,"locationUrl":"","picUrl":"/static/ys_image/880a3b31-c4ad-449f-90c9-b5a9f4bafaef.jpg","startTime":"2017-12-14 15:15:09"}
         * id : 9
         * orderNo : 4536457657
         * signUpTime : 2018-01-02 15:03:03
         * user : {"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"wiki","phone":"18672342353","realName":"","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"}
         */

        private ActiveBean active;
        private int id;
        private String orderNo;
        private String signUpTime;
        private UserBean user;

        public ActiveBean getActive() {
            return active;
        }

        public void setActive(ActiveBean active) {
            this.active = active;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getSignUpTime() {
            return signUpTime;
        }

        public void setSignUpTime(String signUpTime) {
            this.signUpTime = signUpTime;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class ActiveBean {
            /**
             * activeName : 测试福利活动报名
             * cansignUp : true
             * createTime : 2017-12-14 15:17:24
             * createUserId : 1
             * createUserName : 超级管理员
             * detailsPic : /static/ys_image/b51c0edb-ef74-4d82-9851-dace0deed7bc.jpg
             * endTime : 2017-12-15 15:15:14
             * id : 13
             * locationUrl :
             * picUrl : /static/ys_image/880a3b31-c4ad-449f-90c9-b5a9f4bafaef.jpg
             * startTime : 2017-12-14 15:15:09
             */

            private String activeName;
            private boolean cansignUp;
            private String createTime;
            private int createUserId;
            private String createUserName;
            private String detailsPic;
            private String endTime;
            private int id;
            private String locationUrl;
            private String picUrl;
            private String startTime;

            public String getActiveName() {
                return activeName;
            }

            public void setActiveName(String activeName) {
                this.activeName = activeName;
            }

            public boolean isCansignUp() {
                return cansignUp;
            }

            public void setCansignUp(boolean cansignUp) {
                this.cansignUp = cansignUp;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getCreateUserId() {
                return createUserId;
            }

            public void setCreateUserId(int createUserId) {
                this.createUserId = createUserId;
            }

            public String getCreateUserName() {
                return createUserName;
            }

            public void setCreateUserName(String createUserName) {
                this.createUserName = createUserName;
            }

            public String getDetailsPic() {
                return detailsPic;
            }

            public void setDetailsPic(String detailsPic) {
                this.detailsPic = detailsPic;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLocationUrl() {
                return locationUrl;
            }

            public void setLocationUrl(String locationUrl) {
                this.locationUrl = locationUrl;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }
        }

        public static class UserBean {
            /**
             * belongCompany :
             * id : 31
             * msgRegId : 190e35f7e079f5a7e78
             * name : wiki
             * phone : 18672342353
             * realName :
             * signName :
             * startCost : null
             * userHeadPortrait : /static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png
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
