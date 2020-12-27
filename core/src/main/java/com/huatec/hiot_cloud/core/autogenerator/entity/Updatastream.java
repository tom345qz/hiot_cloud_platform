package com.huatec.hiot_cloud.core.autogenerator.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author code generator
 * @since 2020-12-15
 */
public class Updatastream extends Model<Updatastream> {

    private static final long serialVersionUID = 1L;

    /**
     * 上行通道id
     */
    private String id;
    /**
     * 上行通道名称
     */
    private String title;
    /**
     * 上行通道类型
     */
    @TableField("data_type")
    private Integer dataType;
    /**
     * 设备id
     */
    @TableField("device_id")
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Updatastream{" +
                "id=" + id +
                ", title=" + title +
                ", dataType=" + dataType +
                ", deviceId=" + deviceId +
                "}";
    }
}
