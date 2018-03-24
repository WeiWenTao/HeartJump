package com.cucr.myapplication.bean.Hyt;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cucr on 2018/1/26.
 */

public class YyhdInfos implements Serializable {

    /**
     * errorMsg :
     * rows : [{"activeContent":"这是活动内容2","activeName":"测试活动2","activeType":2,"auditor":"超级管理员","auditorTime":"2018-01-18 15:47:31","commentCount":0,"createTime":"2018-01-18 14:59:14","createUser":{"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"wiki","phone":"18672342353","realName":"","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"},"distanceEnd":null,"doingStatu":1,"endTime":"2018-01-20 15:00:00","giveUpCount":0,"hytInfo":{"auditor":"超级管理员","auditorTime":"2018-01-17 16:02:55","city":"武汉","createTime":"2018-01-17 15:31:04","createUser":{"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"wiki","phone":"18672342353","realName":"","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"},"email":"476395715@qq.com","id":1,"idCard":"1234567891011","idCardPic1":"","idCardPic2":"","info":"通过","name":"测试后援团","picUrl":"","realUserName":"殷文其","startUser":{"belongCompany":"","id":33,"msgRegId":"","name":"鹿晗","phone":"13794622654","realName":"鹿晗","signName":"","startCost":300,"userHeadPortrait":"/static/ys_image/23fea8d4-1a25-427e-843b-bd159721af07.jpg"},"statu":2,"userContact":"13794613002"},"id":9,"info":"允许通过","isGiveUp":0,"picUrl":"","signUpAmount":0,"statu":2,"sysHytActiveBigpad":{"activeId":null,"bigpads":[{"id":2,"sysHytActiveBigpad":null,"sysHytActiveBigpadInfo":{"address":"测试地址1","id":2,"picUrl":"/static/yuanshi_image/2ec501ee-0e15-4453-93eb-59389110364d.jpg","price":"测试价格1","purpose":"测试用途1","spec":"测试规格1"}},{"id":1,"sysHytActiveBigpad":null,"sysHytActiveBigpadInfo":{"address":"雄楚大道虎泉街交汇处","id":1,"picUrl":"/static/yuanshi_image/1f995cdb-2106-4ee3-ba61-795b55bff20b.jpg","price":"1001/天","purpose":"生日应援/纪念日应援","spec":"18.4米X12.28米（260m2）"}}],"id":1,"yyje":2000,"yzsm":"用资申明"},"sysHytActiveOpenscreen":{"activeId":null,"amount":100,"id":6},"sysHytActiveZc":{"activeId":null,"amount":100,"city":"武汉","explains":"说明","id":1,"scale":"规模"}}]
     * success : true
     * total : 3
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

    public static class RowsBean implements Serializable {
        /**
         * activeContent : 这是活动内容2
         * activeName : 测试活动2
         * activeType : 2
         * auditor : 超级管理员
         * auditorTime : 2018-01-18 15:47:31
         * commentCount : 0
         * createTime : 2018-01-18 14:59:14
         * createUser : {"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"wiki","phone":"18672342353","realName":"","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"}
         * distanceEnd : null
         * doingStatu : 1
         * endTime : 2018-01-20 15:00:00
         * giveUpCount : 0
         * hytInfo : {"auditor":"超级管理员","auditorTime":"2018-01-17 16:02:55","city":"武汉","createTime":"2018-01-17 15:31:04","createUser":{"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"wiki","phone":"18672342353","realName":"","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"},"email":"476395715@qq.com","id":1,"idCard":"1234567891011","idCardPic1":"","idCardPic2":"","info":"通过","name":"测试后援团","picUrl":"","realUserName":"殷文其","startUser":{"belongCompany":"","id":33,"msgRegId":"","name":"鹿晗","phone":"13794622654","realName":"鹿晗","signName":"","startCost":300,"userHeadPortrait":"/static/ys_image/23fea8d4-1a25-427e-843b-bd159721af07.jpg"},"statu":2,"userContact":"13794613002"}
         * id : 9
         * info : 允许通过
         * isGiveUp : 0
         * picUrl :
         * signUpAmount : 0
         * statu : 2
         * sysHytActiveBigpad : {"activeId":null,"bigpads":[{"id":2,"sysHytActiveBigpad":null,"sysHytActiveBigpadInfo":{"address":"测试地址1","id":2,"picUrl":"/static/yuanshi_image/2ec501ee-0e15-4453-93eb-59389110364d.jpg","price":"测试价格1","purpose":"测试用途1","spec":"测试规格1"}},{"id":1,"sysHytActiveBigpad":null,"sysHytActiveBigpadInfo":{"address":"雄楚大道虎泉街交汇处","id":1,"picUrl":"/static/yuanshi_image/1f995cdb-2106-4ee3-ba61-795b55bff20b.jpg","price":"1001/天","purpose":"生日应援/纪念日应援","spec":"18.4米X12.28米（260m2）"}}],"id":1,"yyje":2000,"yzsm":"用资申明"}
         * sysHytActiveOpenscreen : {"activeId":null,"amount":100,"id":6}
         * sysHytActiveZc : {"activeId":null,"amount":100,"city":"武汉","explains":"说明","id":1,"scale":"规模"}
         */

