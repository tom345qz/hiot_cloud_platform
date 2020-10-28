package com.huatec.hiot_cloud.core.authorization.manager;


import com.huatec.hiot_cloud.core.autogenerator.entity.User;
import com.huatec.hiot_cloud.core.config.BooleanTypeEnum;

/**
 * @ Created by zj  on 2017/5/22 at 9:42  for hiot.
 * @ Description:判断持有者、开发者、超级管理者
 */
public class PermissionManager {

    //是否普通用户角色
    public static boolean roleStaff(User user) {
        int isStaff = user.getIsStaff() == null ? BooleanTypeEnum.FALSE.getNumber() : user.getIsStaff();
        if (isStaff == BooleanTypeEnum.TRUE.getNumber()) {
            return true;
        }
        return false;
    }

    //是否开发者角色
    public static boolean roleDeveloper(User user) {
        int isDeveloper = user.getIsDeveloper() == null ? BooleanTypeEnum.FALSE.getNumber() : user.getIsDeveloper();
        if (isDeveloper == BooleanTypeEnum.TRUE.getNumber()) {
            return true;
        }
        return false;
    }

    //是否管理员角色
    public static boolean roleAdmin(User user) {
        int superuser = user.getIsSuperuser() == null ? BooleanTypeEnum.FALSE.getNumber() : user.getIsSuperuser();
        if (superuser == BooleanTypeEnum.TRUE.getNumber()) {
            return true;
        }
        return false;
    }

}