package com.cucr.myapplication.alipay;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cucr on 2018/2/1.
 */

public class WxPayInfo {

    /**
     * msg :
     * obj : {"msg":{"appid":"wxbe72c16183cf70da","noncestr":"X0tOx1jwRj1825xo","package":"Sign=WXPay","partnerid":"1497290532","prepayid":"wx20180201161135cb34aa051a0298792656","sign":"1D2A137F062DC639F16CB41E186C0B14","timestamp":1517472699},"code":"200"}
     * success : true
     */

    private String msg;
    private ObjBean obj;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class ObjBean {
        /**
         * msg : {"appid":"wxbe72c16183cf70da","noncestr":"X0tOx1jwRj1825xo","package":"Sign=WXPay","partnerid":"1497290532","prepayid":"wx20180201161135cb34aa051a0298792656","sign":"1D2A137F062DC639F16CB41E186C0B14","timestamp":1517472699}
         * code : 200
         */

        private MsgBean msg;
        private String code;

        public MsgBean getMsg() {
            return msg;
        }

        public void setMsg(MsgBean msg) {
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public static class MsgBean {
            /**
             * appid : wxbe72c16183cf70da
             * noncestr : X0tOx1jwRj1825xo
             * package : Sign=WXPay
             * partnerid : 1497290532
             * prepayid : wx20180201161135cb34aa051a0298792656
             * sign : 1D2A137F062DC639F16CB41E186C0B14
             * timestamp : 1517472699
             */

            private String appid;
            private String noncestr;
            @SerializedName("package")
            private String packageX;
            private String partnerid;
            private String prepayid;
            private String sign;
            private int timestamp;
            private String orderNo;

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            @Override
            public String toString() {
                return "MsgBean{" +
                        "appid='" + appid + '\'' +
                        ", noncestr='" + noncestr + '\'' +
                        ", packageX='" + packageX + '\'' +
                        ", partnerid='" + partnerid + '\'' +
                        ", prepayid='" + prepayid + '\'' +
                        ", sign='" + sign + '\'' +
                        ", timestamp=" + timestamp +
                        ", orderNo='" + orderNo + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ObjBean{" +
                    "msg=" + msg +
                    ", code='" + code + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WxPayInfo{" +
                "msg='" + msg + '\'' +
                ", obj=" + obj +
                ", success=" + success +
                '}';
    }
}
