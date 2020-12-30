package com.huatec.hiot_cloud.mqtt.service;

/**
 * @ Created by liwenqiang  on 2017/11/27 0027 at 上午 10:24  for mqtt
 * @ Description:
 */
public interface ICoreServiceInner {
    /**
     * 初始化（重连）订阅
     */
    boolean initSub();

    /**
     * 判断主题是否有效
     */
    boolean isValidTopic(String topic);

    /**
     * 更新设备在线状态
     */
    boolean updateOnlineStatus(String topic, String msg);

    /**
     * 上传设备的控制响应
     */
    boolean returnResponse(String topic, String msg);
}
