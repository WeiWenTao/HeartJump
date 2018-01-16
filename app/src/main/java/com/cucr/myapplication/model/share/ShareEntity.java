package com.cucr.myapplication.model.share;

/**
 * Created by cucrx on 2018/1/10.
 */

public class ShareEntity {
    private String title;
    private String describe;
    private String link;
    private String imgURL;

    public ShareEntity(String title, String describe, String link, String imgURL) {
        this.title = title;
        this.describe = describe;
        this.link = link;
        this.imgURL = imgURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
