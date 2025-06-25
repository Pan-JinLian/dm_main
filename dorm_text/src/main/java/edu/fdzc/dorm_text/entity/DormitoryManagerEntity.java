package edu.fdzc.dorm_text.entity;

import java.io.Serializable;

public class DormitoryManagerEntity implements Serializable {

    /**
     * 宿舍管理员编号
     */
    private String id;

    /**
     * 管理员姓名
     */
    private String name;

    /**
     * 管理员密码
     */
    private String password = "e10adc3949ba59abbe56e057f20f883e";

    /**
     * 管理员性别
     */
    private String gender;

    /**
     * 管理员电话
     */
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
