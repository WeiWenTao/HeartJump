package com.cucr.myapplication.model.eventBus;

/**
 * Created by cucr on 2017/11/27.
 * 请求完成
 */

public class EventRequestFinish {
    private int which;

    public EventRequestFinish(int which) {
        this.which = which;
    }

    public int getWhich() {
        return which;
    }

    public void setWhich(int which) {
        this.which = which;
    }

    private String what;

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    //用来区分是哪个接口请求
    public EventRequestFinish(String what) {
        this.what = what;
    }

    public EventRequestFinish() {
    }

}
