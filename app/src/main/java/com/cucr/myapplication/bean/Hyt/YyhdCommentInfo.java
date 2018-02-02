package com.cucr.myapplication.bean.Hyt;

import java.util.List;

/**
 * Created by cucr on 2018/1/31.
 */

public class YyhdCommentInfo {

    /**
     * errorMsg :
     * rows : [{"activeId":7,"childList":[{"activeId":7,"childList":[],"comment":"这是一个儿子评论","giveUpCount":1,"id":2,"isGiveUp":true,"releaseTime":"2018-01-22 16:42:30","user":{"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"wiki","phone":"18672342353","realName":"","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"}}],"comment":"这是一个评论","giveUpCount":0,"id":1,"isGiveUp":false,"releaseTime":"2018-01-22 16:36:27","user":{"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"wiki","phone":"18672342353","realName":"","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"}}]
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
         * activeId : 7
         * childList : [{"activeId":7,"childList":[],"comment":"这是一个儿子评论","giveUpCount":1,"id":2,"isGiveUp":true,"releaseTime":"2018-01-22 16:42:30","user":{"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"wiki","phone":"18672342353","realName":"","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"}}]
         * comment : 这是一个评论
         * giveUpCount : 0
         * id : 1
         * isGiveUp : false
         * releaseTime : 2018-01-22 16:36:27
         * user : {"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"wiki","phone":"18672342353","realName":"","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"}
         */

        private int activeId;
        private String comment;
        private int giveUpCount;
        private int id;
        private boolean isGiveUp;
        private String releaseTime;
        private UserBean user;
        private List<ChildListBean> childList;

        public int getActiveId() {
            return activeId;
        }

        public void setActiveId(int activeId) {
            this.activeId = activeId;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getGiveUpCount() {
            return giveUpCount;
        }

        public void setGiveUpCount(int giveUpCount) {
            this.giveUpCount = giveUpCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIsGiveUp() {
            return isGiveUp;
        }

        public void setIsGiveUp(boolean isGiveUp) {
            this.isGiveUp = isGiveUp;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<ChildListBean> getChildList() {
            return childList;
        }

        public void setChildList(List<ChildListBean> childList) {
            this.childList = childList;
        }

        public static class UserBean {
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

        public static class ChildListBean {
            /**
             * activeId : 7
             * childList : []
             * comment : 这是一个儿子评论
             * giveUpCount : 1
             * id : 2
             * isGiveUp : true
             * releaseTime : 2018-01-22 16:42:30
             * user : {"belongCompany":"","id":31,"msgRegId":"190e35f7e079f5a7e78","name":"wiki","phone":"18672342353","realName":"","signName":"","startCost":null,"userHeadPortrait":"/static/yuanshi_image/898c51e2-0c99-47fa-8a5c-409991bc5f6f.png"}
             */

            private int activeId;
            private String comment;
            private int giveUpCount;
            private int id;
            private boolean isGiveUp;
            private String releaseTime;
            private UserBeanX user;
            private List<?> childList;

            public int getActiveId() {
                return activeId;
            }

            public void setActiveId(int activeId) {
                this.activeId = activeId;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public int getGiveUpCount() {
                return giveUpCount;
            }

            public void setGiveUpCount(int giveUpCount) {
                this.giveUpCount = giveUpCount;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isIsGiveUp() {
                return isGiveUp;
            }

            public void setIsGiveUp(boolean isGiveUp) {
                this.isGiveUp = isGiveUp;
            }

            public String getReleaseTime() {
                return releaseTime;
            }

            public void setReleaseTime(String releaseTime) {
                this.releaseTime = releaseTime;
            }

            public UserBeanX getUser() {
                return user;
            }

            public void setUser(UserBeanX user) {
                this.user = user;
            }

            public List<?> getChildList() {
                return childList;
            }

            public void setChildList(List<?> childList) {
                this.childList = childList;
            }

            public static class UserBeanX {
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
        }
    }
}
