package com.huatec.hiot_cloud.core.bo.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.huatec.hiot_cloud.core.autogenerator.entity.Datastreamlink;
import com.huatec.hiot_cloud.core.autogenerator.entity.Downdatastream;
import com.huatec.hiot_cloud.core.autogenerator.entity.Updatastream;
import com.huatec.hiot_cloud.core.autogenerator.service.IDatastreamlinkService;
import com.huatec.hiot_cloud.core.autogenerator.service.IDeviceService;
import com.huatec.hiot_cloud.core.autogenerator.service.IDowndatastreamService;
import com.huatec.hiot_cloud.core.autogenerator.service.IUpdatastreamService;
import com.huatec.hiot_cloud.core.bo.IDataStreamBO;
import com.huatec.hiot_cloud.core.config.Constants;
import com.huatec.hiot_cloud.core.dto.DataStreamDTO;
import com.huatec.hiot_cloud.core.utils.CustomException;
import com.huatec.hiot_cloud.core.utils.ModelMapperUtils;
import com.huatec.hiot_cloud.core.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 通道模块业务层实现类
 *
 * @author WUWENBO
 * @since 2020/12/16 9:26
 */
@Service
public class DataStreamBO implements IDataStreamBO {
    @Autowired
    private IDowndatastreamService downdatastreamDAO;

    @Autowired
    private IUpdatastreamService updatastreamDAO;

    @Autowired
    private IDatastreamlinkService datastreamlinkDAO;

    @Autowired
    private IDeviceService deviceDAO;

    @Override
    public void save(String deviceId, int dataType, String title, int direction) {
        // 校验业务参数
        if (deviceDAO.selectById(deviceId) == null) {
            throw new CustomException("设备不存在");
        }

        // 方向向上
        if (direction == Constants.DATA_STREAM_DIRECTION_UP) {
            createUpdataStream(deviceId, dataType, title);
        }

        // 方向向下
        if (direction == Constants.DATA_STREAM_DIRECTION_DOWN) {
            createDownDataStream(deviceId, dataType, title);
        }

        // 方向是所有的
        if (direction == Constants.DATA_STREAM_DIRECTION_ALL) {
            // 先保存向上通道
            Updatastream updataStream = createUpdataStream(deviceId, dataType, title);

            // 再保存向下通道
            Downdatastream downDataStream = createDownDataStream(deviceId, dataType, title);

            // 然后保存关联
            Datastreamlink datastreamlink = new Datastreamlink();
            datastreamlink.setId(UUIDUtil.getUUID());
            datastreamlink.setTitle(title);
            datastreamlink.setUpdatastreamId(updataStream.getId());
            datastreamlink.setDowndatastreamId(downDataStream.getId());
            if (!datastreamlinkDAO.insert(datastreamlink)) {
                throw new CustomException("创建通道关联失败");
            }
        }
    }

    @Override
    public Downdatastream getDownDataStreamByUp(String updatastreamId) {

        // 参数校验
        if (updatastreamDAO.selectById(updatastreamId) == null) {
            throw new CustomException("上行通道不存在");
        }

        // 获取上下行通道关联记录
        Wrapper wrapper = new EntityWrapper<>();
        wrapper.eq("updatastream_id", updatastreamId);
        Datastreamlink datastreamlink = datastreamlinkDAO.selectOne(wrapper);
        if (datastreamlink == null || StringUtils.isEmpty(datastreamlink.getDowndatastreamId())) {
            throw new CustomException("上行通道id未关联或下行通道不存在");
        }

        Downdatastream downdatastream = downdatastreamDAO.selectById(datastreamlink.getDowndatastreamId());
        if (downdatastream == null) {
            throw new CustomException("下行通道不存在");
        }
        return downdatastream;
    }

