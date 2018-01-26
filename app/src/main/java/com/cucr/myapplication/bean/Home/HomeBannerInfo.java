package com.cucr.myapplication.bean.Home;

import java.util.List;

/**
 * Created by cucr on 2017/8/30.
 */

public class HomeBannerInfo {


    /**
     * msg :
     * obj : [{"contentId":"","fileContent":"","fileUrl":"/static/ys_image/8559ee2c-bc81-4ad1-9ad4-7b8f8c8eb6e4.gif","id":3,"locationUrl":"123123213","sort":2,"type":1},{"contentId":"","fileContent":"","fileUrl":"/static/ys_image/8d8cc6bb-02d6-4d5d-aa6d-a7605d6b909a.jpg","id":6,"locationUrl":"","sort":2,"type":1}]
     * success : true
     */

    private String msg;
    private boolean success;
    private List<ObjBean> obj;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * contentId :
         * fileContent :
         * fileUrl : /static/ys_image/8559ee2c-bc81-4ad1-9ad4-7b8f8c8eb6e4.gif
         * id : 3
         * locationUrl : 123123213
         * sort : 2
         * type : 1
         */

        private String contentId;
        private String fileContent;
        private String fileUrl;
        private int id;
        private String locationUrl;
        private int sort;
        private int type;

        public String getContentId() {
            return contentId;
        }

        public void setContentId(String contentId) {
            this.contentId = contentId;
        }

        public String getFileContent() {
            return fileContent;
        }

        public void setFileContent(String fileContent) {
            this.fileContent = fileContent;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLocationUrl() {
            return locationUrl;
        }

        public void setLocationUrl(String locationUrl) {
            this.locationUrl = locationUrl;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
