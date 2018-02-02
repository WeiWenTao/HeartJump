package com.cucr.myapplication.bean.login;

/**
 * Created by cucr on 2018/2/2.
 */

public class ThirdLoadInfo {

    /**
     * msg :
     * obj : {"sign":"e470cfaf-c401-4687-afcf-25667379df83","loginStatu":"4","phone":"13554389941","userHeadPortrait":"http://tva4.sinaimg.cn/crop.0.0.664.664.50/006ozhvrjw8f1ayu1g7ihj30ig0igmy1.jpg","name":"低调的小明君","userId":28,"roleId":4}
     * success : true
     */

    private String msg;
    private String obj;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