    @Override
    public List<DataStreamDTO> listDataStreamByDevice(String deviceId, int direction, int dataType) {

        // 业务参数校验
        if (deviceDAO.selectById(deviceId) == null) {
            throw new CustomException("设备不存在");
        }

        // 如果方向是上行通道
        if (direction == Constants.DATA_STREAM_DIRECTION_UP) {
            List<Updatastream> updatastreamList = listUpdatastreams(deviceId, dataType);
            return upListToDTOList(updatastreamList);
        }

        // 如果方向是下行通道
        if (direction == Constants.DATA_STREAM_DIRECTION_DOWN) {
            List<Downdatastream> downdatastreamList = listDownDataStreams(deviceId, dataType);
            return DownListToDTOList(downdatastreamList);
        }
        // 如果方向是所有通道
        if (direction == Constants.DATA_STREAM_DIRECTION_ALL) {
            List<Updatastream> updatastreamList = listUpdatastreams(deviceId, dataType);
            List<Downdatastream> downdatastreamList = listDownDataStreams(deviceId, dataType);
            List<DataStreamDTO> dtoList = upListToDTOList(updatastreamList);
            List<DataStreamDTO> dtoList2 = DownListToDTOList(downdatastreamList);
            if (dtoList2 != null) {
                dtoList.addAll(dtoList2);
            }
            return dtoList;
        }
        throw new CustomException("通道方向不正确");
    }

    /**
     * 下行通道列表转换成dto列表
     *
     * @param downdatastreamList
     * @return
     */
    private List<DataStreamDTO> DownListToDTOList(List<Downdatastream> downdatastreamList) {
        List<DataStreamDTO> dtoList = new ArrayList<>();
        for (Downdatastream downdatastream : downdatastreamList) {
            dtoList.add(ModelMapperUtils.map(downdatastream, DataStreamDTO.class));
        }
        return dtoList;
    }

    /**
     * 上行通道列表转换成dto列表
     *
     * @param updatastreamList
     * @return
     */
    private List<DataStreamDTO> upListToDTOList(List<Updatastream> updatastreamList) {
        List<DataStreamDTO> dtoList = new ArrayList<>();
        for (Updatastream updatastream : updatastreamList) {
            dtoList.add(ModelMapperUtils.map(updatastream, DataStreamDTO.class));
        }
        return dtoList;
    }

    private List<Downdatastream> listDownDataStreams(String deviceId, int dataType) {
        Wrapper<Downdatastream> wrapper = new EntityWrapper<>();
        wrapper.eq("device_id", deviceId);
        if (dataType != Constants.DATA_STREAM_TYPE_ALL) {
            wrapper.eq("data_type", dataType);
        }
        return downdatastreamDAO.selectList(wrapper);
    }

    private List<Updatastream> listUpdatastreams(String deviceId, int dataType) {
        Wrapper<Updatastream> wrapper = new EntityWrapper<>();
        wrapper.eq("device_id", deviceId);
        if (dataType != Constants.DATA_STREAM_TYPE_ALL) {
            wrapper.eq("data_type", dataType);
        }
        return updatastreamDAO.selectList(wrapper);
    }

    /**
     * 创建向下通道
     *
     * @param deviceId
     * @param dataType
     * @param title
     * @return
     */
    private Downdatastream createDownDataStream(String deviceId, int dataType, String title) {
        Downdatastream downdatastream = new Downdatastream();
        downdatastream.setDeviceId(deviceId);
        downdatastream.setDataType(dataType);
        downdatastream.setId(UUIDUtil.getUUID());
        if (StringUtils.isNotEmpty(title)) {
            downdatastream.setTitle(title);
        }
        if (!downdatastreamDAO.insert(downdatastream)) {
            throw new CustomException("保存向下通道失败");
        }
        return downdatastream;
    }

    /**
     * 创建向上通道
     *
     * @param dataType
     * @param title
     * @return
     */
    private Updatastream createUpdataStream(String deviceId, int dataType, String title) {
        Updatastream updatastream = new Updatastream();
        updatastream.setId(UUIDUtil.getUUID());
        updatastream.setDeviceId(deviceId);
        if (StringUtils.isNotEmpty(title)) {
            updatastream.setTitle(title);
        }
        updatastream.setDataType(dataType);
        boolean operSucc = updatastreamDAO.insert(updatastream);
        if (!operSucc) {
            throw new CustomException("保存向上通道失败");
        }
        return updatastream;
    }
}
