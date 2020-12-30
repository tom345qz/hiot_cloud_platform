package com.huatec.hiot_cloud.mqtt.service;

/**
 * @ Created by liwenqiang  on 2017/11/27 0027 at 下午 5:40  for mqtt
 * @ Description:
 */
public interface IMongoService {
    /**
     * 保存设备数据到mongodb
     */
    public boolean saveToMongo(String topic, Integer dataType, String msg);
}
