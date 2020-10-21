package com.huatec.hiot_cloud.core.config;

/**
 * @ Created by liwenqiang on 2017/5/10 .
 * @ Description:自定义请求状态码
 */
public enum ResultStatus {
    /**
     * 0：失败
     * 1：成功
     */
    /**
     * 成功信息
     */
    SUCCESS(1, "成功"),
    SAVE_SUCCESS(1, "增加成功"),
    DELETE_SUCCESS(1, "删除成功"),
    UPDATE_SUCCESS(1, "更改成功"),
    SELECT_SUCCESS(1, "查询成功"),
    SINGUP_SUCCESS(1, "注册成功"),
    LOGIN_SUCCESS(1, "登录成功"),
    LOGOUT_SUCCESS(1, "退出成功"),
    EMAIL_SEND_SUCCESS(1, "操作成功,已经发送密码到您邮箱,请前往邮箱查看"),
    SELECT_EMPTY(1, "查询结果为空"),
    DEVICE_BONDING_SUCCESS(1, "设备绑定成功"),
    DEVICE_IS_BONDING(1, "设备已绑定"),
    DEVICE_IS_UNBONDED(1, "设备已解绑"),
    DEVICE_NOTBONDING_SUCCESS(1, "设备解绑成功"),
    OPERATION_SUCCESS(1, "操作成功"),

    ERROR(0, "失败"),
    GENERAL_ERROR(0, "服务器开小差了，请稍后再试"),
    SINGUP_ERROR(0, "注册失败"),
    LOGIN_ERROR(0, "账号或密码有误，登录失败"),
    USER_EXIST(0, "用户名已存在"),
    EMAIL_EXIST(0, "邮箱已被注册"),
    EMAIL_FORMAT_ERROR(0, "邮箱格式错误"),
    DATA_NOT_FOUND(0, "数据不能为空"),
    USER_NOT_FOUND(0, "用户不存在"),
    USER_PASSWORD_ERROR(0, "密码错误"),
    LOGIN_CODE_WEB_ERROR(0, "登录失败,您是开发者用户，无法在移动APP端登录"),
    LOGIN_CODE_APP_ERROR(0, "登录失败,您是普通用户，无法在网页WEB端登录"),
    USERNAME_OR_EMAIL_ERROR(0, "用户名或邮箱输入错误"),

    SAVE_ERROR(0, "增加失败"),
    DELETE_ERROR(0, "删除失败"),
    UPDATE_ERROR(0, "更改失败"),
    SELECT_ERROR(0, "查询失败"),
    OPERATION_NOT_POWER(0, "没有操作权限"),
    PASSWORD_FORMAT_ERROR(0, "密码格式错误"),
    PASSWORD_ERROR(0, "原密码不正确"),
    CONFIRMPASSWORD_ERROR(0, "确认密码错误"),
    EMAIL_SEND_ERROR(0, "邮件发送失败，请重试或联系管理员"),
    DEVICE_IS_EXIST(0, "您已有同名设备存在"),
    GET_ID_ERROR(0, "获取ID失败"),
    DEVICE_NOT_FOUND(0, "设备不存在"),
    INPUT_PARAM_ERROR(0, "输入参数有误"),
    TITLE_CANNOT_EMPTY(0, "名称不能为空"),
    DATASTREAM_NOT_FOUND(0, "通道不存在"),
    DEVICE_NOT_BOUND(0, "未绑定的设备"),
    DEVICE_NOT_BOUND_EVER(0, "从未绑定过该设备，不能重新绑定"),
    DEVICE_BONDED_BY_OTHERS(0, "设备已被其他用户绑定，您不可以再绑定了"),
    DEVICE_UNBONDED_DENY(0, "不能解绑此设备"),
    DATATYPE_ERROR(0, "通道数据类型有误"),
    OPERATION_ERROR(0, "操作失败"),

    /**
     * 验证失败，重新登录信息
     */
    USER_AUTH_ERROR(-100, "验证token失败，您的请求被残忍拒绝了！"),
    USER_NOT_LOGIN(-100, "没有您的登录信息,请重新登录！"),
    TOKEN_INVALID(-100, "token无效，请重新登录！"),

    SERVER_ERROR(-500, "抱歉，服务器或程序错误"),
    ;
    /**
     * 返回码
     */
    private int status;

    /**
     * 返回结果描述
     */
    private String msg;

    ResultStatus() {
    }

    ResultStatus(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
