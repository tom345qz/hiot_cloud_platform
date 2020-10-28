package com.huatec.hiot_cloud.core.authorization.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

/**
 * Created by liwenqiang on 2017/5/10 0010.
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 */
public class TokenModel implements Serializable {
    //用户Id
    @JsonPropertyOrder
    private String UUId;

    //token类型
    @JsonIgnore
    private String typeCode;

    //随机生成的token
    private String tokenValue;

    public TokenModel(String UUId, String tokenValue) {
        this.UUId = UUId;
        this.tokenValue = tokenValue;
    }

    public TokenModel(String UUId, String typeCode, String tokenValue) {
        this.UUId = UUId;
        this.typeCode = typeCode;
        this.tokenValue = tokenValue;
    }

    public String getUUId() {
        return UUId;
    }

    public void setUUId(String UUId) {
        this.UUId = UUId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    @Override
    public String toString() {
        return "TokenModel{" +
                "UUId='" + UUId + '\'' +
                ", typeCode='" + typeCode + '\'' +
                ", tokenValue='" + tokenValue + '\'' +
                '}';
    }
}
