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
    public static final int USER_TYPE_STAFF = 1;

    /**
     * 密码长度
     */
    public static final int PASSWORD_LENGTH = 6;

    /**
     * 用户文件存储目录：user
     */
    public static final String UPLOAD_PATH_USER = "user";

    /**
     * 设备文件存储目录：device
     */
    public static final String UPLOAD_PATH_DEVICE = "device";
    /**
     * 图片加载目录(保存到数据库的相对路径(去除项目名))：
     */
    public static final String DOWNLOAD_PATH_IMG = "uploadfiles/images";
    /**
     * 图片加载目录(保存到数据库的相对路径(去除项目名))：
     */
    public static final String DOWNLOAD_PATH_IMG_DEFAULT = "/uploadfiles/images/default/nophoto.jpg";
    /**
     * 上传文件存储的根路径
     * 在 linux 系统上，路径的连接符的值为 '/'；在Windows系统上，它为 '\\';
     * File.separator可以根据不同的操作系统自动添加目录斜杠
     */
    public static final String UPLOAD_PATH = PropertyConfigurer.getProperty("file.upload.path");
    //"D:\\huatec\\hiot_cloud\\uploadfiles";

    /**
     * 上传文件存储的路径前缀，不含uploadfiles
     */
    public static final String UPLOAD_PATH_PREFIX = PropertyConfigurer.getProperty("file.upload.path.prefix");
    /**
     * 图片存储文件夹
     */
    public static final String UPLOAD_PATH_IMG = "images";

    /**
     * 通道类型：所有
     */
    public static final int DATA_STREAM_TYPE_ALL = 0;

    public static final int DATA_STREAM_TYPE_VALUE = 1;

    public static final int DATA_STREAM_TYPE_SWITCH = 2;

    public static final int DATA_STREAM_TYPE_GPS = 3;

    public static final int DATA_STREAM_TYPE_TXT = 4;

    /**
     * 通道方向：所有
     */
    public static final int DATA_STREAM_DIRECTION_ALL = 0;

    public static final int DATA_STREAM_DIRECTION_UP = 1;

    public static final int DATA_STREAM_DIRECTION_DOWN = 2;

}
