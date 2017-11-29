package com.cucr.myapplication.model.starList;

import java.util.List;

/**
 * Created by cucr on 2017/11/28.
 */

public class StarRequires {

    /**
     * msg : {"activeScene":0,"assistantNum":2,"bed":1,"carNum":1,"economyClass":2,"firstClass":1,"fsjj":1,"hzs":1,"id":5,"qtyq":"没得","startTimeList":[{"id":9,"time":"2017-01-02 00:00:00"},{"id":7,"time":"2017-01-03 00:00:00"},{"id":8,"time":"2017-01-01 00:00:00"}],"userId":17}
     * obj :
     * success : true
     */

    private String msg;
    private  MsgBean obj;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MsgBean getObj() {
        return obj;
    }

    public void setObj(MsgBean obj) {
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class MsgBean {
        /**
         * activeScene : 0
         * assistantNum : 2
         * bed : 1
         * carNum : 1
         * economyClass : 2
         * firstClass : 1
         * fsjj : 1
         * hzs : 1
         * id : 5
         * qtyq : 没得
         * startTimeList : [{"id":9,"time":"2017-01-02 00:00:00"},{"id":7,"time":"2017-01-03 00:00:00"},{"id":8,"time":"2017-01-01 00:00:00"}]
         * userId : 17
         */

        private int activeScene;
        private int assistantNum;
        private int bed;
        private int carNum;
        private int economyClass;
        private int firstClass;
        private int fsjj;
        private int hzs;
        private int id;
        private String qtyq;
        private int userId;
        private List<StartTimeListBean> startTimeList;

        public int getActiveScene() {
            return activeScene;
        }

        public void setActiveScene(int activeScene) {
            this.activeScene = activeScene;
        }

        public int getAssistantNum() {
            return assistantNum;
        }

        public void setAssistantNum(int assistantNum) {
            this.assistantNum = assistantNum;
        }

        public int getBed() {
            return bed;
        }

        public void setBed(int bed) {
            this.bed = bed;
        }

        public int getCarNum() {
            return carNum;
        }

        public void setCarNum(int carNum) {
            this.carNum = carNum;
        }

        public int getEconomyClass() {
            return economyClass;
        }

        public void setEconomyClass(int economyClass) {
            this.economyClass = economyClass;
        }

        public int getFirstClass() {
            return firstClass;
        }

        public void setFirstClass(int firstClass) {
            this.firstClass = firstClass;
        }

        public int getFsjj() {
            return fsjj;
        }

        public void setFsjj(int fsjj) {
            this.fsjj = fsjj;
        }

        public int getHzs() {
            return hzs;
        }

        public void setHzs(int hzs) {
            this.hzs = hzs;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getQtyq() {
            return qtyq;
        }

        public void setQtyq(String qtyq) {
            this.qtyq = qtyq;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public List<StartTimeListBean> getStartTimeList() {
            return startTimeList;
        }

        public void setStartTimeList(List<StartTimeListBean> startTimeList) {
            this.startTimeList = startTimeList;
        }

        public static class StartTimeListBean {
            /**
             * id : 9
             * time : 2017-01-02 00:00:00
             */

            private int id;
            private String time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            @Override
            public String toString() {
                return "StartTimeListBean{" +
                        "id=" + id +
                        ", time='" + time + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "activeScene=" + activeScene +
                    ", assistantNum=" + assistantNum +
                    ", bed=" + bed +
                    ", carNum=" + carNum +
                    ", economyClass=" + economyClass +
                    ", firstClass=" + firstClass +
                    ", fsjj=" + fsjj +
                    ", hzs=" + hzs +
                    ", id=" + id +
                    ", qtyq='" + qtyq + '\'' +
                    ", userId=" + userId +
                    ", startTimeList=" + startTimeList +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "StarRequires{" +
                "msg='" + msg + '\'' +
                ", obj=" + obj +
                ", success=" + success +
                '}';
    }
}
