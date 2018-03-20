package com.cucr.myapplication.bean.EditPersonalInfo;

/**
 * Created by cucr on 2018/3/2.
 */

public class IMHytInfo {

    /**
     * msg :
     * obj : [{"picUrl":"http://192.168.1.147:8080/static/yuanshi_image/a68007ea-b236-4dbc-8af6-c7280ea11b82.png","name":"后援团"}]
     * success : true
     */

    private String msg;
    private boolean success;
    private ObjBean obj;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * picUrl : http://192.168.1.147:8080/static/yuanshi_image/a68007ea-b236-4dbc-8af6-c7280ea11b82.png
         * name : 后援团
         */

        private String picUrl;
        private String name;
        private int hytId;

        public int getHytId() {
            return hytId;
        }

        public void setHytId(int hytId) {
            this.hytId = hytId;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
