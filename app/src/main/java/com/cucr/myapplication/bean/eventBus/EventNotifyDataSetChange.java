package com.cucr.myapplication.bean.eventBus;

/**
 * Created by cucr on 2017/12/8.
 */

public class EventNotifyDataSetChange {

    private int which;

    public int getWhich() {
        return which;
    }

    public void setWhich(int which) {
        this.which = which;
    }

    public EventNotifyDataSetChange(int which) {
        this.which = which;
    }

}
