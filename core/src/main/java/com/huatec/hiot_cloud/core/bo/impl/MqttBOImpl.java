package com.huatec.hiot_cloud.core.bo.impl;

import com.huatec.hiot_cloud.core.autogenerator.service.IUpdatastreamService;
import com.huatec.hiot_cloud.core.bo.IMqttBO;
import com.huatec.hiot_cloud.mqtt.service.ICoreServiceOuter;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Created by liwenqiang  on 2017/10/23 0023 at 上午 10:36  for hiot
 * @ Description:
 */

@Service
public class MqttBOImpl implements IMqttBO {
    @Autowired
    private ICoreServiceOuter coreServiceOuter;

    @Autowired
    private IUpdatastreamService updatastreamDAO;

    @Override
    public boolean addSub(String[] topics, int qos) throws MqttException {
        if (topics != null && topics.length > 0) {
            return coreServiceOuter.addSub(topics, qos);
        }
        return true;
    }

    @Override
    public boolean removeSub(String[] topics) throws MqttException {
        if (topics != null && topics.length > 0) {
            return coreServiceOuter.removeSub(topics);
        }
        return true;
    }

    @Override
    public boolean addPub(String topic, int qos, String msg) throws MqttException {
        return coreServiceOuter.addPub(topic, qos, msg);
    }

    @Override
    public void initSubMqtt() throws MqttException {
        /** 1、查询出所有设备ID并加入到MQTT主题订阅中，用户判断设备是否连接及是否掉线
         ** 2、查询出所有向上通道并加入到MQTT主题订阅中,用于接收设备的数据
         **/

        List<String> updatastreamIds = updatastreamDAO.findAllIds();
        if (updatastreamIds != null && updatastreamIds.size() > 0) {
            String[] topics = new String[updatastreamIds.size()];
            updatastreamIds.toArray(topics);
            coreServiceOuter.addSub(topics, 0);
        }
    }

}
