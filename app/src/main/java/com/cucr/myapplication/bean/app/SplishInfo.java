package com.cucr.myapplication.bean.app;

/**
 * Created by cucr on 2018/3/21.
 */

public class SplishInfo {

    /**
     * msg :
     * obj : {"contentId":"","fileContent":"","fileUrl":"/static/ys_image/6db297e2-fea9-480e-bf6f-97195f4a8a16.gif","id":676,"locationUrl":"http://www.baidu.com","sort":null,"timeCount":null,"type":2,"videoPagePic":""}
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

    public static class ObjBean {
        /**
         * contentId :
         * fileContent :
         * fileUrl : /static/ys_image/6db297e2-fea9-480e-bf6f-97195f4a8a16.gif
         * id : 676
         * locationUrl : http://www.baidu.com
         * sort : null
         * timeCount : null
         * type : 2
         * videoPagePic :
         */

        private String contentId;
        private String fileContent;
        private String fileUrl;
        private int id;
        private String locationUrl;
        private Object sort;
        private Object timeCount;
        private int type;
        private String videoPagePic;

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

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public Object getTimeCount() {
            return timeCount;
        }

        public void setTimeCount(Object timeCount) {
            this.timeCount = timeCount;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getVideoPagePic() {
            return videoPagePic;
        }

        public void setVideoPagePic(String videoPagePic) {
            this.videoPagePic = videoPagePic;
        }
    }
}
