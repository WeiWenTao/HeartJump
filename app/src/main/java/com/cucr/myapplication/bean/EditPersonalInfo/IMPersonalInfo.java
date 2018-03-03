package com.cucr.myapplication.bean.EditPersonalInfo;

/**
 * Created by cucr on 2018/3/2.
 */

public class IMPersonalInfo {

    /**
     * msg :
     * obj : {"belongCompany":"","id":54,"msgRegId":"123456","name":"笨笨的考拉","phone":"15527609321","realName":"","ryimToken":"E/EIkOdAE+9MOxeCAFa34Ah6+9YvyxcsZKTG2qJ3/++m3mKnutcJQ02qWwaqXst9LDMCyZRf/7OScDSSBT8AGA==","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/43dd8d15-bca5-4582-839f-35fec9b16d65.png"}
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
         * belongCompany :
         * id : 54
         * msgRegId : 123456
         * name : 笨笨的考拉
         * phone : 15527609321
         * realName :
         * ryimToken : E/EIkOdAE+9MOxeCAFa34Ah6+9YvyxcsZKTG2qJ3/++m3mKnutcJQ02qWwaqXst9LDMCyZRf/7OScDSSBT8AGA==
         * signName :
         * startCost : null
         * userHeadPortrait : /static/yuanshi_image/43dd8d15-bca5-4582-839f-35fec9b16d65.png
         */

        private String belongCompany;
        private int id;
        private String msgRegId;
        private String name;
        private String phone;
        private String realName;
        private String ryimToken;
        private String signName;
        private Object startCost;
        private String userHeadPortrait;

        public String getBelongCompany() {
            return belongCompany;
        }

        public void setBelongCompany(String belongCompany) {
            this.belongCompany = belongCompany;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMsgRegId() {
            return msgRegId;
        }

        public void setMsgRegId(String msgRegId) {
            this.msgRegId = msgRegId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getRyimToken() {
            return ryimToken;
        }

        public void setRyimToken(String ryimToken) {
            this.ryimToken = ryimToken;
        }

        public String getSignName() {
            return signName;
        }

        public void setSignName(String signName) {
            this.signName = signName;
        }

        public Object getStartCost() {
            return startCost;
        }

        public void setStartCost(Object startCost) {
            this.startCost = startCost;
        }

        public String getUserHeadPortrait() {
            return userHeadPortrait;
        }

        public void setUserHeadPortrait(String userHeadPortrait) {
            this.userHeadPortrait = userHeadPortrait;
        }
    }
}
