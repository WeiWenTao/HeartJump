package com.cucr.myapplication.bean.eventBus;

/**
 * Created by cucr on 2018/2/11.
 * 999 minefragment 关注和粉丝取消关注事件
 * 998 发布或删除了活动 刷新
 */

public class CommentEvent {
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public CommentEvent(int flag) {
        this.flag = flag;
    }
}
