package com.cucr.myapplication.bean.app;

/**
 * Created by cucr on 2017/12/22.
 */

public class AppInfo {


    /**
     * actionCode : azUp
     * extra1 : 1       版本号
     * extra2 : 2
     * extra3 : 3
     * groupFild : 1
     * id : 41
     * keyFild : 1.0.1
     * remark : 1.一一一\ \n2.二二二3.三三三
     * sort : 2
     * valueFild : http://192.168.1.147:8080/download/xingtiao.apk
     */

    private String actionCode;
    private int extra1;
    private String extra2;
    private String extra3;
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

    public int getExtra1() {
        return extra1;
    }

    public void setExtra1(int extra1) {
        this.extra1 = extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public void setExtra2(String extra2) {
        this.extra2 = extra2;
    }

    public String getExtra3() {
        return extra3;
    }

    public void setExtra3(String extra3) {
        this.extra3 = extra3;
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
