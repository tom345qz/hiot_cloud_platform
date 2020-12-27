package com.huatec.hiot_cloud.core.autogenerator.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author code generator
 * @since 2020-12-16
 */
public class Datastreamlink extends Model<Datastreamlink> {

    private static final long serialVersionUID = 1L;

    /**
     * 通道连接id
     */
    private String id;
    /**
     * 通道名称
     */
    private String title;
    /**
     * 上行通道id
     */
    @TableField("updatastream_id")
    private String updatastreamId;
    /**
     * 下行通道id
     */
    @TableField("downdatastream_id")
    private String downdatastreamId;


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

    public String getUpdatastreamId() {
        return updatastreamId;
    }

    public void setUpdatastreamId(String updatastreamId) {
        this.updatastreamId = updatastreamId;
    }

    public String getDowndatastreamId() {
        return downdatastreamId;
    }

    public void setDowndatastreamId(String downdatastreamId) {
        this.downdatastreamId = downdatastreamId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Datastreamlink{" +
                "id=" + id +
                ", title=" + title +
                ", updatastreamId=" + updatastreamId +
                ", downdatastreamId=" + downdatastreamId +
                "}";
    }
}
