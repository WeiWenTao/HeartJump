package com.cucr.myapplication.bean.login;

/**
 * Created by 911 on 2017/8/14.
 */

public class LoadUserInfo {

    /**
     * msg : str
     * success : true
     */

    private String msg;
    private boolean success;
    private Object object;

    @Override
    public String toString() {
        return "LoadUserInfo{" +
                "msg='" + msg + '\'' +
                ", success=" + success +
                ", mObject=" + object +
                '}';
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

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
}
