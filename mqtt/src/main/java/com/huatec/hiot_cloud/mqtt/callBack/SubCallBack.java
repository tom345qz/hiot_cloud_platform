package com.huatec.hiot_cloud.mqtt.callBack;

import com.huatec.hiot_cloud.mqtt.service.SubCallBackService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * @ Created by liwenqiang  on 2017/11/23 0023 at 下午 3:46  for mqtt
 * @ Description:
 */
@Component
@DependsOn("subCallBackService")
public class SubCallBack implements MqttCallbackExtended {
    @Autowired
    private SubCallBackService subCallBackService;

    //连接成功时调用
    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        System.out.println("连接成功s");
        // 重连需要重新订阅主题
        if (reconnect) {
            System.out.println("重连成功s");
            subCallBackService.initSub();
        }
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
        String msg = message.toString();
        if (msg.trim().length() > 0) {
            subCallBackService.msgHandle(topic, msg);
        }
    }

    //接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("发宋完成");
    }
}
