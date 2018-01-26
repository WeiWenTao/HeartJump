package com.cucr.myapplication.bean.RZ;

/**
 * Created by cucr on 2017/9/12.
 */

public class RzResult {

    /**
     * msg :
     * obj : {"auditor":"","auditorTime":null,"belongCompany":"333","companyContact":"","companyName":"","contact":"222","createType":0,"id":34,"info":"","pic1":"/static/yuanshi_image/4e131348-53ed-4ccd-9145-7e1d7db4d645.jpg","pic2":"/static/yuanshi_image/b56f851a-3921-445b-9b2f-7c17abcbc68f.jpg","pic3":"","result":0,"startCost":444,"type":0,"upTime":"2017-09-13 10:57:41","userAccount":"13554389941","userId":14,"userName":"111"}
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
         * auditor :
         * auditorTime : null
         * belongCompany : 333
         * companyContact :
         * companyName :
         * contact : 222
         * createType : 0
         * id : 34
         * info :
         * pic1 : /static/yuanshi_image/4e131348-53ed-4ccd-9145-7e1d7db4d645.jpg
         * pic2 : /static/yuanshi_image/b56f851a-3921-445b-9b2f-7c17abcbc68f.jpg
         * pic3 :
         * result : 0
         * startCost : 444
         * type : 0
         * upTime : 2017-09-13 10:57:41
         * userAccount : 13554389941
         * userId : 14
         * userName : 111
         */

        private String auditor;
        private Object auditorTime;
        private String belongCompany;
        private String companyContact;
        private String companyName;
        private String contact;
        private int createType;
        private String position;
        private Integer id;
        private String info;
        private String pic1;
        private String pic2;
        private String pic3;
        private int result;
        private int startCost;
        private int type;
        private String upTime;
        private String userAccount;
        private int userId;
        private String userName;

        public String getAuditor() {
            return auditor;
        }

        public void setAuditor(String auditor) {
            this.auditor = auditor;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public Object getAuditorTime() {
            return auditorTime;
        }

        public void setAuditorTime(Object auditorTime) {
            this.auditorTime = auditorTime;
        }

        public String getBelongCompany() {
            return belongCompany;
        }

        public void setBelongCompany(String belongCompany) {
            this.belongCompany = belongCompany;
        }

        public String getCompanyContact() {
            return companyContact;
        }

        public void setCompanyContact(String companyContact) {
            this.companyContact = companyContact;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public int getCreateType() {
            return createType;
        }

        public void setCreateType(int createType) {
            this.createType = createType;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getPic1() {
            return pic1;
        }

        public void setPic1(String pic1) {
            this.pic1 = pic1;
        }

        public String getPic2() {
            return pic2;
        }

        public void setPic2(String pic2) {
            this.pic2 = pic2;
        }

        public String getPic3() {
            return pic3;
        }

        public void setPic3(String pic3) {
            this.pic3 = pic3;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public int getStartCost() {
            return startCost;
        }

        public void setStartCost(int startCost) {
            this.startCost = startCost;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUpTime() {
            return upTime;
        }

        public void setUpTime(String upTime) {
            this.upTime = upTime;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
