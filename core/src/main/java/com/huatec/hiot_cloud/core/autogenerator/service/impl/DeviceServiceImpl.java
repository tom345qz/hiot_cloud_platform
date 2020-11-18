package com.huatec.hiot_cloud.core.autogenerator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.huatec.hiot_cloud.core.autogenerator.entity.Device;
import com.huatec.hiot_cloud.core.autogenerator.mapper.DeviceMapper;
import com.huatec.hiot_cloud.core.autogenerator.service.IDeviceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author code generator
 * @since 2020-11-18
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

    @Override
    public boolean titleExist(String title) {
        Wrapper wrapper = new EntityWrapper<Device>();
        wrapper.eq("title", title);
        int count = selectCount(wrapper);
        return count == 0 ? false : true;
    }
}
