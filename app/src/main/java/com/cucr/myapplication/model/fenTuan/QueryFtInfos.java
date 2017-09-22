package com.cucr.myapplication.model.fenTuan;

import java.util.List;

/**
 * Created by cucr on 2017/9/22.
 */

public class QueryFtInfos {

    /**
     * errorMsg :
     * rows : [{"attrFileList":[{"contentId":"2","fileContent":"","fileUrl":"file111222","id":8,"locationUrl":"","sort":null,"type":0},{"contentId":"2","fileContent":"","fileUrl":"file111","id":7,"locationUrl":"","sort":null,"type":0}],"commentCount":null,"content":"测试内容","creaetTime":"2017-08-30 09:38:42","createUserId":1,"createUserName":"系统管理员","dataType":1,"fansTeam":{"contentCount":1,"fansCount":1,"fansTeamName":"李玟de粉丝团","id":7,"managerId":1,"managerName":"系统管理员","startId":17,"startName":"李玟"},"giveUpCount":0,"id":2,"readCount":0,"remarks":"","title":"测试新闻","type":1}]
     * success : true
     * total : 1
     */

    private String errorMsg;
    private boolean success;
    private int total;
    private List<RowsBean> rows;

    @Override
    public String toString() {
        return "QueryFtInfos{" +
                "errorMsg='" + errorMsg + '\'' +
                ", success=" + success +
                ", total=" + total +
                ", rows=" + rows +
                '}';
    }

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
        @Override
        public String toString() {
            return "RowsBean{" +
                    "commentCount=" + commentCount +
                    ", content='" + content + '\'' +
                    ", creaetTime='" + creaetTime + '\'' +
                    ", createUserId=" + createUserId +
                    ", createUserName='" + createUserName + '\'' +
                    ", dataType=" + dataType +
                    ", fansTeam=" + fansTeam +
                    ", giveUpCount=" + giveUpCount +
                    ", id=" + id +
                    ", readCount=" + readCount +
                    ", remarks='" + remarks + '\'' +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", attrFileList=" + attrFileList +
                    '}';
        }

        /**
         * attrFileList : [{"contentId":"2","fileContent":"","fileUrl":"file111222","id":8,"locationUrl":"","sort":null,"type":0},{"contentId":"2","fileContent":"","fileUrl":"file111","id":7,"locationUrl":"","sort":null,"type":0}]
         * commentCount : null
         * content : 测试内容
         * creaetTime : 2017-08-30 09:38:42
         * createUserId : 1
         * createUserName : 系统管理员
         * dataType : 1
         * fansTeam : {"contentCount":1,"fansCount":1,"fansTeamName":"李玟de粉丝团","id":7,"managerId":1,"managerName":"系统管理员","startId":17,"startName":"李玟"}
         * giveUpCount : 0
         * id : 2
         * readCount : 0
         * remarks :
         * title : 测试新闻
         * type : 1
         */

        private Integer commentCount;
        private String content;
        private String creaetTime;
        private int createUserId;
        private String createUserName;
        private int dataType;
        private FansTeamBean fansTeam;
        private int giveUpCount;
        private int id;
        private int readCount;
        private String remarks;
        private String title;
        private int type;
        private List<AttrFileListBean> attrFileList;


        public Integer getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(Integer commentCount) {
            this.commentCount = commentCount;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreaetTime() {
            return creaetTime;
        }

        public void setCreaetTime(String creaetTime) {
            this.creaetTime = creaetTime;
        }

        public int getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(int createUserId) {
            this.createUserId = createUserId;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }

        public FansTeamBean getFansTeam() {
            return fansTeam;
        }

        public void setFansTeam(FansTeamBean fansTeam) {
            this.fansTeam = fansTeam;
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

        public int getReadCount() {
            return readCount;
        }

        public void setReadCount(int readCount) {
            this.readCount = readCount;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<AttrFileListBean> getAttrFileList() {
            return attrFileList;
        }

        public void setAttrFileList(List<AttrFileListBean> attrFileList) {
            this.attrFileList = attrFileList;
        }

        public static class FansTeamBean {
            @Override
            public String toString() {
                return "FansTeamBean{" +
                        "contentCount=" + contentCount +
                        ", fansCount=" + fansCount +
                        ", fansTeamName='" + fansTeamName + '\'' +
                        ", id=" + id +
                        ", managerId=" + managerId +
                        ", managerName='" + managerName + '\'' +
                        ", startId=" + startId +
                        ", startName='" + startName + '\'' +
                        '}';
            }

            /**
             * contentCount : 1
             * fansCount : 1
             * fansTeamName : 李玟de粉丝团
             * id : 7
             * managerId : 1
             * managerName : 系统管理员
             * startId : 17
             * startName : 李玟
             */

            private int contentCount;
            private int fansCount;
            private String fansTeamName;
            private int id;
            private int managerId;
            private String managerName;
            private int startId;
            private String startName;

            public int getContentCount() {
                return contentCount;
            }

            public void setContentCount(int contentCount) {
                this.contentCount = contentCount;
            }

            public int getFansCount() {
                return fansCount;
            }

            public void setFansCount(int fansCount) {
                this.fansCount = fansCount;
            }

            public String getFansTeamName() {
                return fansTeamName;
            }

            public void setFansTeamName(String fansTeamName) {
                this.fansTeamName = fansTeamName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getManagerId() {
                return managerId;
            }

            public void setManagerId(int managerId) {
                this.managerId = managerId;
            }

            public String getManagerName() {
                return managerName;
            }

            public void setManagerName(String managerName) {
                this.managerName = managerName;
            }

            public int getStartId() {
                return startId;
            }

            public void setStartId(int startId) {
                this.startId = startId;
            }

            public String getStartName() {
                return startName;
            }

            public void setStartName(String startName) {
                this.startName = startName;
            }
        }

        public static class AttrFileListBean {
            /**
             * contentId : 2
             * fileContent :
             * fileUrl : file111222
             * id : 8
             * locationUrl :
             * sort : null
             * type : 0
             */

            private String contentId;
            private String fileContent;
            private String fileUrl;
            private int id;
            private String locationUrl;
            private Object sort;
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

            public Object getSort() {
                return sort;
            }

            public void setSort(Object sort) {
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
}
