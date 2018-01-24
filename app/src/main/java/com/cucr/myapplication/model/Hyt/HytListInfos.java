package com.cucr.myapplication.model.Hyt;

import java.util.List;

/**
 * Created by cucrx on 2018/1/19.
 */

public class HytListInfos {

    /**
     * errorMsg :
     * rows : [{"city":"武汉","createTime":"2018-01-16 17:59:28","createUser":{"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"wiki","phone":"18672342353","realName":"","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"},"email":"476395715@qq.com","id":2,"idCard":"1234567891011","idCardPic1":"","idCardPic2":"","name":"测试后援团","picUrl":"","realUserName":"殷文其","startUser":{"belongCompany":"","id":33,"msgRegId":"","name":"鹿晗","phone":"13794622654","realName":"鹿晗","signName":"","startCost":300,"userHeadPortrait":"/static/ys_image/23fea8d4-1a25-427e-843b-bd159721af07.jpg"},"userContact":"13794613002"}]
     * success : true
     * total : 1
     */

    private String errorMsg;
    private boolean success;
    private int total;
    private List<RowsBean> rows;

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

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * city : 武汉
         * createTime : 2018-01-16 17:59:28
         * createUser : {"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"wiki","phone":"18672342353","realName":"","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"}
         * email : 476395715@qq.com
         * id : 2
         * idCard : 1234567891011
         * idCardPic1 :
         * idCardPic2 :
         * name : 测试后援团
         * picUrl :
         * realUserName : 殷文其
         * startUser : {"belongCompany":"","id":33,"msgRegId":"","name":"鹿晗","phone":"13794622654","realName":"鹿晗","signName":"","startCost":300,"userHeadPortrait":"/static/ys_image/23fea8d4-1a25-427e-843b-bd159721af07.jpg"}
         * userContact : 13794613002
         */

        private String city;
        private String createTime;
        private CreateUserBean createUser;
        private String email;
        private int id;
        private String idCard;
        private String idCardPic1;
        private String idCardPic2;
        private String name;
        private String picUrl;
        private String realUserName;
        private StartUserBean startUser;
        private String userContact;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public CreateUserBean getCreateUser() {
            return createUser;
        }

        public void setCreateUser(CreateUserBean createUser) {
            this.createUser = createUser;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getIdCardPic1() {
            return idCardPic1;
        }

        public void setIdCardPic1(String idCardPic1) {
            this.idCardPic1 = idCardPic1;
        }

        public String getIdCardPic2() {
            return idCardPic2;
        }

        public void setIdCardPic2(String idCardPic2) {
            this.idCardPic2 = idCardPic2;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getRealUserName() {
            return realUserName;
        }

        public void setRealUserName(String realUserName) {
            this.realUserName = realUserName;
        }

        public StartUserBean getStartUser() {
            return startUser;
        }

        public void setStartUser(StartUserBean startUser) {
            this.startUser = startUser;
        }

        public String getUserContact() {
            return userContact;
        }

        public void setUserContact(String userContact) {
            this.userContact = userContact;
        }

        public static class CreateUserBean {
            /**
             * belongCompany :
             * id : 31
             * msgRegId : 190e35f7e079f5a7e78
             * name : wiki
             * phone : 18672342353
             * realName :
             * signName :
             * startCost : null
             * userHeadPortrait : /static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png
             */

            private String belongCompany;
            private int id;
            private String msgRegId;
            private String name;
            private String phone;
            private String realName;
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

        public static class StartUserBean {
            /**
             * belongCompany :
             * id : 33
             * msgRegId :
             * name : 鹿晗
             * phone : 13794622654
             * realName : 鹿晗
             * signName :
             * startCost : 300
             * userHeadPortrait : /static/ys_image/23fea8d4-1a25-427e-843b-bd159721af07.jpg
             */

            private String belongCompany;
            private int id;
            private String msgRegId;
            private String name;
            private String phone;
            private String realName;
            private String signName;
            private int startCost;
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

            public String getSignName() {
                return signName;
            }

            public void setSignName(String signName) {
                this.signName = signName;
            }

            public int getStartCost() {
                return startCost;
            }

            public void setStartCost(int startCost) {
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
}
