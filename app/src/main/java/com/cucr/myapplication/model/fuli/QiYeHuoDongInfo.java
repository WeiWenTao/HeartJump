package com.cucr.myapplication.model.fuli;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cucr on 2017/12/7.
 */

public class QiYeHuoDongInfo implements Serializable{

    /**
     * errorMsg :
     * rows : [{"activeAdress":"Asdasdasdasd","activeEndTime":null,"activeInfo":"","activeName":"Sadasdasd","activePlace":"北京 通州 ","activeScene":null,"activeStartTime":"2017-11-04 09:26:00","appStartId":null,"appStartName":"","applyUser":{"belongCompany":"","id":29,"msgRegId":"123456","name":"迪丽热巴","realName":"迪丽热巴","signName":"你好","userHeadPortrait":"/static/ys_image/d6c26a83-91ae-42c8-ba9a-b4c94de964f7.jpg"},"commentCount":0,"giveUpCount":0,"id":5,"isSignUp":0,"openys":0,"peopleCount":null,"picurl":"","result":null,"type":1,"upTime":"2017-11-03 09:26:56","ys":56},{"activeAdress":"Asdasdasdasd","activeEndTime":null,"activeInfo":"","activeName":"Sadasdasd","activePlace":"北京 通州 ","activeScene":null,"activeStartTime":"2017-11-04 09:26:00","appStartId":null,"appStartName":"","applyUser":{"belongCompany":"","id":29,"msgRegId":"123456","name":"迪丽热巴","realName":"迪丽热巴","signName":"你好","userHeadPortrait":"/static/ys_image/d6c26a83-91ae-42c8-ba9a-b4c94de964f7.jpg"},"commentCount":0,"giveUpCount":0,"id":6,"isSignUp":0,"openys":0,"peopleCount":null,"picurl":"","result":null,"type":1,"upTime":"2017-11-03 09:26:59","ys":56}]
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

    public static class RowsBean implements Serializable{
        /**
         * activeAdress : Asdasdasdasd
         * activeEndTime : null
         * activeInfo :
         * activeName : Sadasdasd
         * activePlace : 北京 通州
         * activeScene : null
         * activeStartTime : 2017-11-04 09:26:00
         * appStartId : null
         * appStartName :
         * applyUser : {"belongCompany":"","id":29,"msgRegId":"123456","name":"迪丽热巴","realName":"迪丽热巴","signName":"你好","userHeadPortrait":"/static/ys_image/d6c26a83-91ae-42c8-ba9a-b4c94de964f7.jpg"}
         * commentCount : 0
         * giveUpCount : 0
         * id : 5
         * isSignUp : 0
         * openys : 0
         * peopleCount : null
         * picurl :
         * result : null
         * type : 1
         * upTime : 2017-11-03 09:26:56
         * ys : 56
         */

        private String activeAdress;
        private Object activeEndTime;
        private String activeInfo;
        private String activeName;
        private String activePlace;
        private Object activeScene;
        private String activeStartTime;
        private Object appStartId;
        private String appStartName;
        private ApplyUserBean applyUser;
        private int commentCount;
        private int giveUpCount;
        private int id;
        private int isSignUp;
        private int openys;
        private Integer peopleCount;
        private String picurl;
        private Object result;
        private int type;
        private String upTime;
        private int ys;

        public String getActiveAdress() {
            return activeAdress;
        }

        public void setActiveAdress(String activeAdress) {
            this.activeAdress = activeAdress;
        }

        public Object getActiveEndTime() {
            return activeEndTime;
        }

        public void setActiveEndTime(Object activeEndTime) {
            this.activeEndTime = activeEndTime;
        }

        public String getActiveInfo() {
            return activeInfo;
        }

        public void setActiveInfo(String activeInfo) {
            this.activeInfo = activeInfo;
        }

        public String getActiveName() {
            return activeName;
        }

        public void setActiveName(String activeName) {
            this.activeName = activeName;
        }

        public String getActivePlace() {
            return activePlace;
        }

        public void setActivePlace(String activePlace) {
            this.activePlace = activePlace;
        }

        public Object getActiveScene() {
            return activeScene;
        }

        public void setActiveScene(Object activeScene) {
            this.activeScene = activeScene;
        }

        public String getActiveStartTime() {
            return activeStartTime;
        }

        public void setActiveStartTime(String activeStartTime) {
            this.activeStartTime = activeStartTime;
        }

        public Object getAppStartId() {
            return appStartId;
        }

        public void setAppStartId(Object appStartId) {
            this.appStartId = appStartId;
        }

        public String getAppStartName() {
            return appStartName;
        }

        public void setAppStartName(String appStartName) {
            this.appStartName = appStartName;
        }

        public ApplyUserBean getApplyUser() {
            return applyUser;
        }

        public void setApplyUser(ApplyUserBean applyUser) {
            this.applyUser = applyUser;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getGiveUpCount() {
            return giveUpCount;
        }

        public void setGiveUpCount(int giveUpCount) {
            this.giveUpCount = giveUpCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsSignUp() {
            return isSignUp;
        }

        public void setIsSignUp(int isSignUp) {
            this.isSignUp = isSignUp;
        }

        public int getOpenys() {
            return openys;
        }

        public void setOpenys(int openys) {
            this.openys = openys;
        }

        public Integer getPeopleCount() {
            return peopleCount;
        }

        public void setPeopleCount(Integer peopleCount) {
            this.peopleCount = peopleCount;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUpTime() {
            return upTime;
        }

        public void setUpTime(String upTime) {
            this.upTime = upTime;
        }

        public int getYs() {
            return ys;
        }

        public void setYs(int ys) {
            this.ys = ys;
        }

        public static class ApplyUserBean implements Serializable {
            /**
             * belongCompany :
             * id : 29
             * msgRegId : 123456
             * name : 迪丽热巴
             * realName : 迪丽热巴
             * signName : 你好
             * userHeadPortrait : /static/ys_image/d6c26a83-91ae-42c8-ba9a-b4c94de964f7.jpg
             */

            private String belongCompany;
            private int id;
            private String msgRegId;
            private String name;
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
                return "ApplyUserBean{" +
                        "belongCompany='" + belongCompany + '\'' +
                        ", id=" + id +
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
                    "activeAdress='" + activeAdress + '\'' +
                    ", activeEndTime=" + activeEndTime +
                    ", activeInfo='" + activeInfo + '\'' +
                    ", activeName='" + activeName + '\'' +
                    ", activePlace='" + activePlace + '\'' +
                    ", activeScene=" + activeScene +
                    ", activeStartTime='" + activeStartTime + '\'' +
                    ", appStartId=" + appStartId +
                    ", appStartName='" + appStartName + '\'' +
                    ", applyUser=" + applyUser +
                    ", commentCount=" + commentCount +
                    ", giveUpCount=" + giveUpCount +
                    ", id=" + id +
                    ", isSignUp=" + isSignUp +
                    ", openys=" + openys +
                    ", peopleCount=" + peopleCount +
                    ", picurl='" + picurl + '\'' +
                    ", result=" + result +
                    ", type=" + type +
                    ", upTime='" + upTime + '\'' +
                    ", ys=" + ys +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "QiYeHuoDongInfo{" +
                "errorMsg='" + errorMsg + '\'' +
                ", success=" + success +
                ", total=" + total +
                ", rows=" + rows +
                '}';
    }
}
