package com.cucr.myapplication.alipay;

/**
 * Created by cucr on 2017/10/16.
 */

public class PayResultInfo {


    /**
     * alipay_trade_app_pay_response : {"code":"10000","msg":"Success","app_id":"2017101009230891","auth_app_id":"2017101009230891","charset":"utf-8","timestamp":"2017-10-16 19:26:34","total_amount":"0.01","trade_no":"2017101621001004540250124310","seller_id":"2088621866106080","out_trade_no":"1016192626165561"}
     * sign : H/ln/sCv1DC3NWzZQyxhI+4lRIihQuhkHmsYneXu/CYz0xqr1TzXcawuEYR+bfYvafiVPsL4FCWCHdgbh2hOADftjseZ+0ddfAPUW9WLFN0M+rb2d7syzcAAFRXDp53Bt6F+lFeGUOMO3az3HesOlTY68jcgTxkOiZbhnZ4qswY5kuPA0LEl6fQCpJJ9tAzKptekS2i1ZO0xsmhv+arD1c/TWQ/tvk0hASDIBlpS5MlTMJm5R2g0qGIahEZDqHYc0O15LsImzCYAftVYi30nexVurxaPh4xHCMaTmYnOt7AnOVFHeO+AVDFNT+BFXEEE4ngGXpWmfcy9OUjteX+Naw==
     * sign_type : RSA2
     */

    private AlipayTradeAppPayResponseBean alipay_trade_app_pay_response;
    private String sign;
    private String sign_type;

    public AlipayTradeAppPayResponseBean getAlipay_trade_app_pay_response() {
        return alipay_trade_app_pay_response;
    }

    public void setAlipay_trade_app_pay_response(AlipayTradeAppPayResponseBean alipay_trade_app_pay_response) {
        this.alipay_trade_app_pay_response = alipay_trade_app_pay_response;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public static class AlipayTradeAppPayResponseBean {
        /**
         * code : 10000
         * msg : Success
         * app_id : 2017101009230891
         * auth_app_id : 2017101009230891
         * charset : utf-8
         * timestamp : 2017-10-16 19:26:34
         * total_amount : 0.01
         * trade_no : 2017101621001004540250124310
         * seller_id : 2088621866106080
         * out_trade_no : 1016192626165561
         */

        private String code;
        private String msg;
        private String app_id;
        private String auth_app_id;
        private String charset;
        private String timestamp;
        private String total_amount;
        private String trade_no;
        private String seller_id;
        private String out_trade_no;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getAuth_app_id() {
            return auth_app_id;
        }

        public void setAuth_app_id(String auth_app_id) {
            this.auth_app_id = auth_app_id;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }
    }
}
