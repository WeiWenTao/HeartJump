package com.cucr.myapplication.model.starJourney;

import java.util.List;

/**
 * Created by cucr on 2017/9/18.
 */

public class StarScheduleLIst {


    /**
     * msg :
     * obj : ["2018-01","2017-09"]
     * success : true
     */

    private String msg;
    private boolean success;
    private List<String> obj;

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

    public List<String> getObj() {
        return obj;
    }

    public void setObj(List<String> obj) {
        this.obj = obj;
    }
}
