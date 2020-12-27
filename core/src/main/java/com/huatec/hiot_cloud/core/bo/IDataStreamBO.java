package com.huatec.hiot_cloud.core.bo;

import com.huatec.hiot_cloud.core.autogenerator.entity.Downdatastream;
import com.huatec.hiot_cloud.core.dto.DataStreamDTO;

import java.util.List;

/**
 * 通道模块业务层接口
 *
 * @author WUWENBO
 * @since 2020/12/16 9:23
 */
public interface IDataStreamBO {

    /**
     * 保存通道
     *
     * @param deviceId
     * @param dataType
     * @param title
     * @param direction
     */
    void save(String deviceId, int dataType, String title, int direction);

    /**
     * 根据上行通道id查询下行通道
     *
     * @param updatastreamId
     * @return
     */
    Downdatastream getDownDataStreamByUp(String updatastreamId);

    /**
     * 根据条件查询通道列表
     *
     * @param deviceId
     * @param direction
     * @param dataType
     * @return
     */
    List<DataStreamDTO> listDataStreamByDevice(String deviceId, int direction, int dataType);
}