        private String activeContent;
        private String activeName;
        private int activeType;
        private String auditor;
        private String auditorTime;
        private int commentCount;
        private String createTime;
        private CreateUserBean createUser;
        private Object distanceEnd;
        private int doingStatu;
        private String endTime;
        private int giveUpCount;
        private HytInfoBean hytInfo;
        private int id;
        private String info;
        private int isGiveUp;
        private String picUrl;
        private double signUpAmount;
        private int statu;
        private SysHytActiveBigpadBean sysHytActiveBigpad;
        private SysHytActiveOpenscreenBean sysHytActiveOpenscreen;
        private SysHytActiveZcBean sysHytActiveZc;

        public String getActiveContent() {
            return activeContent;
        }

        public void setActiveContent(String activeContent) {
            this.activeContent = activeContent;
        }

        public String getActiveName() {
            return activeName;
        }

        public void setActiveName(String activeName) {
            this.activeName = activeName;
        }

        public int getActiveType() {
            return activeType;
        }

        public void setActiveType(int activeType) {
            this.activeType = activeType;
        }

        public String getAuditor() {
            return auditor;
        }

        public void setAuditor(String auditor) {
            this.auditor = auditor;
        }

        public String getAuditorTime() {
            return auditorTime;
        }

        public void setAuditorTime(String auditorTime) {
            this.auditorTime = auditorTime;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
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

        public Object getDistanceEnd() {
            return distanceEnd;
        }

        public void setDistanceEnd(Object distanceEnd) {
            this.distanceEnd = distanceEnd;
        }

        public int getDoingStatu() {
            return doingStatu;
        }

        public void setDoingStatu(int doingStatu) {
            this.doingStatu = doingStatu;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getGiveUpCount() {
            return giveUpCount;
        }

        public void setGiveUpCount(int giveUpCount) {
            this.giveUpCount = giveUpCount;
        }

        public HytInfoBean getHytInfo() {
            return hytInfo;
        }

        public void setHytInfo(HytInfoBean hytInfo) {
            this.hytInfo = hytInfo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getIsGiveUp() {
            return isGiveUp;
        }

        public void setIsGiveUp(int isGiveUp) {
            this.isGiveUp = isGiveUp;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public double getSignUpAmount() {
            return signUpAmount;
        }

        public void setSignUpAmount(double signUpAmount) {
            this.signUpAmount = signUpAmount;
        }

        public int getStatu() {
            return statu;
        }

        public void setStatu(int statu) {
            this.statu = statu;
        }

        public SysHytActiveBigpadBean getSysHytActiveBigpad() {
            return sysHytActiveBigpad;
        }

        public void setSysHytActiveBigpad(SysHytActiveBigpadBean sysHytActiveBigpad) {
            this.sysHytActiveBigpad = sysHytActiveBigpad;
        }

        public SysHytActiveOpenscreenBean getSysHytActiveOpenscreen() {
            return sysHytActiveOpenscreen;
        }

        public void setSysHytActiveOpenscreen(SysHytActiveOpenscreenBean sysHytActiveOpenscreen) {
            this.sysHytActiveOpenscreen = sysHytActiveOpenscreen;
        }

        public SysHytActiveZcBean getSysHytActiveZc() {
            return sysHytActiveZc;
        }

        public void setSysHytActiveZc(SysHytActiveZcBean sysHytActiveZc) {
            this.sysHytActiveZc = sysHytActiveZc;
        }

        public static class CreateUserBean implements Serializable {
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

        public static class HytInfoBean implements Serializable {
            /**
             * auditor : 超级管理员
             * auditorTime : 2018-01-17 16:02:55
             * city : 武汉
             * createTime : 2018-01-17 15:31:04
             * createUser : {"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"wiki","phone":"18672342353","realName":"","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"}
             * email : 476395715@qq.com
             * id : 1
             * idCard : 1234567891011
             * idCardPic1 :
             * idCardPic2 :
             * info : 通过
             * name : 测试后援团
             * picUrl :
             * realUserName : 殷文其
             * startUser : {"belongCompany":"","id":33,"msgRegId":"","name":"鹿晗","phone":"13794622654","realName":"鹿晗","signName":"","startCost":300,"userHeadPortrait":"/static/ys_image/23fea8d4-1a25-427e-843b-bd159721af07.jpg"}
             * statu : 2
             * userContact : 13794613002
             */

            private String auditor;
            private String auditorTime;
            private String city;
            private String createTime;
            private CreateUserBeanX createUser;
            private String email;
            private int id;
            private String idCard;
            private String idCardPic1;
            private String idCardPic2;
            private String info;
            private String name;
            private String picUrl;
            private String realUserName;
            private StartUserBean startUser;
            private int statu;
            private String userContact;

            public String getAuditor() {
                return auditor;
            }

            public void setAuditor(String auditor) {
                this.auditor = auditor;
            }

            public String getAuditorTime() {
                return auditorTime;
            }

            public void setAuditorTime(String auditorTime) {
                this.auditorTime = auditorTime;
            }

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

            public CreateUserBeanX getCreateUser() {
                return createUser;
            }

            public void setCreateUser(CreateUserBeanX createUser) {
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

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
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

            public int getStatu() {
                return statu;
            }

            public void setStatu(int statu) {
                this.statu = statu;
            }

            public String getUserContact() {
                return userContact;
            }

            public void setUserContact(String userContact) {
                this.userContact = userContact;
            }

            public static class CreateUserBeanX implements Serializable {
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

            public static class StartUserBean implements Serializable {
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

        public static class SysHytActiveBigpadBean implements Serializable {
            /**
             * activeId : null
             * bigpads : [{"id":2,"sysHytActiveBigpad":null,"sysHytActiveBigpadInfo":{"address":"测试地址1","id":2,"picUrl":"/static/yuanshi_image/2ec501ee-0e15-4453-93eb-59389110364d.jpg","price":"测试价格1","purpose":"测试用途1","spec":"测试规格1"}},{"id":1,"sysHytActiveBigpad":null,"sysHytActiveBigpadInfo":{"address":"雄楚大道虎泉街交汇处","id":1,"picUrl":"/static/yuanshi_image/1f995cdb-2106-4ee3-ba61-795b55bff20b.jpg","price":"1001/天","purpose":"生日应援/纪念日应援","spec":"18.4米X12.28米（260m2）"}}]
             * id : 1
             * yyje : 2000
             * yzsm : 用资申明
             */

            private Object activeId;
            private int id;
            private double yyje;
            private String yzsm;
            private List<BigpadsBean> bigpads;

            public Object getActiveId() {
                return activeId;
            }

            public void setActiveId(Object activeId) {
                this.activeId = activeId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public double getYyje() {
                return yyje;
            }

            public void setYyje(double yyje) {
                this.yyje = yyje;
            }

            public String getYzsm() {
                return yzsm;
            }

            public void setYzsm(String yzsm) {
                this.yzsm = yzsm;
            }

            public List<BigpadsBean> getBigpads() {
                return bigpads;
            }

            public void setBigpads(List<BigpadsBean> bigpads) {
                this.bigpads = bigpads;
            }

            public static class BigpadsBean implements Serializable {
                /**
                 * id : 2
                 * sysHytActiveBigpad : null
                 * sysHytActiveBigpadInfo : {"address":"测试地址1","id":2,"picUrl":"/static/yuanshi_image/2ec501ee-0e15-4453-93eb-59389110364d.jpg","price":"测试价格1","purpose":"测试用途1","spec":"测试规格1"}
                 */

                private int id;
                private Object sysHytActiveBigpad;
                private SysHytActiveBigpadInfoBean sysHytActiveBigpadInfo;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public Object getSysHytActiveBigpad() {
                    return sysHytActiveBigpad;
                }

                public void setSysHytActiveBigpad(Object sysHytActiveBigpad) {
                    this.sysHytActiveBigpad = sysHytActiveBigpad;
                }

                public SysHytActiveBigpadInfoBean getSysHytActiveBigpadInfo() {
                    return sysHytActiveBigpadInfo;
                }

                public void setSysHytActiveBigpadInfo(SysHytActiveBigpadInfoBean sysHytActiveBigpadInfo) {
                    this.sysHytActiveBigpadInfo = sysHytActiveBigpadInfo;
                }

                public static class SysHytActiveBigpadInfoBean implements Serializable {
                    /**
                     * address : 测试地址1
                     * id : 2
                     * picUrl : /static/yuanshi_image/2ec501ee-0e15-4453-93eb-59389110364d.jpg
                     * price : 测试价格1
                     * purpose : 测试用途1
                     * spec : 测试规格1
                     */

                    private String address;
                    private int id;
                    private String picUrl;
                    private String price;
                    private String purpose;
                    private String spec;

                    public String getAddress() {
                        return address;
                    }

                    public void setAddress(String address) {
                        this.address = address;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getPicUrl() {
                        return picUrl;
                    }

                    public void setPicUrl(String picUrl) {
                        this.picUrl = picUrl;
                    }

                    public String getPrice() {
                        return price;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }

                    public String getPurpose() {
                        return purpose;
                    }

                    public void setPurpose(String purpose) {
                        this.purpose = purpose;
                    }

                    public String getSpec() {
                        return spec;
                    }

                    public void setSpec(String spec) {
                        this.spec = spec;
                    }
                }
            }
        }

        public static class SysHytActiveOpenscreenBean implements Serializable {
            /**
             * activeId : null
             * amount : 100
             * id : 6
             */

            private Object activeId;
            private double amount;
            private int id;

            public Object getActiveId() {
                return activeId;
            }

            public void setActiveId(Object activeId) {
                this.activeId = activeId;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class SysHytActiveZcBean implements Serializable {
            /**
             * activeId : null
             * amount : 100
             * city : 武汉
             * explains : 说明
             * id : 1
             * scale : 规模
             */

            private Object activeId;
            private double amount;
            private String city;
            private String explains;
            private int id;
            private String scale;

            public Object getActiveId() {
                return activeId;
            }

            public void setActiveId(Object activeId) {
                this.activeId = activeId;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getExplains() {
                return explains;
            }

            public void setExplains(String explains) {
                this.explains = explains;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getScale() {
                return scale;
            }

            public void setScale(String scale) {
                this.scale = scale;
            }
        }
    }
}
