package com.cucr.myapplication.bean.setting;

/**
 * Created by cucr on 2018/5/17.
 */

public class MineInfo {

    private int icon;
    private String catgory;
    private int flag;

    public MineInfo(int icon, String catgory, int flag) {
        this.icon = icon;
        this.catgory = catgory;
        this.flag = flag;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getCatgory() {
        return catgory;
    }

    public void setCatgory(String catgory) {
        this.catgory = catgory;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
