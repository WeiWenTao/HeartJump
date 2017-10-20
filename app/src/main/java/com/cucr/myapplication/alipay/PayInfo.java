package com.cucr.myapplication.alipay;

/**
 * Created by cucr on 2017/10/12.
 */

public class PayInfo {

    /**
     * msg : app_id=2017101009230891&biz_content=%7B%22body%22%3A%221%22%7D&charset=UTF-8&method=alipay.trade.app.pay¬ify_url=http%3A%2F%2F101.132.96.199%2Finterface%2Fpay%2FalipayNotify&sign_type=RSA×tamp=2017-10-12%2009%3A35%3A26&version=1.0&sign=R3Uiq0mEx1CiK9Cj9bghpu3eoF77KoOjIcJKvFS7VTJHp56r3MAcMpVK%2F5oLBuiAL3hoSQgbK66abCuGc%2F7hI7JPSRlKYLw8q6uX5Mn9JUygE6bZGafwzaluEkB8t5DfjQI5lQ%2B9NlTxwg64abEuZlDPfdGxk2SeiDYLSXE9UnRC2J8CsXYo3ikwoggCmtoQe2By7M1aFtTeGxmj5995aQTy13SzYlOR2EjlXdqWA3PnKDYSegiPozFqiyhB4sIlJVBpl8llQmONS%2BSuIkofthe1weTFsBP9Df5%2FAl96TrqtimPzDWT36MF6lUNeV6eooA96pb5bdnOjDf1oXjSZ3A%3D%3D
     * obj : null
     * success : true
     */

    private String msg;
    private Object obj;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
