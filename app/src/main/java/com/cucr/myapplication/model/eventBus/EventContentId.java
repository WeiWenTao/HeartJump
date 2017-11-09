package com.cucr.myapplication.model.eventBus;

/**
 * Created by cucr on 2017/11/8.
 */

public class EventContentId {
    private int contentId;

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public EventContentId(int contentId) {

        this.contentId = contentId;
    }
}
