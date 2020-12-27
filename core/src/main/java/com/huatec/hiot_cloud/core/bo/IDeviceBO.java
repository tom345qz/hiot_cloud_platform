package com.huatec.hiot_cloud.core.bo;

import com.baomidou.mybatisplus.plugins.Page;
import com.huatec.hiot_cloud.core.autogenerator.entity.Device;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 设备管理模块业务层节课
 *
 * @author WUWENBO
 * @since 2020/11/18 9:00
 */
public interface IDeviceBO {

    /**
     * 保存设备
     *
     * @param userId      用户id
     * @param title       设备名称
     * @param devType     设备类型
     * @param mac         物理地址
     * @param description 设备描述
     * @param file        设备图片
     */
    String save(String userId, String title, String devType, String mac, String description, MultipartFile file);

    /**
     * 修改设备
     *
     * @param id          设备id
     * @param userId      用户id
     * @param title       设备名称
     * @param devType     设备类型
     * @param mac         物理地址
     * @param description 设备描述
     * @param file        设备图片
     */
    void update(String id, String userId, String title, String devType, String mac, String description, MultipartFile file);

    /**
     * 获取设备信息
     *
     * @param id 设备id
     * @return 设备对象
     */
    Device get(String id);

    /**
     * 删除设备信息
     *
     * @param id 设备id
     */
    void delete(String id);

    /**
     * 分页查询设备列表
     *
     * @param userId
     * @param pageIndex
     * @param number
     * @return
     */
    Page<Device> listByPage(String userId, int pageIndex, int number);
}
