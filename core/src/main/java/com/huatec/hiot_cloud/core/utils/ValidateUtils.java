package com.huatec.hiot_cloud.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数校验工具类
 *
 * @author WUWENBO
 * @since 2020/11/2 16:48
 */
public class ValidateUtils {

    /**
     * 校验邮箱格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean validateEmail(String email) {
        String rule = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        return validateString(email, rule);
    }

    /**
     * 校验密码是否正确
     *
     * @param password
     * @return
     */
    public static boolean validatePassword(String password) {
        String rule = "^[a-zA-Z]\\w{5,17}$";//密码字幕数字下划线组成6-18位
        //正则表达式的模式
        return validateString(password, rule);
    }

    /**
     * 校验用户名是否正确
     *
     * @param userName
     * @return
     */
    public static boolean validateUserName(String userName) {
        String rule = "^[\\u4e00-\\u9fff\\w]{3,16}$";//字母、数字、中文、下划线组成，3-16位
        return validateString(userName, rule);
    }

    /**
     * 校验参数和规则是否匹配
     *
     * @param params 参数
     * @param rule   规则
     * @return true 匹配， false 不匹配
     */
    private static boolean validateString(String params, String rule) {
        //正则表达式的模式
        Pattern p = Pattern.compile(rule);
        //正则表达式的匹配器
        Matcher m = p.matcher(params);
        //进行正则匹配
        return m.matches();
    }
}
