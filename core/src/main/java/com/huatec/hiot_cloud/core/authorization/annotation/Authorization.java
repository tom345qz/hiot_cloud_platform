package com.huatec.hiot_cloud.core.authorization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ Created by liwenqiang on 2017/5/10 0010.
 * @ Description:在Controller的方法上使用此注解，该方法在映射时会检查用户是否登录，并返回错误信息
 * 不加此注解拦截器AuthorizationInterceptor也会自动拦截，
 * 只不过没有错误信息，只返回HTTP code:400 Bad Request – 请求无效,需要附加细节解释
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {
}