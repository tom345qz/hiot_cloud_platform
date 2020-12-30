package com.huatec.hiot_cloud.mqtt.client;

import com.huatec.hiot_cloud.mqtt.callBack.PubCallBack;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ Created by liwenqiang
 * @ Description: 发布客户端
 */
@Component
public class PubClient {
    private static final String HOST = "tcp://127.0.0.1:61613"; //服务器IP:端口
    private static final String CLIENTID = "pubClient"; //客户端唯一标识
    private static final String USERNAME = "admin"; //用户名
    private static final String PASSWORD = "password"; //密码
    private static MqttClient mqttClient; //客户端实例

    @Autowired
    private PubCallBack pubCallBack;

    public static void main(String[] args) throws MqttException {
        PubClient client = new PubClient();
        client.connect();
        //主题
        String topic = "topic";
        //发布
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(0);
        mqttMessage.setRetained(false);
        //发布的消息内容
        String msg = "测试连接";
        mqttMessage.setPayload(msg.getBytes());
        MqttTopic mqttTopic = client.mqttClient.getTopic(topic);
        client.publish(mqttTopic, mqttMessage);
    }

    @PostConstruct
    public void connect() throws MqttException {
        // 新建客户端实例
        // MemoryPersistence设置客户端实例的保存形式，默认为以内存保存，此处以内存保存
        mqttClient = new MqttClient(HOST, CLIENTID, new MemoryPersistence());
        // 设置连接时的参数
        MqttConnectOptions options = new MqttConnectOptions();
        /* 是否清空session,如果设置为false表示服务器会保留客户端的连接记录，
        设置为true表示每次连接到服务器都以新的身份连接*/
        options.setCleanSession(true);
        // 用户名
        options.setUserName(USERNAME);
        // 密码
        options.setPassword(PASSWORD.toCharArray());
        // 连接超时时间 单位为秒
        options.setConnectionTimeout(100);
        // 心跳间隔时间 单位为秒
        options.setKeepAliveInterval(180);
        // 掉线自动重连
        options.setAutomaticReconnect(true);
        /* 遗嘱消息：当连接异常断开时发送的预告，服务器检测到客户端异常断开后，
         服务器会把此消息推送给订阅了此主题的客户机*/
        options.setWill("close", "offline".getBytes(), 0, true);
        // 设置回调函数
        mqttClient.setCallback(pubCallBack);
        mqttClient.connect(options);
    }

    //发布
    public void addPub(String topic, int qos, String msg) throws MqttException {
        //发布
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(false);
        //发布的消息内容
        mqttMessage.setPayload(msg.getBytes());
        MqttTopic mqttTopic = mqttClient.getTopic(topic);
        publish(mqttTopic, mqttMessage);
    }

    public void publish(MqttTopic topic, MqttMessage message) throws MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
    }

    //测试发布
    public void connAndPub() throws MqttException {
        //connect();
        //主题
        String topic = "topic";
        //发布
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(0);
        mqttMessage.setRetained(false);
        //发布的消息内容
        String msg = "测试发布订阅";
        mqttMessage.setPayload(msg.getBytes());
        MqttTopic mqttTopic = mqttClient.getTopic(topic);
        publish(mqttTopic, mqttMessage);
    }


}
