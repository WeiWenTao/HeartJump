package com.cucr.myapplication.bean.invate;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cucr on 2018/6/19.
 */

public class InvateSerchStar implements Serializable {

    /**
     * errorMsg :
     * rows : [{"id":7270,"userHeadPortrait":"http://webdata.cucr.cn/d5ab37a6d236d79ce519c80263e367bb.jpg","userPicCover":"http://webdata.cucr.cn/d5ab37a6d236d79ce519c80263e367bb.jpg","realName":"胡冰卿","code":"sTIEjh"},{"id":687,"userHeadPortrait":"http://webdata.cucr.cn/ea7c8e1fac82b4c70fb661f2a512ff51.jpg","userPicCover":"http://webdata.cucr.cn/ea7c8e1fac82b4c70fb661f2a512ff51.jpg","realName":"胡歌","code":"sTIEjh"},{"id":642,"userHeadPortrait":"http://webdata.cucr.cn/4ab0a9dfc1a8abb7a72e86012ed5bc7d.jpg","userPicCover":"http://webdata.cucr.cn/4ab0a9dfc1a8abb7a72e86012ed5bc7d.jpg","realName":"胡静","code":"sTIEjh"},{"id":339,"userHeadPortrait":"http://webdata.cucr.cn/993823b9f6e7971e3484365808422ef3.jpg","userPicCover":"http://webdata.cucr.cn/993823b9f6e7971e3484365808422ef3.jpg","realName":"胡军","code":"sTIEjh"},{"id":879,"userHeadPortrait":"http://webdata.cucr.cn/26b74b418fd8d0db5afdee9f3119cb08.jpg","userPicCover":"http://webdata.cucr.cn/26b74b418fd8d0db5afdee9f3119cb08.jpg","realName":"胡可","code":"sTIEjh"},{"id":2680,"userHeadPortrait":"http://webdata.cucr.cn/60fc41fdc4d1cc591c30b37550c58d1e.jpg","userPicCover":"http://webdata.cucr.cn/60fc41fdc4d1cc591c30b37550c58d1e.jpg","realName":"胡夏","code":"sTIEjh"},{"id":1010,"userHeadPortrait":"http://webdata.cucr.cn/ec76abc9d46a6715f71880c3b0830832.jpg","userPicCover":"http://webdata.cucr.cn/ec76abc9d46a6715f71880c3b0830832.jpg","realName":"胡杏儿","code":"sTIEjh"},{"id":526,"userHeadPortrait":"http://webdata.cucr.cn/3b29e1449ebf86d4ed98af89cdcc4226.jpg","userPicCover":"http://webdata.cucr.cn/3b29e1449ebf86d4ed98af89cdcc4226.jpg","realName":"胡彦斌","code":"sTIEjh"},{"id":4868,"userHeadPortrait":"http://webdata.cucr.cn/df763dd1bd8b2ea72a7ca5473f523adb.jpg","userPicCover":"http://webdata.cucr.cn/df763dd1bd8b2ea72a7ca5473f523adb.jpg","realName":"胡海泉","code":"sTIEjh"}]
     * success : true
     * total : 0
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
         * id : 7270
         * userHeadPortrait : http://webdata.cucr.cn/d5ab37a6d236d79ce519c80263e367bb.jpg
         * userPicCover : http://webdata.cucr.cn/d5ab37a6d236d79ce519c80263e367bb.jpg
         * realName : 胡冰卿
         * code : sTIEjh
         */

        private int id;
        private String userHeadPortrait;
        private String userPicCover;
        private String realName;
        private String code;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserHeadPortrait() {
            return userHeadPortrait;
        }

        public void setUserHeadPortrait(String userHeadPortrait) {
            this.userHeadPortrait = userHeadPortrait;
        }

        public String getUserPicCover() {
            return userPicCover;
        }

        public void setUserPicCover(String userPicCover) {
            this.userPicCover = userPicCover;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
