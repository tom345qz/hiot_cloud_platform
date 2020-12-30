package com.huatec.hiot_cloud.mqtt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ Created by liwenqiang  on 2017/11/24 0024 at 上午 10:35  for mqtt
 * @ Description:
 */
@Service
public class SubCallBackService {
    @Autowired
    private ICoreServiceInner coreServiceInner;

    /**
     * 掉线重连重新订阅
     **/
    public void initSub() {
        coreServiceInner.initSub();
    }

    /**
     * 设备消息处理
     **/
    public void msgHandle(String topic, String msg) {
        System.out.println(topic);
        System.out.println(msg);
        // todo 接收到设备的上线下线信息的处理
    }
}
