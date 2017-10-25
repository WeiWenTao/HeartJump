package com.cucr.myapplication.model.fenTuan;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cucr on 2017/10/18.
 */

public class FtCommentInfo implements Serializable {

    /**
     * errorMsg :
     * rows : [{"childList":[{"childList":[],"comment":"2","contentId":2,"giveUpCount":null,"id":2,"isGiveUp":0,"releaseTime":"2017-08-30 15:02:01","type":0,"user":{"id":17,"name":"wiki","userHeadPortrait":"/static/ys_image/933dd507-e2a2-4e33-a573-3271cbcbfedf.png"}}],"comment":"2","contentId":2,"giveUpCount":2,"id":1,"isGiveUp":0,"releaseTime":"2017-08-30 15:00:06","type":0,"user":{"id":17,"name":"wiki","userHeadPortrait":"/static/ys_image/933dd507-e2a2-4e33-a573-3271cbcbfedf.png"}}]
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

    public static class RowsBean implements Serializable {
        @Override
        public String toString() {
            return "RowsBean{" +
                    "comment='" + comment + '\'' +
                    ", contentId=" + contentId +
                    ", giveUpCount=" + giveUpCount +
                    ", id=" + id +
                    ", isGiveUp=" + isGiveUp +
                    ", releaseTime='" + releaseTime + '\'' +
                    ", type=" + type +
                    ", user=" + user +
                    ", childList=" + childList +
                    '}';
        }

        /**
         * childList : [{"childList":[],"comment":"2","contentId":2,"giveUpCount":null,"id":2,"isGiveUp":0,"releaseTime":"2017-08-30 15:02:01","type":0,"user":{"id":17,"name":"wiki","userHeadPortrait":"/static/ys_image/933dd507-e2a2-4e33-a573-3271cbcbfedf.png"}}]
         * comment : 2
         * contentId : 2
         * giveUpCount : 2
         * id : 1
         * isGiveUp : 0
         * releaseTime : 2017-08-30 15:00:06
         * type : 0
         * user : {"id":17,"name":"wiki","userHeadPortrait":"/static/ys_image/933dd507-e2a2-4e33-a573-3271cbcbfedf.png"}
         */

        private String comment;
        private int contentId;
        private Integer giveUpCount;
        private int id;
        private boolean isGiveUp;
        private String releaseTime;
        private int type;
        private UserBean user;
        private List<FtCommentInfo.RowsBean> childList;
        private int childListSize;

        public int getCommentCount() {
            return childListSize;
        }

        public void setCommentCount(int childListSize) {
            this.childListSize = childListSize;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getContentId() {
            return contentId;
        }

        public void setContentId(int contentId) {
            this.contentId = contentId;
        }

        public Integer getGiveUpCount() {
            return giveUpCount;
        }

        public void setGiveUpCount(Integer giveUpCount) {
            this.giveUpCount = giveUpCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean getIsGiveUp() {
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<FtCommentInfo.RowsBean> getChildList() {
            return childList;
        }

        public void setChildList(List<FtCommentInfo.RowsBean> childList) {
            this.childList = childList;
        }

        public static class UserBean implements Serializable {
            @Override
            public String toString() {
                return "UserBean{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", userHeadPortrait='" + userHeadPortrait + '\'' +
                        '}';
            }

            /**
             * id : 17
             * name : wiki
             * userHeadPortrait : /static/ys_image/933dd507-e2a2-4e33-a573-3271cbcbfedf.png
             */

            private int id;
            private String name;
            private String userHeadPortrait;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUserHeadPortrait() {
                return userHeadPortrait;
            }

            public void setUserHeadPortrait(String userHeadPortrait) {
                this.userHeadPortrait = userHeadPortrait;
            }
        }

        /*public static class ChildListBean implements Serializable {

            @Override
            public String toString() {
                return "ChildListBean{" +
                        "comment='" + comment + '\'' +
                        ", contentId=" + contentId +
                        ", giveUpCount=" + giveUpCount +
                        ", id=" + id +
                        ", isGiveUp=" + isGiveUp +
                        ", releaseTime='" + releaseTime + '\'' +
                        ", type=" + type +
                        ", user=" + user +
                        ", childList=" + childList +
                        '}';
            }

            *//**
         * childList : []
         * comment : 2
         * contentId : 2
         * giveUpCount : null
         * id : 2
         * isGiveUp : 0
         * releaseTime : 2017-08-30 15:02:01
         * type : 0
         * user : {"id":17,"name":"wiki","userHeadPortrait":"/static/ys_image/933dd507-e2a2-4e33-a573-3271cbcbfedf.png"}
         *//*

            private String comment;
            private int contentId;
            private Object giveUpCount;
            private int id;
            private boolean isGiveUp;
            private String releaseTime;
            private int type;
            private UserBeanX user;
            private List<?> childList;


            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public int getContentId() {
                return contentId;
            }

            public void setContentId(int contentId) {
                this.contentId = contentId;
            }

            public Object getGiveUpCount() {
                return giveUpCount;
            }

            public void setGiveUpCount(Object giveUpCount) {
                this.giveUpCount = giveUpCount;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean getIsGiveUp() {
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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

            public static class UserBeanX implements Serializable {
                *//**
         * id : 17
         * name : wiki
         * userHeadPortrait : /static/ys_image/933dd507-e2a2-4e33-a573-3271cbcbfedf.png
         *//*

                private int id;
                private String name;
                private String userHeadPortrait;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUserHeadPortrait() {
                    return userHeadPortrait;
                }

                public void setUserHeadPortrait(String userHeadPortrait) {
                    this.userHeadPortrait = userHeadPortrait;
                }

                @Override
                public String toString() {
                    return "UserBeanX{" +
                            "id=" + id +
                            ", name='" + name + '\'' +
                            ", userHeadPortrait='" + userHeadPortrait + '\'' +
                            '}';
                }
            }
        }*/
    }

    @Override
    public String toString() {
        return "FtCommentInfo{" +
                "errorMsg='" + errorMsg + '\'' +
                ", success=" + success +
                ", total=" + total +
                ", rows=" + rows +
                '}';
    }
}
