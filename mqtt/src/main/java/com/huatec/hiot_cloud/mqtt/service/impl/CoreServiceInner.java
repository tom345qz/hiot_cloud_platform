package com.huatec.hiot_cloud.mqtt.service.impl;

//import com.huatec.hiot_cloud.core.service.ext.IMqttService;

import com.huatec.hiot_cloud.mqtt.service.ICoreServiceInner;
import org.springframework.stereotype.Service;

/**
 * @ Created by liwenqiang  on 2017/11/27 0027 at 下午 3:42  for mqtt
 * @ Description:
 */
@Service
public class CoreServiceInner implements ICoreServiceInner {

    // TODO
    @Override
    public boolean initSub() {
        System.out.println("这里做重新订阅（结合实际业务）");
        return false;
    }

    // TODO
    @Override
    public boolean returnResponse(String topic, String msg) {
        System.out.println("这里上传设备的响应数据");
        return false;
    }

    // TODO
    @Override
    public boolean isValidTopic(String topic) {
        System.out.println("这里判断主题是否有效");
        return true;
    }

    // TODO
    @Override
    public boolean updateOnlineStatus(String topic, String msg) {
        System.out.println("这里上传设备的在线状态");
        return false;
    }

}
