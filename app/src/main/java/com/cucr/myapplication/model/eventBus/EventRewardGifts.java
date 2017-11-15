package com.cucr.myapplication.model.eventBus;

/**
 * Created by cucr on 2017/11/15.
 */

public class EventRewardGifts {
    private String giftPic;
    private int giftId;

    public String getGiftPic() {
        return giftPic;
    }

    public void setGiftPic(String giftPic) {
        this.giftPic = giftPic;
    }

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

    public EventRewardGifts(String giftPic, int giftId) {
        this.giftPic = giftPic;
        this.giftId = giftId;
    }

    @Override
    public String toString() {
        return "EventRewardGifts{" +
                "giftPic='" + giftPic + '\'' +
                ", giftId=" + giftId +
                '}';
    }
}
