package com.cucr.myapplication.bean.fenTuan;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cucr on 2017/9/22.
 */

public class QueryFtInfos implements Serializable {


    /**
     * errorMsg :
     * rows : [{"attrFileList":[{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/f7f968ee-10f9-4fc5-8ba2-823581644ea9.jpg","id":55,"locationUrl":"","sort":7,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/67fbed2f-9c2d-4134-8570-5fba42a1351d.png","id":56,"locationUrl":"","sort":6,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/46bb568b-807a-498b-a951-576e8a75acaa.png","id":57,"locationUrl":"","sort":5,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/f44e36bd-950b-4b58-afa6-b2ce6d1e252a.png","id":50,"locationUrl":"","sort":0,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/5217653a-319d-418d-9f38-068effc5ae4c.png","id":58,"locationUrl":"","sort":1,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/35b058a1-0325-4ab4-9046-84ef3fba7b1c.jpg","id":53,"locationUrl":"","sort":4,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/1f88b8ba-2074-4ebe-9058-13bd919a290f.png","id":52,"locationUrl":"","sort":2,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/567e8690-9c8a-4e9c-a21e-6d4da66ba7ac.png","id":51,"locationUrl":"","sort":3,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/a43fa350-399e-4f0e-a2ca-8cd7488a9449.jpg","id":54,"locationUrl":"","sort":8,"timeCount":null,"type":0,"videoPagePic":""}],"commentCount":null,"content":"","creaetTime":"2017-09-22 14:06:45","createUserId":22,"createUserName":"000","dataType":1,"fansTeam":{"contentCount":100,"fansCount":100,"fansTeamName":"微文滔的粉丝团","id":5,"managerId":1,"managerName":"系统管理员","startId":5,"startName":"微文滔"},"giveUpCount":null,"id":14,"isGiveUp":false,"readCount":null,"remarks":"","title":"","type":1,"userHeadPortrait":"123"},{"attrFileList":[],"commentCount":null,"content":"1111111","creaetTime":"2017-09-22 11:51:06","createUserId":22,"createUserName":"000","dataType":1,"fansTeam":{"contentCount":100,"fansCount":100,"fansTeamName":"微文滔的粉丝团","id":5,"managerId":1,"managerName":"系统管理员","startId":5,"startName":"微文滔"},"giveUpCount":null,"id":13,"isGiveUp":false,"readCount":null,"remarks":"","title":"","type":0,"userHeadPortrait":"123"},{"attrFileList":[{"contentId":"9","fileContent":"","fileUrl":"/static/yuanshi_image/f82b0adb-4079-498c-bbb1-c82a58726c1c.png","id":41,"locationUrl":"","sort":2,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"9","fileContent":"","fileUrl":"/static/yuanshi_image/80100e45-7f51-48ce-9b5f-d5648cd90aa5.png","id":47,"locationUrl":"","sort":4,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"9","fileContent":"","fileUrl":"/static/yuanshi_image/4c543f04-1159-47c4-a59c-076495ca7701.png","id":48,"locationUrl":"","sort":3,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"9","fileContent":"","fileUrl":"/static/yuanshi_image/714e45d2-316e-4146-aeec-dee437173279.png","id":46,"locationUrl":"","sort":1,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"9","fileContent":"","fileUrl":"/static/yuanshi_image/a5c3e347-edc9-47e1-85c6-e7343adf3081.png","id":43,"locationUrl":"","sort":7,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"9","fileContent":"","fileUrl":"/static/yuanshi_image/4dbad387-6aaa-4dc8-b663-b0d8939b4185.jpg","id":44,"locationUrl":"","sort":6,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"9","fileContent":"","fileUrl":"/static/yuanshi_image/6d7d77b8-eef2-40a7-a6b8-9f9d07aaa7ea.jpg","id":42,"locationUrl":"","sort":8,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"9","fileContent":"","fileUrl":"/static/yuanshi_image/2f5ed950-8a05-468b-9faa-6d2017ab02e4.png","id":45,"locationUrl":"","sort":5,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"9","fileContent":"","fileUrl":"/static/yuanshi_image/e76bf5ee-edb5-41eb-82d0-1de222398c28.jpg","id":49,"locationUrl":"","sort":0,"timeCount":null,"type":0,"videoPagePic":""}],"commentCount":null,"content":"123","creaetTime":"2017-09-22 11:44:05","createUserId":22,"createUserName":"000","dataType":1,"fansTeam":{"contentCount":100,"fansCount":100,"fansTeamName":"微文滔的粉丝团","id":5,"managerId":1,"managerName":"系统管理员","startId":5,"startName":"微文滔"},"giveUpCount":null,"id":9,"isGiveUp":false,"readCount":null,"remarks":"","title":"","type":1,"userHeadPortrait":"123"}]
     * success : true
     * total : 3
     */

    private String errorMsg;
    private Boolean success;
    private Integer total;
    private List<RowsBean> rows;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getTotal() {
        return total == null ? 0 : total;
    }

