package com.huatec.hiot_cloud.core.config;

/**
 * 逻辑真假枚举
 *
 * @author wuwenbo
 * @since 2020-6-7
 */
public enum BooleanTypeEnum {


    /**
     * true
     */
    TRUE(1),

    /**
     * fase
     */
    FALSE(0);

    /**
     * 号码
     */
    private int number;


    BooleanTypeEnum(int number) {
        this.number = number;
    }

    public static BooleanTypeEnum getBooleanType(int number) {
        for (BooleanTypeEnum type : values()) {
            if (number == type.getNumber()) {
                return type;
            }
        }
        return null;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}