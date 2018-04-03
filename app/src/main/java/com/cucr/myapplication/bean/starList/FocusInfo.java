package com.cucr.myapplication.bean.starList;

import java.util.List;

/**
 * Created by cucr on 2017/9/6.
 */

public class FocusInfo {


    /**
     * errorMsg :
     * rows : [{"followTime":"2018-03-29 10:55:56","id":118,"isfollow":0,"start":{"belongCompany":"","companyName":"","id":10002514,"msgRegId":"170976fa8a8b4a4df67","name":"妙恋果粒多°","phone":"13554389941","realName":"","ryimToken":"lIhVhSx+3WyuqImG8D9QFwh6+9YvyxcsZKTG2qJ3/+8QsmHR0FSpTsG8fhgpoWiuAOA8KlLdf94eQFKppiwVrZJwNJIFPwAY","signName":"","startCost":null,"userHeadPortrait":"http://101.132.96.199/static/yuanshi_image/4e6c8e6d-0957-4279-829a-1b6d6124f6a6.png","weiboUrl":""},"user":{"belongCompany":"","companyName":"","id":10002515,"msgRegId":"141fe1da9ea012a5a66","name":"晓月","phone":"18062092535","realName":"","ryimToken":"42DPPuqhMLPfdDbci+SA1gJNjeKytZG7limYQo+HrZaGyE46fRac9ZIhfhSbSi/zdxkcXaGnFBWDJXGrQe05NOYpEp+R4GSG","signName":"I like it","startCost":null,"userHeadPortrait":"http://101.132.96.199/static/yuanshi_image/4d039170-8cf6-41dd-a8e9-d2317da465a5.png","weiboUrl":""}},{"followTime":"2018-03-28 19:01:39","id":82,"isfollow":1,"start":{"belongCompany":"","companyName":"","id":10002514,"msgRegId":"170976fa8a8b4a4df67","name":"妙恋果粒多°","phone":"13554389941","realName":"","ryimToken":"lIhVhSx+3WyuqImG8D9QFwh6+9YvyxcsZKTG2qJ3/+8QsmHR0FSpTsG8fhgpoWiuAOA8KlLdf94eQFKppiwVrZJwNJIFPwAY","signName":"","startCost":null,"userHeadPortrait":"http://101.132.96.199/static/yuanshi_image/4e6c8e6d-0957-4279-829a-1b6d6124f6a6.png","weiboUrl":""},"user":{"belongCompany":"","companyName":"","id":10002517,"msgRegId":"18171adc0306485af20","name":"呼哈","phone":"13554516956","realName":"","ryimToken":"AZnFUjes3lsq5OHSdXrxPwh6+9YvyxcsZKTG2qJ3/+8QsmHR0FSpTlNtG0SwLSmS2LhFgRQ8M4OlSyJKMBkP2ZJwNJIFPwAY","signName":"","startCost":null,"userHeadPortrait":"http://101.132.96.199/static/touxiang.png","weiboUrl":""}}]
     * success : true
     * total : 2
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

        public RowsBean(String followTime, int id, StartBean start) {
            this.followTime = followTime;
            this.id = id;
            this.start = start;
        }


        /**
         * followTime : 2018-03-29 10:55:56
         * id : 118
         * isfollow : 0
         * start : {"belongCompany":"","companyName":"","id":10002514,"msgRegId":"170976fa8a8b4a4df67","name":"妙恋果粒多°","phone":"13554389941","realName":"","ryimToken":"lIhVhSx+3WyuqImG8D9QFwh6+9YvyxcsZKTG2qJ3/+8QsmHR0FSpTsG8fhgpoWiuAOA8KlLdf94eQFKppiwVrZJwNJIFPwAY","signName":"","startCost":null,"userHeadPortrait":"http://101.132.96.199/static/yuanshi_image/4e6c8e6d-0957-4279-829a-1b6d6124f6a6.png","weiboUrl":""}
         * user : {"belongCompany":"","companyName":"","id":10002515,"msgRegId":"141fe1da9ea012a5a66","name":"晓月","phone":"18062092535","realName":"","ryimToken":"42DPPuqhMLPfdDbci+SA1gJNjeKytZG7limYQo+HrZaGyE46fRac9ZIhfhSbSi/zdxkcXaGnFBWDJXGrQe05NOYpEp+R4GSG","signName":"I like it","startCost":null,"userHeadPortrait":"http://101.132.96.199/static/yuanshi_image/4d039170-8cf6-41dd-a8e9-d2317da465a5.png","weiboUrl":""}
         */

