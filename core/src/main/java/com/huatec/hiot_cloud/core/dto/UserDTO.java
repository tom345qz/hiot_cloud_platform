package com.huatec.hiot_cloud.core.dto;

import com.baomidou.mybatisplus.annotations.TableField;

import java.util.Date;

/**
 * 用户对象DTO
 *
 * @author WUWENBO
 * @since 2020/10/28 10:19
 */
public class UserDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;
    /**
     * 密码
     */
    private String password;
    /**
     * 最后一次登录时间
     */
    private Date lastlogin;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 注册邮箱
     */
    private String email;
    /**
     * 是否可用
     */
    private Integer active;
    /**
     * 关联时间
     */
    private Date dateJoined;
    /**
     * 是否超级管理员，0：不是，1：是
     */
    private Integer superuser;
    /**
     * 是否普通用户，0：不是，1：是
     */
    private Integer staff;
    /**
     * 是否开发者，0：不是，1：是
     */
    private Integer developer;
    /**
     * 用户头像
     */
    private String img;
    /**
     * 用户手机
     */
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public Integer getSuperuser() {
        return superuser;
    }

    public void setSuperuser(Integer superuser) {
        this.superuser = superuser;
    }

    public Integer getStaff() {
        return staff;
    }

    public void setStaff(Integer staff) {
        this.staff = staff;
    }

    public Integer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Integer developer) {
        this.developer = developer;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
