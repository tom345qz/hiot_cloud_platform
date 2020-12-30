package com.huatec.hiot_cloud.mqtt.service.impl;

import com.huatec.hiot_cloud.mongodb.entity.Alert;
import com.huatec.hiot_cloud.mongodb.entity.Measurement;
import com.huatec.hiot_cloud.mongodb.entity.Status;
import com.huatec.hiot_cloud.mongodb.entity.Waypoint;
import com.huatec.hiot_cloud.mongodb.entity.base.UpdatastreamData;
import com.huatec.hiot_cloud.mongodb.manager.IMongoApiManager;
import com.huatec.hiot_cloud.mqtt.service.IMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ Created by liwenqiang  on 2017/11/28 0028 at 上午 9:31  for mqtt
 * @ Description:
 */
@Service("mongoService2")
@DependsOn("mongoApiManager")
public class MongoService implements IMongoService {
    @Autowired
    private IMongoApiManager mongoApiManager;

    @Override
    public boolean saveToMongo(String topic, Integer dataType, String msg) {
        /* dataType表示的通道数据类型：
         * 1:数值测量值型；2：开关状态型；3：地理位置定位型；4：文本预警消息型。
         **/
        if (0 < dataType && dataType < 5) {
            UpdatastreamData udsData = null;
            switch (dataType) {
                case 1:
                    Measurement measurement = new Measurement();
                    measurement.setUpdatastreamId(topic);
                    measurement.setTiming(new Date());
                    measurement.setValue(msg);
                    udsData = measurement;
                    break;
                case 2:
                    Integer stat = null;
                    try {
                        stat = Integer.valueOf(msg);
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                    if (stat != 0 && stat != 1) {
                        return false;
                    }
                    Status status = new Status();
                    status.setUpdatastreamId(topic);
                    status.setTiming(new Date());
                    status.setStatus(stat);
                    udsData = status;
                    break;
                case 3:
                    String[] str = msg.split(",");
                    if (str.length < 2) {
                        return false;
                    }
                    Waypoint waypoint = new Waypoint();
                    waypoint.setUpdatastreamId(topic);
                    waypoint.setTiming(new Date());
                    waypoint.setLongitude(str[0]);
                    waypoint.setLatitude(str[1]);
                    if (str.length == 3) {
                        waypoint.setElevation(str[2]);
                    }
                    udsData = waypoint;
                    break;
                case 4:
                    Alert alert = new Alert();
                    alert.setUpdatastreamId(topic);
                    alert.setTiming(new Date());
                    alert.setNews(msg);
                    udsData = alert;
                    break;
                default:
                    return false;
            }
            mongoApiManager.upload(dataType, udsData);
            return true;
        } else {
            return false;
        }
    }
}
