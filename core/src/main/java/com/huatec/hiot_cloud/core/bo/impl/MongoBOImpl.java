package com.huatec.hiot_cloud.core.bo.impl;

import com.huatec.hiot_cloud.core.autogenerator.entity.Updatastream;
import com.huatec.hiot_cloud.core.autogenerator.service.IUpdatastreamService;
import com.huatec.hiot_cloud.core.bo.IMongoBO;
import com.huatec.hiot_cloud.core.config.Constants;
import com.huatec.hiot_cloud.core.utils.CustomException;
import com.huatec.hiot_cloud.mongodb.entity.Alert;
import com.huatec.hiot_cloud.mongodb.entity.Measurement;
import com.huatec.hiot_cloud.mongodb.entity.Status;
import com.huatec.hiot_cloud.mongodb.entity.Waypoint;
import com.huatec.hiot_cloud.mongodb.entity.base.UpdatastreamData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author WUWENBO
 * @since 2020/12/21 22:37
 */
@Service
public class MongoBOImpl implements IMongoBO {
    @Autowired
    IUpdatastreamService updatastreamDAO;

    @Override
    public Integer findDataTypeByUdsId(String updatastreamId) {
        Updatastream updatastream = updatastreamDAO.selectById(updatastreamId);
        if (updatastream == null || updatastream.getDataType() == null) {
            throw new CustomException("无此上行通道");
        }
        return updatastream.getDataType();
    }

    @Override
    public UpdatastreamData getUdsData(String updatastreamId, Integer dataType, String deviceDataStr) {
        if (deviceDataStr == null || deviceDataStr.trim().length() == 0) {
            return null;
        }
        /* data_type
         * 1：数值2：开关 3 GPS 4：文本
         */
        switch (dataType) {
            case Constants.DATA_STREAM_TYPE_VALUE:
                Measurement measurement = new Measurement();
                measurement.setUpdatastreamId(updatastreamId);
                measurement.setTiming(new Date());
                measurement.setValue(deviceDataStr);
                return measurement;

            case Constants.DATA_STREAM_TYPE_SWITCH:
                Integer statusInt = null;
                try {
                    statusInt = Integer.valueOf(deviceDataStr);
                } catch (NumberFormatException nfe) {
                    throw new NumberFormatException("开关型通道数据错误");
                }
                Status status = new Status();
                status.setUpdatastreamId(updatastreamId);
                status.setTiming(new Date());
                status.setStatus(statusInt);
                return status;

            case Constants.DATA_STREAM_TYPE_GPS:
                /* 经度,纬度,海拔是由字符串接收并通过英文逗号分割的；
                 * 经度纬度必须有，海拔可选；
                 * 必须按照顺序拼接。
                 */
                String[] waypointArray = deviceDataStr.trim().split(",");
                String longitude = waypointArray[0];
                String latitude = waypointArray[1];
                String elevation = null;
                if (waypointArray.length == 3) {
                    elevation = waypointArray[2];
                }
                if (longitude == null || latitude == null) {
                    throw new NumberFormatException("地理位置型通道数据错误");
                }
                Waypoint waypoint = new Waypoint();
                waypoint.setUpdatastreamId(updatastreamId);
                waypoint.setTiming(new Date());
                waypoint.setLongitude(longitude);
                waypoint.setLatitude(latitude);
                waypoint.setElevation(elevation);
                return waypoint;

            case Constants.DATA_STREAM_TYPE_TXT:
                Alert alert = new Alert();
                alert.setUpdatastreamId(updatastreamId);
                alert.setTiming(new Date());
                alert.setNews(deviceDataStr);
                return alert;

            default:
                throw new IllegalArgumentException("通道数据类型码错误");
        }
    }
}
