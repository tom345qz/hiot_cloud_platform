package com.huatec.hiot_cloud.mqtt.service.impl;

import com.huatec.hiot_cloud.mqtt.client.PubClient;
import com.huatec.hiot_cloud.mqtt.client.SubClient;
import com.huatec.hiot_cloud.mqtt.service.ICoreServiceOuter;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ Created by liwenqiang  on 2017/11/27 0027 at 上午 10:21  for mqtt
 * @ Description:
 */
@Service
public class CoreServiceOuter implements ICoreServiceOuter {
    @Autowired
    private SubClient subClient;
    @Autowired
    private PubClient pubClient;

    @Override
    public boolean addSub(String[] topics, int qos) {
        try {
            if (qos != 0 && qos != 1 && qos != 2) {
                qos = 0;
            }
            subClient.addSub(topics, qos);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addPub(String topic, int qos, String msg) {
        try {
            if (qos != 0 && qos != 1 && qos != 2) {
                qos = 0;
            }
            pubClient.addPub(topic, qos, msg);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeSub(String[] topics) {
        try {
            subClient.removeSub(topics);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }
}
