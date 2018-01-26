package com.cucr.myapplication.bean.fenTuan;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cucr on 2017/11/7.
 */

public class FtBackpackInfo implements Serializable {


    /**
     * msg :
     * obj : {"zjg":100,"list":[{"balance":100,"id":1,"userAccountType":{"id":1,"name":"道具1","picUrl":"","proportion":1},"userId":31}]}
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

    public static class ObjBean implements Serializable  {
        /**
         * zjg : 100
         * list : [{"balance":100,"id":1,"userAccountType":{"id":1,"name":"道具1","picUrl":"","proportion":1},"userId":31}]
         */

        private int zjg;
        private List<ListBean> list;

        public int getZjg() {
            return zjg;
        }

        public void setZjg(int zjg) {
            this.zjg = zjg;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable  {
            /**
             * balance : 100
             * id : 1
             * userAccountType : {"id":1,"name":"道具1","picUrl":"","proportion":1}
             * userId : 31
             */

            private int balance;
            private int id;
            private UserAccountTypeBean userAccountType;
            private int userId;

            public int getBalance() {
                return balance;
            }

            public void setBalance(int balance) {
                this.balance = balance;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public UserAccountTypeBean getUserAccountType() {
                return userAccountType;
            }

            public void setUserAccountType(UserAccountTypeBean userAccountType) {
                this.userAccountType = userAccountType;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public static class UserAccountTypeBean implements Serializable  {
                /**
                 * id : 1
                 * name : 道具1
                 * picUrl :
                 * proportion : 1
                 */

                private int id;
                private String name;
                private String picUrl;
                private int proportion;

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

                public String getPicUrl() {
                    return picUrl;
                }

                public void setPicUrl(String picUrl) {
                    this.picUrl = picUrl;
                }

                public int getProportion() {
                    return proportion;
                }

                public void setProportion(int proportion) {
                    this.proportion = proportion;
                }
            }
        }
    }
}
