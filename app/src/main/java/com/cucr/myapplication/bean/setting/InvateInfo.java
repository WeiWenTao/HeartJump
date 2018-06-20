package com.cucr.myapplication.bean.setting;

/**
 * Created by cucr on 2018/6/15.
 */

public class InvateInfo {

    /**
     * msg :
     * obj : {"id":4502,"userHeadPortrait":"http://webdata.cucr.cn/efb05b83b73f230a34a35f280dd18013.jpg","userPicCover":"http://webdata.cucr.cn/efb05b83b73f230a34a35f280dd18013.jpg","realName":"鹿晗","code":"LObmGI","shareCode":""}
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
         * id : 4502
         * userHeadPortrait : http://webdata.cucr.cn/efb05b83b73f230a34a35f280dd18013.jpg
         * userPicCover : http://webdata.cucr.cn/efb05b83b73f230a34a35f280dd18013.jpg
         * realName : 鹿晗
         * code : LObmGI
         * shareCode :
         */

        private int id;
        private String userHeadPortrait;
        private String userPicCover;
        private String realName;
        private String code;
        private String shareCode;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserHeadPortrait() {
            return userHeadPortrait;
        }

        public void setUserHeadPortrait(String userHeadPortrait) {
            this.userHeadPortrait = userHeadPortrait;
        }

        public String getUserPicCover() {
            return userPicCover;
        }

        public void setUserPicCover(String userPicCover) {
            this.userPicCover = userPicCover;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getShareCode() {
            return shareCode;
        }

        public void setShareCode(String shareCode) {
            this.shareCode = shareCode;
        }
    }
}
