package com.cucr.myapplication.model.starList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cucr on 2017/9/5.
 */

public class StarListInfos implements Serializable {

    /**
     * errorMsg :
     * rows : [{"fansCount":"null","id":"5","realName":"微文滔","startShowPic":"/static/ys_image/c5aabb6a-f5e1-47bc-98a4-daaaa8a2149e.gif","userPicCover":"/static/ys_image/1b71333d-c126-4bbe-8a56-ba48382c6be2.gif"}]
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
        /**
         * fansCount : null
         * id : 5
         * realName : 微文滔
         * startShowPic : /static/ys_image/c5aabb6a-f5e1-47bc-98a4-daaaa8a2149e.gif
         * userPicCover : /static/ys_image/1b71333d-c126-4bbe-8a56-ba48382c6be2.gif
         */

        private String fansCount;
        private int id;
        private String realName;
        private String startShowPic;
        private String userPicCover;
        private int isfollow;
        private int startCost;

        public int getStartCost() {
            return startCost;
        }

        public void setStartCost(int startCost) {
            this.startCost = startCost;
        }

        public int getIsfollow() {
            return isfollow;
        }

        public void setIsfollow(int isfollow) {
            this.isfollow = isfollow;
        }

        public String getFansCount() {
            return fansCount;
        }

        public void setFansCount(String fansCount) {
            this.fansCount = fansCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getStartShowPic() {
            return startShowPic;
        }

        public void setStartShowPic(String startShowPic) {
            this.startShowPic = startShowPic;
        }

        public String getUserPicCover() {
            return userPicCover;
        }

        public void setUserPicCover(String userPicCover) {
            this.userPicCover = userPicCover;
        }

        @Override
        public String toString() {
            return "RowsBean{" +
                    "fansCount='" + fansCount + '\'' +
                    ", id=" + id +
                    ", realName='" + realName + '\'' +
                    ", startShowPic='" + startShowPic + '\'' +
                    ", userPicCover='" + userPicCover + '\'' +
                    ", isfollow=" + isfollow +
                    ", startCost=" + startCost +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "StarListInfos{" +
                "errorMsg='" + errorMsg + '\'' +
                ", success=" + success +
                ", total=" + total +
                ", rows=" + rows +
                '}';
    }
}