        private String followTime;
        private int id;
        private int isfollow;
        private StartBean start;
        private UserBean user;

        public String getFollowTime() {
            return followTime;
        }

        public void setFollowTime(String followTime) {
            this.followTime = followTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsfollow() {
            return isfollow;
        }

        public void setIsfollow(int isfollow) {
            this.isfollow = isfollow;
        }

        public StartBean getStart() {
            return start;
        }

        public void setStart(StartBean start) {
            this.start = start;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class StartBean {
            public StartBean(int id, String userHeadPortrait) {
                this.id = id;
                this.userHeadPortrait = userHeadPortrait;
            }

            /**
             * belongCompany :
             * companyName :
             * id : 10002514
             * msgRegId : 170976fa8a8b4a4df67
             * name : 妙恋果粒多°
             * phone : 13554389941
             * realName :
             * ryimToken : lIhVhSx+3WyuqImG8D9QFwh6+9YvyxcsZKTG2qJ3/+8QsmHR0FSpTsG8fhgpoWiuAOA8KlLdf94eQFKppiwVrZJwNJIFPwAY
             * signName :
             * startCost : null
             * userHeadPortrait : http://101.132.96.199/static/yuanshi_image/4e6c8e6d-0957-4279-829a-1b6d6124f6a6.png
             * weiboUrl :
             */

            private String belongCompany;
            private String companyName;
            private int id;
            private String msgRegId;
            private String name;
            private String phone;
            private String realName;
            private String ryimToken;
            private String signName;
            private Object startCost;
            private String userHeadPortrait;
            private String weiboUrl;

            public String getBelongCompany() {
                return belongCompany;
            }

            public void setBelongCompany(String belongCompany) {
                this.belongCompany = belongCompany;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
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

            public String getWeiboUrl() {
                return weiboUrl;
            }

            public void setWeiboUrl(String weiboUrl) {
                this.weiboUrl = weiboUrl;
            }
        }

        public static class UserBean {
            /**
             * belongCompany :
             * companyName :
             * id : 10002515
             * msgRegId : 141fe1da9ea012a5a66
             * name : 晓月
             * phone : 18062092535
             * realName :
             * ryimToken : 42DPPuqhMLPfdDbci+SA1gJNjeKytZG7limYQo+HrZaGyE46fRac9ZIhfhSbSi/zdxkcXaGnFBWDJXGrQe05NOYpEp+R4GSG
             * signName : I like it
             * startCost : null
             * userHeadPortrait : http://101.132.96.199/static/yuanshi_image/4d039170-8cf6-41dd-a8e9-d2317da465a5.png
             * weiboUrl :
             */

            private String belongCompany;
            private String companyName;
            private int id;
            private String msgRegId;
            private String name;
            private String phone;
            private String realName;
            private String ryimToken;
            private String signName;
            private Object startCost;
            private String userHeadPortrait;
            private String weiboUrl;

            public String getBelongCompany() {
                return belongCompany;
            }

            public void setBelongCompany(String belongCompany) {
                this.belongCompany = belongCompany;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
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

            public String getWeiboUrl() {
                return weiboUrl;
            }

            public void setWeiboUrl(String weiboUrl) {
                this.weiboUrl = weiboUrl;
            }
        }
    }
}