    public void setTotal(Integer total) {
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
                    "commentCount=" + commentCount +
                    ", content='" + content + '\'' +
                    ", creaetTime='" + creaetTime + '\'' +
                    ", createUserId=" + createUserId +
                    ", createUserName='" + createUserName + '\'' +
                    ", dataType=" + dataType +
                    ", fansTeam=" + fansTeam +
                    ", giveUpCount=" + giveUpCount +
                    ", id=" + id +
                    ", isGiveUp=" + isGiveUp +
                    ", readCount=" + readCount +
                    ", remarks='" + remarks + '\'' +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", userHeadPortrait='" + userHeadPortrait + '\'' +
                    ", attrFileList=" + attrFileList +
                    ", dssl=" + dssl +
                    ", locationUrl=" + locationUrl +
                    '}';
        }

        /**
         * attrFileList : [{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/f7f968ee-10f9-4fc5-8ba2-823581644ea9.jpg","id":55,"locationUrl":"","sort":7,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/67fbed2f-9c2d-4134-8570-5fba42a1351d.png","id":56,"locationUrl":"","sort":6,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/46bb568b-807a-498b-a951-576e8a75acaa.png","id":57,"locationUrl":"","sort":5,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/f44e36bd-950b-4b58-afa6-b2ce6d1e252a.png","id":50,"locationUrl":"","sort":0,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/5217653a-319d-418d-9f38-068effc5ae4c.png","id":58,"locationUrl":"","sort":1,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/35b058a1-0325-4ab4-9046-84ef3fba7b1c.jpg","id":53,"locationUrl":"","sort":4,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/1f88b8ba-2074-4ebe-9058-13bd919a290f.png","id":52,"locationUrl":"","sort":2,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/567e8690-9c8a-4e9c-a21e-6d4da66ba7ac.png","id":51,"locationUrl":"","sort":3,"timeCount":null,"type":0,"videoPagePic":""},{"contentId":"14","fileContent":"","fileUrl":"/static/yuanshi_image/a43fa350-399e-4f0e-a2ca-8cd7488a9449.jpg","id":54,"locationUrl":"","sort":8,"timeCount":null,"type":0,"videoPagePic":""}]
         * commentCount : null
         * content :
         * creaetTime : 2017-09-22 14:06:45
         * createUserId : 22
         * createUserName : 000
         * dataType : 1
         * fansTeam : {"contentCount":100,"fansCount":100,"fansTeamName":"微文滔的粉丝团","id":5,"managerId":1,"managerName":"系统管理员","startId":5,"startName":"微文滔"}
         * giveUpCount : null
         * id : 14
         * isGiveUp : false
         * readCount : null
         * remarks :
         * title :
         * type : 1
         * userHeadPortrait : 123
         */

        private Integer commentCount;
        private String content;
        private String creaetTime;
        private Integer createUserId;
        private Integer newTransCount;
        private String createUserName;
        private Integer dataType;
        private FansTeamBean fansTeam;
        private SysHytInfo sysHytInfo;
        private Integer giveUpCount;
        private Integer id;
        private Boolean isGiveUp;
        private Integer readCount;
        private String remarks;
        private String locationUrl;
        private String userHeadPortrait;
        private List<AttrFileListBean> attrFileList;
        private int createUserRoleId;

        public String getLocationUrl() {
            return locationUrl;
        }

        public void setLocationUrl(String locationUrl) {
            this.locationUrl = locationUrl;
        }

        private String title;
        private Integer type;
        private Integer dssl;

        public Integer getDssl() {
            return dssl;
        }

        public void setDssl(Integer dssl) {
            this.dssl = dssl;
        }

        public Integer getNewTransCount() {
            return newTransCount;
        }

        public void setNewTransCount(Integer newTransCount) {
            this.newTransCount = newTransCount;
        }


        public int getCreateUserRoleId() {
            return createUserRoleId;
        }

        public void setCreateUserRoleId(int createUserRoleId) {
            this.createUserRoleId = createUserRoleId;
        }

