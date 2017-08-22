package com.cucr.myapplication.model.EditPersonalInfo;

/**
 * Created by 911 on 2017/8/18.
 */

public class PersonMessage {

    /**
     * msg :
     * obj : {"age":0,"areaId":3,"areaName":"","cityId":2,"cityName":"","createdatetime":null,"driverId":"","id":2,"loginname":"18672342353","name":"wiki","otherloginname":"","password":"e10adc3949ba59abbe56e057f20f883e","phone":"18672342353","provinceId":1,"provinceName":"","roleIds":"2","roleNames":"明星用户","sex":1,"sign":"abddcf08-1fd0-45ee-82c9-40f236cfe35a","signName":"???????????","userHeadPortrait":"/static/yuanshi_image/dee25aee-94bc-485b-92e6-fca0d8907ca9.jpg","userPicCover":""}
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
         * age : 0
         * areaId : 3
         * areaName :
         * cityId : 2
         * cityName :
         * createdatetime : null
         * driverId :
         * id : 2
         * loginname : 18672342353
         * name : wiki
         * otherloginname :
         * password : e10adc3949ba59abbe56e057f20f883e
         * phone : 18672342353
         * provinceId : 1
         * provinceName :
         * roleIds : 2
         * roleNames : 明星用户
         * sex : 1
         * sign : abddcf08-1fd0-45ee-82c9-40f236cfe35a
         * signName : ???????????
         * userHeadPortrait : /static/yuanshi_image/dee25aee-94bc-485b-92e6-fca0d8907ca9.jpg
         * userPicCover :
         */

        private int age;
        private int areaId;
        private String areaName;
        private int cityId;
        private String cityName;
        private Object createdatetime;
        private String driverId;
        private int id;
        private String loginname;
        private String name;
        private String otherloginname;
        private String password;
        private String phone;
        private int provinceId;
        private String provinceName;
        private String roleIds;
        private String roleNames;
        private int sex;
        private String sign;
        private String signName;
        private String userHeadPortrait;
        private String userPicCover;
        private String birthday;

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public ObjBean(int age, int areaId, String areaName, int cityId, String cityName, Object createdatetime, String driverId, int id, String loginname, String name, String otherloginname, String password, String phone, int provinceId, String provinceName, String roleIds, String roleNames, int sex, String sign, String signName, String userHeadPortrait, String userPicCover, String birthday) {
            this.age = age;
            this.areaId = areaId;
            this.areaName = areaName;
            this.cityId = cityId;
            this.cityName = cityName;
            this.createdatetime = createdatetime;
            this.driverId = driverId;
            this.id = id;
            this.loginname = loginname;
            this.name = name;
            this.otherloginname = otherloginname;
            this.password = password;
            this.phone = phone;
            this.provinceId = provinceId;
            this.provinceName = provinceName;
            this.roleIds = roleIds;
            this.roleNames = roleNames;
            this.sex = sex;
            this.sign = sign;
            this.signName = signName;
            this.userHeadPortrait = userHeadPortrait;
            this.userPicCover = userPicCover;
            this.birthday = birthday;
        }

        public String getBirthday() {
            return birthday;
        }


        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public Object getCreatedatetime() {
            return createdatetime;
        }

        public void setCreatedatetime(Object createdatetime) {
            this.createdatetime = createdatetime;
        }

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLoginname() {
            return loginname;
        }

        public void setLoginname(String loginname) {
            this.loginname = loginname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOtherloginname() {
            return otherloginname;
        }

        public void setOtherloginname(String otherloginname) {
            this.otherloginname = otherloginname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getRoleIds() {
            return roleIds;
        }

        public void setRoleIds(String roleIds) {
            this.roleIds = roleIds;
        }

        public String getRoleNames() {
            return roleNames;
        }

        public void setRoleNames(String roleNames) {
            this.roleNames = roleNames;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getSignName() {
            return signName;
        }

        public void setSignName(String signName) {
            this.signName = signName;
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

        @Override
        public String toString() {
            return "ObjBean{" +
                    "age=" + age +
                    ", areaId=" + areaId +
                    ", areaName='" + areaName + '\'' +
                    ", cityId=" + cityId +
                    ", cityName='" + cityName + '\'' +
                    ", createdatetime=" + createdatetime +
                    ", driverId='" + driverId + '\'' +
                    ", id=" + id +
                    ", loginname='" + loginname + '\'' +
                    ", name='" + name + '\'' +
                    ", otherloginname='" + otherloginname + '\'' +
                    ", password='" + password + '\'' +
                    ", phone='" + phone + '\'' +
                    ", provinceId=" + provinceId +
                    ", provinceName='" + provinceName + '\'' +
                    ", roleIds='" + roleIds + '\'' +
                    ", roleNames='" + roleNames + '\'' +
                    ", sex=" + sex +
                    ", sign='" + sign + '\'' +
                    ", signName='" + signName + '\'' +
                    ", userHeadPortrait='" + userHeadPortrait + '\'' +
                    ", userPicCover='" + userPicCover + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PersonMessage{" +
                "msg='" + msg + '\'' +
                ", obj=" + obj +
                ", success=" + success +
                '}';
    }
}
