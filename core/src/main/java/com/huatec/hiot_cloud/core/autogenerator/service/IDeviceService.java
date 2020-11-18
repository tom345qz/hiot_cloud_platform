package com.huatec.hiot_cloud.core.autogenerator.service;

import com.huatec.hiot_cloud.core.autogenerator.entity.Device;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author code generator
 * @since 2020-11-18
 */
public interface IDeviceService extends IService<Device> {

    /**
     * 校验某设备名称是否存在
     *
     * @param title 设备名称
     * @return true 存在，false 不存在
     */
    boolean titleExist(String title);
}
