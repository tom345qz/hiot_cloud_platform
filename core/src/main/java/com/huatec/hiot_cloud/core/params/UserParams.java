package com.huatec.hiot_cloud.core.params;

import com.baomidou.mybatisplus.annotations.TableField;

import java.util.Date;

/**
 * 用户对象参数类
 *
 * @author WUWENBO
 * @since 2020/11/4 8:43
 */
public class UserParams {

    private static final long serialVersionUID = 1L;

    /**
     * 密码
     */
    private String password;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 注册邮箱
     */
    private String email;

    /**
     * 0：开发人员（web），1：普通用户(app)
     */
    private int userType;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