        public Integer getCommentCount() {
            return commentCount == null ? 0 : commentCount;
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

        public Integer getCreateUserId() {
            return createUserId == null ? 0 : createUserId;
        }

        public void setCreateUserId(Integer createUserId) {
            this.createUserId = createUserId;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public Integer getDataType() {
            return dataType == null ? 0 : dataType;
        }

        public void setDataType(Integer dataType) {
            this.dataType = dataType;
        }

        public FansTeamBean getFansTeam() {
            return fansTeam;
        }

        public void setFansTeam(FansTeamBean fansTeam) {
            this.fansTeam = fansTeam;
        }

        public Integer getGiveUpCount() {
            return giveUpCount == null ? 0 : giveUpCount;
        }

        public void setGiveUpCount(Integer giveUpCount) {
            this.giveUpCount = giveUpCount;
        }

        public Integer getId() {
            return id == null ? 0 : id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Boolean isIsGiveUp() {
            return isGiveUp;
        }

        public void setIsGiveUp(Boolean isGiveUp) {
            this.isGiveUp = isGiveUp;
        }

        public Integer getReadCount() {
            return readCount == null ? 0 : readCount;
        }

        public void setReadCount(Integer readCount) {
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

        public Integer getType() {
            return type == null ? 0 : type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getUserHeadPortrait() {
            return userHeadPortrait;
        }

        public void setUserHeadPortrait(String userHeadPortrait) {
            this.userHeadPortrait = userHeadPortrait;
        }

        public List<AttrFileListBean> getAttrFileList() {
            return attrFileList;
        }

        public void setAttrFileList(List<AttrFileListBean> attrFileList) {
            this.attrFileList = attrFileList;
        }

        public static class SysHytInfo implements Serializable {
            private String createTime;
            private Integer createUserId;
            private Integer id;
            private String name;
            private String picUrl;
            private Integer startId;

            public SysHytInfo(String createTime, Integer createUserId, Integer id, String name, String picUrl, Integer startId) {
                this.createTime = createTime;
                this.createUserId = createUserId;
                this.id = id;
                this.name = name;
                this.picUrl = picUrl;
                this.startId = startId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Integer getCreateUserId() {
                return createUserId;
            }

            public void setCreateUserId(Integer createUserId) {
                this.createUserId = createUserId;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
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

            public Integer getStartId() {
                return startId;
            }

            public void setStartId(Integer startId) {
                this.startId = startId;
            }

            @Override
            public String toString() {
                return "SysHytInfo{" +
                        "createTime='" + createTime + '\'' +
                        ", createUserId=" + createUserId +
                        ", id=" + id +
                        ", name='" + name + '\'' +
                        ", picUrl='" + picUrl + '\'' +
                        ", startId=" + startId +
                        '}';
            }
        }

        public static class FansTeamBean implements Serializable {
            /**
             * contentCount : 100
             * fansCount : 100
             * fansTeamName : 微文滔的粉丝团
             * id : 5
             * managerId : 1
             * managerName : 系统管理员
             * startId : 5
             * startName : 微文滔
             */

            private Integer contentCount;
            private Integer fansCount;
            private String fansTeamName;
            private Integer id;
            private Integer managerId;
            private String managerName;
            private Integer startId;
            private String startName;

            public Integer getContentCount() {
                return contentCount == null ? 0 : contentCount;
            }

            public void setContentCount(Integer contentCount) {
                this.contentCount = contentCount;
            }

            public Integer getFansCount() {
                return fansCount == null ? 0 : fansCount;
            }

            public void setFansCount(Integer fansCount) {
                this.fansCount = fansCount;
            }

            public String getFansTeamName() {
                return fansTeamName;
            }

            public void setFansTeamName(String fansTeamName) {
                this.fansTeamName = fansTeamName;
            }

            public Integer getId() {
                return id == null ? 0 : id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getManagerId() {
                return managerId == null ? 0 : managerId;
            }

            public void setManagerId(Integer managerId) {
                this.managerId = managerId;
            }

            public String getManagerName() {
                return managerName;
            }

            public void setManagerName(String managerName) {
                this.managerName = managerName;
            }

            public Integer getStartId() {
                return startId == null ? 0 : startId;
            }

            public void setStartId(Integer startId) {
                this.startId = startId;
            }

            public String getStartName() {
                return startName;
            }

            public void setStartName(String startName) {
                this.startName = startName;
            }
        }

        public static class AttrFileListBean implements Serializable {
            @Override
            public String toString() {
                return "AttrFileListBean{" +
                        "contentId='" + contentId + '\'' +
                        ", fileContent='" + fileContent + '\'' +
                        ", fileUrl='" + fileUrl + '\'' +
                        ", id=" + id +
                        ", locationUrl='" + locationUrl + '\'' +
                        ", sort=" + sort +
                        ", timeCount=" + timeCount +
                        ", type=" + type +
                        ", videoPagePic='" + videoPagePic + '\'' +
                        '}';
            }

            /**
             * contentId : 14
             * fileContent :
             * fileUrl : /static/yuanshi_image/f7f968ee-10f9-4fc5-8ba2-823581644ea9.jpg
             * id : 55
             * locationUrl :
             * sort : 7
             * timeCount : null
             * type : 0
             * videoPagePic :
             */

            private String contentId;
            private String fileContent;
            private String fileUrl;
            private Integer id;
            private String locationUrl;
            private Integer sort;
            private Integer timeCount;
            private Integer type;
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

            public Integer getId() {
                return id == null ? 0 : id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getLocationUrl() {
                return locationUrl;
            }

            public void setLocationUrl(String locationUrl) {
                this.locationUrl = locationUrl;
            }

            public Integer getSort() {
                return sort == null ? 0 : sort;
            }

            public void setSort(Integer sort) {
                this.sort = sort;
            }

            public Integer getTimeCount() {
                return timeCount == null ? 0 : timeCount;
            }

            public void setTimeCount(Integer timeCount) {
                this.timeCount = timeCount;
            }

            public Integer getType() {
                return type == null ? 0 : type;
            }

            public void setType(Integer type) {
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

    @Override
    public String toString() {
        return "QueryFtInfos{" +
                "errorMsg='" + errorMsg + '\'' +
                ", success=" + success +
                ", total=" + total +
                ", rows=" + rows +
                '}';
    }
}
