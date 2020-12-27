package com.huatec.hiot_cloud.core.dto;

import java.io.Serializable;

/**
 * 返回前端的通道信息DTO
 *
 * @author WUWENBO
 * @since 2020/12/16 10:39
 */
public class DataStreamDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 下行通道id
     */
    private String id;
    /**
     * 下行通道名称
     */
    private String title;
    /**
     * 下行通道类型
     */
    private Integer dataType;
    /**
     * 设备id
     */
    private String deviceId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
