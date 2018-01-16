package com.cucr.myapplication.model.fuli;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cucr on 2017/12/7.
 * 企业活动和预约我的-我的预约共用一个Bean
 */

public class QiYeHuoDongInfo implements Serializable {

    /**
     * errorMsg :
     * rows : [{"activeAdress":"点军区82号","activeEndTime":"2017-12-31 00:00:00","activeInfo":"不知道为啥就想办场活动","activeName":"心跳互娱答谢会，就是让你们感受到无上的光荣 的的传统","activePlace":"湖北 宜昌 西陵区","activeScene":0,"activeStartTime":"2017-12-31 00:00:00","appStartUser":{"belongCompany":"","id":29,"msgRegId":"123456","name":"笨笨的考拉","phone":"15527609321","realName":"迪丽热巴","signName":"你好","userHeadPortrait":"/static/yuanshi_image/c6fe2069-b05b-41fd-92d4-212568df749d.png"},"applyUser":{"belongCompany":"","id":29,"msgRegId":"123456","name":"笨笨的考拉","phone":"15527609321","realName":"迪丽热巴","signName":"你好","userHeadPortrait":"/static/yuanshi_image/c6fe2069-b05b-41fd-92d4-212568df749d.png"},"commentCount":1,"giveUpCount":1,"id":12,"isSignUp":1,"openys":0,"peopleCount":1,"picurl":"/static/yuanshi_image/cf83877b-7848-481d-9c40-3fbdc61e535e.png","result":1,"type":1,"upTime":"2017-12-05 15:26:11","ys":333},{"activeAdress":"大学路8号三峡大学图书馆","activeEndTime":"2017-12-05 15:26:11","activeInfo":"心跳互动娱乐发布会，需要进行发布会展示，希望大家能够一起参加吧","activeName":"心跳互娱网络科技有限公司答谢会","activePlace":"湖北 宜昌 西陵区","activeScene":1,"activeStartTime":"2017-12-20 14:53:00","appStartUser":{"belongCompany":"","id":29,"msgRegId":"123456","name":"笨笨的考拉","phone":"15527609321","realName":"迪丽热巴","signName":"你好","userHeadPortrait":"/static/yuanshi_image/c6fe2069-b05b-41fd-92d4-212568df749d.png"},"applyUser":{"belongCompany":"","id":29,"msgRegId":"123456","name":"笨笨的考拉","phone":"15527609321","realName":"迪丽热巴","signName":"你好","userHeadPortrait":"/static/yuanshi_image/c6fe2069-b05b-41fd-92d4-212568df749d.png"},"commentCount":1,"giveUpCount":1,"id":11,"isSignUp":1,"openys":0,"peopleCount":1,"picurl":"/static/yuanshi_image/9798a787-eb9e-4354-9d64-8be8dc0f3da6.png","result":1,"type":1,"upTime":"2017-12-05 15:04:33","ys":100},{"activeAdress":"书城路52号洪山创意中心三楼","activeEndTime":"2017-12-05 15:26:11","activeInfo":"为1感谢各大投资人对公司的大力 ，特地在年会举行之际，邀请各位同仁来参加。也希望各位粉丝进行深入交流","activeName":"创联凯尔答谢会","activePlace":"湖北 武汉 洪山区","activeScene":1,"activeStartTime":"2017-12-20 14:25:00","appStartUser":{"belongCompany":"","id":29,"msgRegId":"123456","name":"笨笨的考拉","phone":"15527609321","realName":"迪丽热巴","signName":"你好","userHeadPortrait":"/static/yuanshi_image/c6fe2069-b05b-41fd-92d4-212568df749d.png"},"applyUser":{"belongCompany":"","id":29,"msgRegId":"123456","name":"笨笨的考拉","phone":"15527609321","realName":"迪丽热巴","signName":"你好","userHeadPortrait":"/static/yuanshi_image/c6fe2069-b05b-41fd-92d4-212568df749d.png"},"commentCount":1,"giveUpCount":1,"id":10,"isSignUp":1,"openys":1,"peopleCount":1,"picurl":"/static/yuanshi_image/0515903a-4a3d-4092-8fd7-cd9840a174c8.png","result":1,"type":1,"upTime":"2017-12-05 14:28:54","ys":56}]
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

    public static class RowsBean implements Serializable {
        /**
         * activeAdress : 点军区82号
         * activeEndTime : 2017-12-31 00:00:00
         * activeInfo : 不知道为啥就想办场活动
         * activeName : 心跳互娱答谢会，就是让你们感受到无上的光荣 的的传统
         * activePlace : 湖北 宜昌 西陵区
         * activeScene : 0
         * activeStartTime : 2017-12-31 00:00:00
         * appStartUser : {"belongCompany":"","id":29,"msgRegId":"123456","name":"笨笨的考拉","phone":"15527609321","realName":"迪丽热巴","signName":"你好","userHeadPortrait":"/static/yuanshi_image/c6fe2069-b05b-41fd-92d4-212568df749d.png"}
         * applyUser : {"belongCompany":"","id":29,"msgRegId":"123456","name":"笨笨的考拉","phone":"15527609321","realName":"迪丽热巴","signName":"你好","userHeadPortrait":"/static/yuanshi_image/c6fe2069-b05b-41fd-92d4-212568df749d.png"}
         * commentCount : 1
         * giveUpCount : 1
         * id : 12
         * isSignUp : 1
         * openys : 0
         * peopleCount : 1
         * picurl : /static/yuanshi_image/cf83877b-7848-481d-9c40-3fbdc61e535e.png
         * result : 1
         * type : 1
         * upTime : 2017-12-05 15:26:11
         * ys : 333
         */

