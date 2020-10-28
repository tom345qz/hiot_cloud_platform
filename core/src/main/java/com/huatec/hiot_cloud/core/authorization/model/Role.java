package com.huatec.hiot_cloud.core.authorization.model;

import java.io.Serializable;

/**
 * @ Created by liwenqiang  on 2017/7/21 0021 at 下午 2:19  for hiot
 * @ Description: 权限角色
 */
public class Role implements Serializable {

    /**
     * 管理员
     */
    public static final String ADMIN = "ADMIN";

    /**
     * 开发人员
     */
    public static final String DEVELOPER = "DEVELOPER";

    /**
     * 普通用户
     */
    public static final String STAFF = "STAFF";

    /**
     * 智能设备
     */
    public static final String DEVICE = "DEVICE";

}