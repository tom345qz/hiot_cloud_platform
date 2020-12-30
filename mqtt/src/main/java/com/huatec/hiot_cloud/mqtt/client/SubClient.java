package com.huatec.hiot_cloud.mqtt.client;

import com.huatec.hiot_cloud.mqtt.callBack.SubCallBack;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * @ Created by liwenqiang
 * @ Description:  订阅客户端
 */
@Component
@DependsOn("subCallBack")
public class SubClient {
    private static final String HOST = "tcp://127.0.0.1:61613"; //服务器IP:端口
    private static final String CLIENTID = "subClient"; //客户端唯一标识
    private static final String USERNAME = "admin"; //用户名
    private static final String PASSWORD = "password"; //密码
    private static MqttClient mqttClient; //客户端实例

    @Autowired
    private SubCallBack subCallBack;

    public static void main(String[] args) throws MqttException {
        SubClient client = new SubClient();
        client.connect();
        //主题
        String topic = "topic";
        //订阅
        client.mqttClient.subscribe(topic, 0);
    }

    // 在构造器之后再调用，为的是让bean属性先注入，避免后面回调函数出现bean空的情况
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
        mqttClient.setCallback(subCallBack);
        mqttClient.connect(options);
    }

    //增加订阅
    public void addSub(String[] topics, int qos) throws MqttException {
        //订阅
        int[] qoss = new int[topics.length];
        Arrays.fill(qoss, qos);
        mqttClient.subscribe(topics, qoss);
    }

    //增加订阅
    public void removeSub(String[] topics) throws MqttException {
        //订阅
        mqttClient.unsubscribe(topics);
    }

    //测试订阅
    public void connAndSub() throws MqttException {
        //connect();
        //主题
        String topic = "topic";
        //订阅
        mqttClient.subscribe(topic, 0);
    }
}
