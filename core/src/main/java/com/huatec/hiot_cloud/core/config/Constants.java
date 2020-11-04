package com.huatec.hiot_cloud.core.config;

/**
 * 常量类
 *
 * @author WUWENBO
 * @since 2020/10/21 10:21
 */
public class Constants {


    /**
     * 登录类型app
     */
    public static final String LOGIN_CODE_APP = "app";

    /**
     * 登录类型web
     */
    public static final String LOGIN_CODE_WEB = "web";

    /**
     * 状态为真
     */
    public static final int STATUS_TRUE = 1;

    /**
     * 状态为假
     */
    public static final int STATUS_FALSE = 0;

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * 存储当前设备id的字段名
     */
    public static final String CURRENT_DEVICE_ID = "CURRENT_DEVICE_ID";

    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * Authorization(token)的类型:用户类型
     */
    public static final String AUTHORIZATION_USER = "user";
    /**
     * Authorization(token)的类型:设备类型
     */
    public static final String AUTHORIZATION_DEVICE = "dev";
    /**
     * 用户token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 2160; //90天
    /**
     * 设备token有效期（天）
     */
    public static final int TOKEN_EXPIRES_DAY = 180; //180天

    /**
     * 用户类型开发人员
     */
    public static final int USER_TYPE_DEVELOPER = 0;

    /**
     * 用户类型普通用户
     */
    public static final int USER_TYPE_STAFF = 0;
}
