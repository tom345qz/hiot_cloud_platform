package com.huatec.hiot_cloud.core.autogenerator.service;

import com.huatec.hiot_cloud.core.autogenerator.entity.Updatastream;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author code generator
 * @since 2020-12-15
 */
public interface IUpdatastreamService extends IService<Updatastream> {

    /**
     * 获取所有上行通道id
     *
     * @return
     */
    List<String> findAllIds();
}
