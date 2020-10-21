package com.huatec.hiot_cloud.core.autogenerator.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author code generator
 * @since 2020-10-07
 */
public class Users extends Model<Users> {

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
    @TableField("is_active")
    private Integer isActive;
    /**
     * 关联时间
     */
    @TableField("date_joined")
    private Date dateJoined;
    /**
     * 是否超级管理员，0：不是，1：是
     */
    @TableField("is_superuser")
    private Integer isSuperuser;
    /**
     * 是否普通用户，0：不是，1：是
     */
    @TableField("is_staff")
    private Integer isStaff;
    /**
     * 是否开发者，0：不是，1：是
     */
    @TableField("is_developer")
    private Integer isDeveloper;
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

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public Integer getIsSuperuser() {
        return isSuperuser;
    }

    public void setIsSuperuser(Integer isSuperuser) {
        this.isSuperuser = isSuperuser;
    }

    public Integer getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(Integer isStaff) {
        this.isStaff = isStaff;
    }

    public Integer getIsDeveloper() {
        return isDeveloper;
    }

    public void setIsDeveloper(Integer isDeveloper) {
        this.isDeveloper = isDeveloper;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", password=" + password +
                ", lastlogin=" + lastlogin +
                ", username=" + username +
                ", email=" + email +
                ", isActive=" + isActive +
                ", dateJoined=" + dateJoined +
                ", isSuperuser=" + isSuperuser +
                ", isStaff=" + isStaff +
                ", isDeveloper=" + isDeveloper +
                ", img=" + img +
                ", phone=" + phone +
                "}";
    }
}
