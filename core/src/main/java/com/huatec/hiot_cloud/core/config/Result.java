package com.huatec.hiot_cloud.core.config;

import java.util.List;

/**
 * @ Created by liwenqiang on 2017/5/3 0003.
 * @ Description:
 */
public class Result {

    /**
     * 状态,成功：1，失败：0
     */
    private int status;
    /**
     * 消息
     */
    private String msg;
    /**
     * 数据
     */
    private Object data;

    public Result() {
    }

    public Result(int status, String msg) {
        this.status = status;
        this.msg = msg;
        this.data = null;
    }

    public Result(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Result(ResultStatus resultStatus) {
        this.status = resultStatus.getStatus();
        this.msg = resultStatus.getMsg();
        this.data = null;
    }

    public Result(ResultStatus resultStatus, Object data) {
        this.status = resultStatus.getStatus();
        this.msg = resultStatus.getMsg();
        this.data = data;
    }

    public Result(ResultStatus resultStatus, String str) {
        this.status = resultStatus.getStatus();
        this.msg = resultStatus.getMsg() + "：[" + str + "]";
    }

    //具体成功信息，带数据(2个参数)
    public static Result ok(ResultStatus ok, Object data) {
        //查询集合为空的统一提示处理
        if (data instanceof List) {
            List list = (List) data;

        }
        return new Result(ok, data);
    }

    //成功信息，不带数据。
    public static Result ok() {
        return new Result(ResultStatus.SUCCESS);
    }

    //具体成功信息，不带数据。
    public static Result ok(ResultStatus ok) {
        return new Result(ok);
    }

    //具体错误信息。
    public static Result error(ResultStatus error) {
        return new Result(error);
    }

    //具体错误信息(加附带信息)。
    public static Result error(ResultStatus error, String str) {
        return new Result(error, str);
    }

    /**
     * 格式化占位符
     */
    public static Result format(ResultStatus rs, String... value) {
        StringBuffer sbMsg = new StringBuffer(rs.getMsg());
        int index = 0;
        for (int i = 0, l = value.length; i < l; i++) {
            String old = "{" + i + "}";
            index = sbMsg.indexOf(old, index);
            sbMsg = sbMsg.replace(index, index + old.length(), value[i]);
        }
        return new Result(rs.getStatus(), sbMsg.toString());
    }

    /**
     * 首部添加信息
     */
    public static Result appendBegin(Result r, String addMsg) {
        r.setMsg(addMsg + "," + r.getMsg());
        return r;
    }

    /**
     * 尾部添加信息
     */
    public static Result appendEnd(Result r, String addMsg) {
        r.setMsg(r.getMsg() + "," + addMsg);
        return r;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
