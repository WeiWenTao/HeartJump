package com.cucr.myapplication.model.fenTuan;

import java.util.List;

/**
 * Created by cucr on 2017/11/9.
 */

public class DaShangListInfo {

    /**
     * errorMsg :
     * rows : [{"acceptUser":{"id":29,"msgRegId":"123456","name":"笨笨的考拉","userHeadPortrait":"/static/ys_image/dfdbe4d2-4dbd-486a-9576-b4eb59ef45bc.gif"},"id":8,"rewardContentId":134,"rewardMoney":1,"rewardTime":"2017-11-08 14:24:44","rewardType":{"id":1,"name":"道具1","picUrl":"","proportion":1},"rewardUser":{"id":31,"msgRegId":"13065ffa4e32b2e11e1","name":"wiki","userHeadPortrait":""}}]
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
         * acceptUser : {"id":29,"msgRegId":"123456","name":"笨笨的考拉","userHeadPortrait":"/static/ys_image/dfdbe4d2-4dbd-486a-9576-b4eb59ef45bc.gif"}
         * id : 8
         * rewardContentId : 134
         * rewardMoney : 1
         * rewardTime : 2017-11-08 14:24:44
         * rewardType : {"id":1,"name":"道具1","picUrl":"","proportion":1}
         * rewardUser : {"id":31,"msgRegId":"13065ffa4e32b2e11e1","name":"wiki","userHeadPortrait":""}
         */

        private AcceptUserBean acceptUser;
        private int id;
        private int rewardContentId;
        private int rewardMoney;
        private String rewardTime;
        private RewardTypeBean rewardType;
        private RewardUserBean rewardUser;

        public AcceptUserBean getAcceptUser() {
            return acceptUser;
        }

        public void setAcceptUser(AcceptUserBean acceptUser) {
            this.acceptUser = acceptUser;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRewardContentId() {
            return rewardContentId;
        }

        public void setRewardContentId(int rewardContentId) {
            this.rewardContentId = rewardContentId;
        }

        public int getRewardMoney() {
            return rewardMoney;
        }

        public void setRewardMoney(int rewardMoney) {
            this.rewardMoney = rewardMoney;
        }

        public String getRewardTime() {
            return rewardTime;
        }

        public void setRewardTime(String rewardTime) {
            this.rewardTime = rewardTime;
        }

        public RewardTypeBean getRewardType() {
            return rewardType;
        }

        public void setRewardType(RewardTypeBean rewardType) {
            this.rewardType = rewardType;
        }

        public RewardUserBean getRewardUser() {
            return rewardUser;
        }

        public void setRewardUser(RewardUserBean rewardUser) {
            this.rewardUser = rewardUser;
        }

        public static class AcceptUserBean {
            /**
             * id : 29
             * msgRegId : 123456
             * name : 笨笨的考拉
             * userHeadPortrait : /static/ys_image/dfdbe4d2-4dbd-486a-9576-b4eb59ef45bc.gif
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

            @Override
            public String toString() {
                return "AcceptUserBean{" +
                        "id=" + id +
                        ", msgRegId='" + msgRegId + '\'' +
                        ", name='" + name + '\'' +
                        ", userHeadPortrait='" + userHeadPortrait + '\'' +
                        '}';
            }
        }

        public static class RewardTypeBean {
            /**
             * id : 1
             * name : 道具1
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

            @Override
            public String toString() {
                return "RewardTypeBean{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", picUrl='" + picUrl + '\'' +
                        ", proportion=" + proportion +
                        '}';
            }
        }

        public static class RewardUserBean {

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

            @Override
            public String toString() {
                return "RewardUserBean{" +
                        "id=" + id +
                        ", msgRegId='" + msgRegId + '\'' +
                        ", name='" + name + '\'' +
                        ", userHeadPortrait='" + userHeadPortrait + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "RowsBean{" +
                    "acceptUser=" + acceptUser +
                    ", id=" + id +
                    ", rewardContentId=" + rewardContentId +
                    ", rewardMoney=" + rewardMoney +
                    ", rewardTime='" + rewardTime + '\'' +
                    ", rewardType=" + rewardType +
                    ", rewardUser=" + rewardUser +
                    '}';
        }
    }
}
