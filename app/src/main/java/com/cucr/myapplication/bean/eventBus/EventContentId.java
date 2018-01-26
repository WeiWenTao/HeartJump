package com.cucr.myapplication.bean.eventBus;

/**
 * Created by cucr on 2017/11/8.
 */

public class EventContentId {
    private int contentId;
    private int position;

    public int getContentId() {
        return contentId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public EventContentId(int contentId,int position) {
        this.contentId = contentId;
        this.position = position;
    }
}
