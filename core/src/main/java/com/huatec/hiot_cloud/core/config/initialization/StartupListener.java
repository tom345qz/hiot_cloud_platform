package com.huatec.hiot_cloud.core.config.initialization;

import com.huatec.hiot_cloud.core.bo.IMqttBO;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @ Created by liwenqiang  on 2017/7/6 0006 at 上午 11:39  for hiot
 * @ Description: 启动监听器：Spring容器将所有的Bean都初始化完成之后执行的操作
 * ****** 1、订阅mqtt主题
 */
@Component
//ContextRefreshedEvent为初始化完毕事件，spring还有很多事件可以利用
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private IMqttBO mqttBO;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        /** 但是这个时候，会存在一个问题，在web 项目中（spring mvc），系统会存在两个容器，
         * 一个是root application context ,
         * 另一个就是我们自己的 projectName-servlet context（作为root application context的子容器）。
         * 这种情况下，就会造成onApplicationEvent方法被执行两次。
         * 为了避免上面提到的问题，我们可以只在root application context初始化完成后调用逻辑代码，
         * 其他的容器的初始化完成，则不做任何处理，修改后代码
         * */
        if (event.getApplicationContext().getParent() == null) {//root application context 没有parent，他就是老大.
            System.out.println("所有bean注入完毕");
            /** 1、订阅mqtt主题*/
            try {
                mqttBO.initSubMqtt();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
}
