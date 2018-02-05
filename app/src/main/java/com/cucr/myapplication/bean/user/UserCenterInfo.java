package com.cucr.myapplication.bean.user;

/**
 * Created by cucr on 2017/11/29.
 */

public class UserCenterInfo {

    /**
     * msg :
     * obj : {"yhtx":"/static/yuanshi_image/19e7a523-9d3c-4c45-afc2-4dbd34bb4abc.png","qm":"","dtsl":17,"sex":1,"gzmxsl":4,"xbsl":983123,"userPicCover":null,"isgz":"false","realName":"12","yhnc":"我在这里啊","fssl":0,"gzsl":4}
     * success : true
     */

    private String msg;
    private ObjBean obj;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class ObjBean {
        /**
         * yhtx : /static/yuanshi_image/19e7a523-9d3c-4c45-afc2-4dbd34bb4abc.png
         * qm :
         * dtsl : 17
         * sex : 1
         * gzmxsl : 4
         * xbsl : 983123
         * userPicCover : null
         * isgz : false
         * realName : 12
         * yhnc : 我在这里啊
         * fssl : 0
         * gzsl : 4
         */

        private String yhtx;
        private String qm;
        private int dtsl;
        private int sex;
        private int gzmxsl;
        private int xbsl;
        private String userPicCover;
        private int isgz;
        private String realName;
        private String yhnc;
        private int fssl;
        private int gzsl;

        public String getYhtx() {
            return yhtx;
        }

        public void setYhtx(String yhtx) {
            this.yhtx = yhtx;
        }

        public String getQm() {
            return qm;
        }

        public void setQm(String qm) {
            this.qm = qm;
        }

        public int getDtsl() {
            return dtsl;
        }

        public void setDtsl(int dtsl) {
            this.dtsl = dtsl;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getGzmxsl() {
            return gzmxsl;
        }

        public void setGzmxsl(int gzmxsl) {
            this.gzmxsl = gzmxsl;
        }

        public int getXbsl() {
            return xbsl;
        }

        public void setXbsl(int xbsl) {
            this.xbsl = xbsl;
        }

        public String getUserPicCover() {
            return userPicCover;
        }

        public void setUserPicCover(String userPicCover) {
            this.userPicCover = userPicCover;
        }

        public int getIsgz() {
            return isgz;
        }

        public void setIsgz(int isgz) {
            this.isgz = isgz;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getYhnc() {
            return yhnc;
        }

        public void setYhnc(String yhnc) {
            this.yhnc = yhnc;
        }

        public int getFssl() {
            return fssl;
        }

        public void setFssl(int fssl) {
            this.fssl = fssl;
        }

        public int getGzsl() {
            return gzsl;
        }

        public void setGzsl(int gzsl) {
            this.gzsl = gzsl;
        }
    }
}
