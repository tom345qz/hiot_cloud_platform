package com.huatec.hiot_cloud.core.autogenerator.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author code generator
 * @since 2020-11-18
 */
public class Device extends Model<Device> {

    private static final long serialVersionUID = 1L;

    /**
     * 设备id
     */
    private String id;
    /**
     * 设备名称
     */
    private String title;
    /**
     * 设备类型
     */
    @TableField("dev_type")
    private String devType;
    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;
    /**
     * 物理地址
     */
    private String mac;
    /**
     * 设备状态，0：未启用，1：已启用
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date created;
    /**
     * 修改时间
     */
    private Date updated;
    /**
     * 设备图片地址
     */
    private String deviceimg;
    /**
     * 设备描述
     */
    private String description;


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

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getDeviceimg() {
        return deviceimg;
    }

    public void setDeviceimg(String deviceimg) {
        this.deviceimg = deviceimg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", title=" + title +
                ", devType=" + devType +
                ", userId=" + userId +
                ", mac=" + mac +
                ", status=" + status +
                ", created=" + created +
                ", updated=" + updated +
                ", deviceimg=" + deviceimg +
                ", description=" + description +
                "}";
    }
}
