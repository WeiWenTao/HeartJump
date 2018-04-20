package com.cucr.myapplication.bean.Home;

/**
 * Created by cucr on 2018/4/20.
 */

public class HomeNewsCatgory {

    private String catgory;
    private int newsType;

    public HomeNewsCatgory(String catgory, int newsType) {
        this.catgory = catgory;
        this.newsType = newsType;
    }

    public String getCatgory() {
        return catgory;
    }

    public void setCatgory(String catgory) {
        this.catgory = catgory;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

}
