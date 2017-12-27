package com.cucr.myapplication.model;

/**
 * Created by cucr on 2017/12/22.
 */

public class AppInfo {

    /**
     * actionCode : azUp
     * groupFild : 0
     * id : 21
     * keyFild : 1.0
     * remark : app更新 键为最新版本号 值为下载地址 分组为是否强制更新 排序老版本数字小
     * sort : 0
     * valueFild : 下载地址
     */

    private String actionCode;
    private int groupFild;
    private int id;
    private String keyFild;
    private String remark;
    private int sort;
    private String valueFild;

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public int getGroupFild() {
        return groupFild;
    }

    public void setGroupFild(int groupFild) {
        this.groupFild = groupFild;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyFild() {
        return keyFild;
    }

    public void setKeyFild(String keyFild) {
        this.keyFild = keyFild;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getValueFild() {
        return valueFild;
    }

    public void setValueFild(String valueFild) {
        this.valueFild = valueFild;
    }
}