        private String activeAdress;
        private String activeEndTime;
        private String activeInfo;
        private String activeName;
        private String activePlace;
        private int activeScene;
        private String activeStartTime;
        private AppStartUserBean appStartUser;
        private ApplyUserBean applyUser;
        private int commentCount;
        private int giveUpCount;
        private int id;
        private int isSignUp;
        private int openys;
        private int peopleCount;
        private String picurl;
        private int result;
        private int type;
        private String upTime;
        private int ys;
        private int startCost;

        public int getStartCost() {
            return startCost;
        }

        public void setStartCost(int startCost) {
            this.startCost = startCost;
        }

        public String getActiveAdress() {
            return activeAdress;
        }

        public void setActiveAdress(String activeAdress) {
            this.activeAdress = activeAdress;
        }

        public String getActiveEndTime() {
            return activeEndTime;
        }

        public void setActiveEndTime(String activeEndTime) {
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

        public int getActiveScene() {
            return activeScene;
        }

        public void setActiveScene(int activeScene) {
            this.activeScene = activeScene;
        }

        public String getActiveStartTime() {
            return activeStartTime;
        }

        public void setActiveStartTime(String activeStartTime) {
            this.activeStartTime = activeStartTime;
        }

        public AppStartUserBean getAppStartUser() {
            return appStartUser;
        }

        public void setAppStartUser(AppStartUserBean appStartUser) {
            this.appStartUser = appStartUser;
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

        public int getPeopleCount() {
            return peopleCount;
        }

        public void setPeopleCount(int peopleCount) {
            this.peopleCount = peopleCount;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
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

        public static class AppStartUserBean implements Serializable {
            /**
             * belongCompany :
             * id : 29
             * msgRegId : 123456
             * name : 笨笨的考拉
             * phone : 15527609321
             * realName : 迪丽热巴
             * signName : 你好
             * userHeadPortrait : /static/yuanshi_image/c6fe2069-b05b-41fd-92d4-212568df749d.png
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
        }

        public static class ApplyUserBean implements Serializable {
            /**
             * belongCompany :
             * id : 29
             * msgRegId : 123456
             * name : 笨笨的考拉
             * phone : 15527609321
             * realName : 迪丽热巴
             * signName : 你好
             * userHeadPortrait : /static/yuanshi_image/c6fe2069-b05b-41fd-92d4-212568df749d.png
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
        }
    }
}
