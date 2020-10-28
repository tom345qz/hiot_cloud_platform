package com.huatec.hiot_cloud.core.authorization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ Created by liwenqiang on 2017/5/10 0010.
 * @ Description:在Controller的方法参数中使用此注解，该方法在映射时会判断当前用户的操作权限,
 * 具体在AuthorizationInterceptor内判断
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permissions {
    /**
     * 具体角色参见
     * * see com.huatec.radioclub.core.authorization.model.Role
     **/
    String role() default "";
}