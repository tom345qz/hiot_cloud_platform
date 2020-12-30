package com.huatec.hiot_cloud.mqtt.service;

/**
 * @ Created by liwenqiang  on 2017/11/27 0027 at 上午 10:24  for mqtt
 * @ Description:
 */
public interface ICoreServiceOuter {
    /**
     * 增加主题订阅
     */
    boolean addSub(String[] topics, int qos);

    /**
     * 增加信息发布
     */
    boolean addPub(String topic, int qos, String msg);

    /**
     * 取消主题订阅
     */
    boolean removeSub(String[] topics);
}
