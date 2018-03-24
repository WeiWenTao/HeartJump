package com.cucr.myapplication.bean.app;

import java.util.List;

/**
 * Created by cucr on 2018/3/24.
 */

public class TokenMsg {

    /**
     * errorMsg : 签名错误
     * rows : []
     * success : false
     * total : 0
     */

    private String errorMsg;
    private boolean success;
    private int total;
    private List<?> rows;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
