package com.huatec.hiot_cloud.core.bo;

import com.huatec.hiot_cloud.mongodb.entity.base.UpdatastreamData;

/**
 * @author WUWENBO
 * @since 2020/12/21 22:35
 */
public interface IMongoBO {
    /**
     * 根据向上通道ID查询通道数据类型
     */
    Integer findDataTypeByUdsId(String updatastreamId);

    /**
     * 封装向上通道数据对象，用以调用mongo模块保存数据到MongoDB
     */
    UpdatastreamData getUdsData(String updatastreamId, Integer dataType, String deviceDataStr);

}
