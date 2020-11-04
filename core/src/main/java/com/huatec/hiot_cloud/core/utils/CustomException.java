package com.huatec.hiot_cloud.core.utils;


/**
 * 自定义服务异常类
 */
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 信息
     */
    private String msg;

    /**
     * 编码
     */
    private int code;

    public CustomException() {
    }

    public CustomException(int code) {
        this.code = code;
    }

    /**
     * 异常信息
     *
     * @param message
     */
    public CustomException(String message) {
        this.msg = message;
    }

    /**
     * @param message 异常信息
     * @param code    异常编码
     */
    public CustomException(String message, int code) {
        this.code = code;
        this.msg = message;
    }

    /**
     * 获取信息
     *
     * @return
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置信息
     *
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取编码
     *
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置编码
     *
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }

}
