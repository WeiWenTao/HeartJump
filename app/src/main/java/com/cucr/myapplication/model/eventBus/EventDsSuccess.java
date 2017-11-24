package com.cucr.myapplication.model.eventBus;

/**
 * Created by cucr on 2017/11/8.
 */

public class EventDsSuccess {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public EventDsSuccess(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "EventDsSuccess{" +
                "position=" + position +
                '}';
    }
}
