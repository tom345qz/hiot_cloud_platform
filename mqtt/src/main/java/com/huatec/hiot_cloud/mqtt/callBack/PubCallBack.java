package com.huatec.hiot_cloud.mqtt.callBack;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

/**
 * @ Created by liwenqiang  on 2017/11/23 0023 at 下午 3:46  for mqtt
 * @ Description:
 */
@Component
public class PubCallBack implements MqttCallbackExtended {
    //连接成功时调用
    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        System.out.println("连接成功p");
    }

    //在断开连接时调用
    @Override
    public void connectionLost(Throwable cause) {
        cause.printStackTrace();
        System.out.println("连接断开，可以做重连");
    }

    //接收已经预订的发布
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("收到消息主题:" + topic);
        System.out.println("收到消息内容:" + message);
    }

    //接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("发布完成");
    }
}
