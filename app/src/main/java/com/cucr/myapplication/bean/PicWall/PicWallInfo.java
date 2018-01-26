package com.cucr.myapplication.bean.PicWall;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cucr on 2018/1/3.
 */

public class PicWallInfo implements Serializable{

    /**
     * errorMsg :
     * rows : [{"createTime":"2017-12-22 15:55:03","id":1,"statu":1,"picUrl":"","start":{"belongCompany":"","id":29,"msgRegId":"123456","name":"迪丽热巴","phone":"15527609321","realName":"迪丽热巴","signName":"你好","userHeadPortrait":"/static/ys_image/d6c26a83-91ae-42c8-ba9a-b4c94de964f7.jpg"},"user":{"belongCompany":"","id":31,"msgRegId":"13065ffa4e32b2e11e1","name":"wiki","phone":"18672342353","realName":"","signName":"","userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"}}]
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

    public static class RowsBean implements Serializable {
        /**
         * createTime : 2017-12-22 15:55:03
         * id : 1
         * statu : 1
         * picUrl :
         * start : {"belongCompany":"","id":29,"msgRegId":"123456","name":"迪丽热巴","phone":"15527609321","realName":"迪丽热巴","signName":"你好","userHeadPortrait":"/static/ys_image/d6c26a83-91ae-42c8-ba9a-b4c94de964f7.jpg"}
         * user : {"belongCompany":"","id":31,"msgRegId":"13065ffa4e32b2e11e1","name":"wiki","phone":"18672342353","realName":"","signName":"","userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"}
         */

        private String createTime;
        private int id;
        private int statu;
        private String picUrl;
        private StartBean start;
        private UserBean user;
        private int giveUpCount;

        public int getGiveUpCount() {
            return giveUpCount;
        }

        public void setGiveUpCount(int giveUpCount) {
            this.giveUpCount = giveUpCount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatu() {
            return statu;
        }

        public void setStatu(int statu) {
            this.statu = statu;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
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

        public static class StartBean implements Serializable{
            /**
             * belongCompany :
             * id : 29
             * msgRegId : 123456
             * name : 迪丽热巴
             * phone : 15527609321
             * realName : 迪丽热巴
             * signName : 你好
             * userHeadPortrait : /static/ys_image/d6c26a83-91ae-42c8-ba9a-b4c94de964f7.jpg
             */

            private String belongCompany;
            private int id;
            private String msgRegId;
            private String name;
            private String phone;
            private String realName;
            private String signName;
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

            public String getUserHeadPortrait() {
                return userHeadPortrait;
            }

            public void setUserHeadPortrait(String userHeadPortrait) {
                this.userHeadPortrait = userHeadPortrait;
            }

            @Override
            public String toString() {
                return "StartBean{" +
                        "belongCompany='" + belongCompany + '\'' +
                        ", id=" + id +
                        ", msgRegId='" + msgRegId + '\'' +
                        ", name='" + name + '\'' +
                        ", phone='" + phone + '\'' +
                        ", realName='" + realName + '\'' +
                        ", signName='" + signName + '\'' +
                        ", userHeadPortrait='" + userHeadPortrait + '\'' +
                        '}';
            }
        }

        public static class UserBean implements Serializable {
            /**
             * belongCompany :
             * id : 31
             * msgRegId : 13065ffa4e32b2e11e1
             * name : wiki
             * phone : 18672342353
             * realName :
             * signName :
             * userHeadPortrait : /static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png
             */

            private String belongCompany;
            private int id;
            private String msgRegId;
            private String name;
            private String phone;
            private String realName;
            private String signName;
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

            public String getUserHeadPortrait() {
                return userHeadPortrait;
            }

            public void setUserHeadPortrait(String userHeadPortrait) {
                this.userHeadPortrait = userHeadPortrait;
            }

            @Override
            public String toString() {
                return "UserBean{" +
                        "belongCompany='" + belongCompany + '\'' +
                        ", id=" + id +
                        ", msgRegId='" + msgRegId + '\'' +
                        ", name='" + name + '\'' +
                        ", phone='" + phone + '\'' +
                        ", realName='" + realName + '\'' +
                        ", signName='" + signName + '\'' +
                        ", userHeadPortrait='" + userHeadPortrait + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "RowsBean{" +
                    "createTime='" + createTime + '\'' +
                    ", id=" + id +
                    ", statu=" + statu +
                    ", picUrl='" + picUrl + '\'' +
                    ", start=" + start +
                    ", user=" + user +
                    ", giveUpCount=" + giveUpCount +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PicWallInfo{" +
                "errorMsg='" + errorMsg + '\'' +
                ", success=" + success +
                ", total=" + total +
                ", rows=" + rows +
                '}';
    }
}
