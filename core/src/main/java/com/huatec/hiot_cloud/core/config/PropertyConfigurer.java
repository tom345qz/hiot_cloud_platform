package com.huatec.hiot_cloud.core.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ Created by liwenqiang  on 2017/9/6 0006 at 上午 11:13  for hiot
 * @ Description: properties配置文件读取类
 */
public class PropertyConfigurer extends PropertyPlaceholderConfigurer {
    //private Properties props;       // 存取properties配置文件key-value结果
    //为了定义静态方法，自己定义map
    private static Map<String, String> propertyMap;

    public static String getProperty(String name) {
        return propertyMap.get(name);
    }

    public static Object setProperty(String key, String value) {
        return propertyMap.put(key, value);
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        propertyMap = new HashMap<String, String>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            propertyMap.put(keyStr, value);
        }
    }
}
