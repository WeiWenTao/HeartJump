package com.cucr.myapplication.model.starList;

import java.util.List;

/**
 * Created by cucr on 2017/9/6.
 */

public class MyFocusStarInfo {


    /**
     * errorMsg :
     * rows : [{"followTime":"2017-10-30 10:55:14","id":66,"start":{"id":29,"msgRegId":"123456","name":"笨笨的考拉","realName":"陈长义","signName":"你好","userHeadPortrait":"/static/ys_image/dfdbe4d2-4dbd-486a-9576-b4eb59ef45bc.gif"},"user":{"id":28,"msgRegId":"1104a8979290bcd6bb8","name":"9527","realName":"","signName":"","userHeadPortrait":""}},{"followTime":"2017-10-30 10:55:06","id":65,"start":{"id":33,"msgRegId":"","name":"","realName":"林俊杰","signName":"","userHeadPortrait":"/static/ys_image/7f9a1e51-a248-4165-a91a-fcfbaf1e1ccf.gif"},"user":{"id":28,"msgRegId":"1104a8979290bcd6bb8","name":"9527","realName":"","signName":"","userHeadPortrait":""}},{"followTime":"2017-10-30 10:54:53","id":64,"start":{"id":32,"msgRegId":"","name":"","realName":"周杰伦","signName":"","userHeadPortrait":"/static/ys_image/903d9168-68f3-4410-8d24-28b675b0409b.gif"},"user":{"id":28,"msgRegId":"1104a8979290bcd6bb8","name":"9527","realName":"","signName":"","userHeadPortrait":""}}]
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
         * followTime : 2017-10-30 10:55:14
         * id : 66
         * start : {"id":29,"msgRegId":"123456","name":"笨笨的考拉","realName":"陈长义","signName":"你好","userHeadPortrait":"/static/ys_image/dfdbe4d2-4dbd-486a-9576-b4eb59ef45bc.gif"}
         * user : {"id":28,"msgRegId":"1104a8979290bcd6bb8","name":"9527","realName":"","signName":"","userHeadPortrait":""}
         */

        private String followTime;
        private int id;
        private StartBean start;
        private UserBean user;

        public String getFollowTime() {
            return followTime;
        }

        public void setFollowTime(String followTime) {
            this.followTime = followTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public StartBean getStart() {
            return start;
        }

        public void setStart(StartBean start) {
            this.start = start;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class StartBean {
            /**
             * id : 29
             * msgRegId : 123456
             * name : 笨笨的考拉
             * realName : 陈长义
             * signName : 你好
             * userHeadPortrait : /static/ys_image/dfdbe4d2-4dbd-486a-9576-b4eb59ef45bc.gif
             */

            private int id;
            private String msgRegId;
            private String name;
            private String realName;
            private String signName;
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

            public String getUserHeadPortrait() {
                return userHeadPortrait;
            }

            public void setUserHeadPortrait(String userHeadPortrait) {
                this.userHeadPortrait = userHeadPortrait;
            }

            @Override
            public String toString() {
                return "StartBean{" +
                        "id=" + id +
                        ", msgRegId='" + msgRegId + '\'' +
                        ", name='" + name + '\'' +
                        ", realName='" + realName + '\'' +
                        ", signName='" + signName + '\'' +
                        ", userHeadPortrait='" + userHeadPortrait + '\'' +
                        '}';
            }
        }

        public static class UserBean {
            /**
             * id : 28
             * msgRegId : 1104a8979290bcd6bb8
             * name : 9527
             * realName :
             * signName :
             * userHeadPortrait :
             */

            private int id;
            private String msgRegId;
            private String name;
            private String realName;
            private String signName;
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

            public String getUserHeadPortrait() {
                return userHeadPortrait;
            }

            public void setUserHeadPortrait(String userHeadPortrait) {
                this.userHeadPortrait = userHeadPortrait;
            }

            @Override
            public String toString() {
                return "UserBean{" +
                        "id=" + id +
                        ", msgRegId='" + msgRegId + '\'' +
                        ", name='" + name + '\'' +
                        ", realName='" + realName + '\'' +
                        ", signName='" + signName + '\'' +
                        ", userHeadPortrait='" + userHeadPortrait + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "RowsBean{" +
                    "followTime='" + followTime + '\'' +
                    ", id=" + id +
                    ", start=" + start +
                    ", user=" + user +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MyFocusStarInfo{" +
                "errorMsg='" + errorMsg + '\'' +
                ", success=" + success +
                ", total=" + total +
                ", rows=" + rows +
                '}';
    }
}
