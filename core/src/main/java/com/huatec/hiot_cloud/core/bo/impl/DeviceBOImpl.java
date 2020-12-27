package com.huatec.hiot_cloud.core.bo.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.huatec.hiot_cloud.core.autogenerator.entity.Device;
import com.huatec.hiot_cloud.core.autogenerator.service.IDeviceService;
import com.huatec.hiot_cloud.core.bo.IDeviceBO;
import com.huatec.hiot_cloud.core.config.BooleanTypeEnum;
import com.huatec.hiot_cloud.core.config.Constants;
import com.huatec.hiot_cloud.core.utils.CustomException;
import com.huatec.hiot_cloud.core.utils.FileUtil;
import com.huatec.hiot_cloud.core.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 设备管理模块业务层实现类
 *
 * @author WUWENBO
 * @since 2020/11/18 9:03
 */
@Service
@Transactional
public class DeviceBOImpl implements IDeviceBO {

    @Autowired
    private IDeviceService deviceDAO;

    @Override
    public String save(String userId, String title, String devType, String mac, String description, MultipartFile file) {
        // 校验参数
        if (StringUtils.isEmpty(userId)
                || StringUtils.isEmpty(title)
                || StringUtils.isEmpty(devType)) {
            throw new CustomException("参数不正确");
        }

        // 设备名称重复校验
        if (deviceDAO.titleExist(title)) {
            throw new CustomException("该设备名称已存在");
        }

        // 创建设备实体类，初始化字段值
        Device device = new Device();
        device.setId(UUIDUtil.getUUID());
        device.setStatus(BooleanTypeEnum.FALSE.getNumber());
        device.setCreated(new Date());
        device.setUserId(userId);
        device.setDevType(devType);
        device.setTitle(title);
        if (StringUtils.isNotEmpty(mac)) {
            device.setMac(mac);
        }
        if (StringUtils.isNotEmpty(description)) {
            device.setDescription(description);
        }
        if (file != null) {
            String newImagePath = FileUtil.uploadImg(file, Constants.UPLOAD_PATH_DEVICE);
            device.setDeviceimg(newImagePath);
        }

        // 保存到数据库
        if (!deviceDAO.insert(device)) {
            throw new CustomException("设备创建失败");
        }
        return device.getId();
    }

    @Override
    public void update(String id, String userId, String title, String devType, String mac, String description, MultipartFile file) {
        // 参数校验
        if (StringUtils.isEmpty(userId)
                || StringUtils.isEmpty(id)
                || StringUtils.isEmpty(title)
                || StringUtils.isEmpty(devType)) {
            throw new CustomException("参数不正确");
        }

        // 设备id校验
        Device device = deviceDAO.selectById(id);
        if (device == null) {
            throw new CustomException("该设备不存在");
        }

        // 设备名称重复性校验
        if (deviceDAO.titleExist(title)) {
            throw new CustomException("该设备名称已存在");
        }

        // 实体类赋值
        device.setDevType(devType);
        device.setTitle(title);
        if (StringUtils.isNotEmpty(mac)) {
            device.setMac(mac);
        }
        if (StringUtils.isNotEmpty(description)) {
            device.setDescription(description);
        }
        if (file != null) {

            String oldImagePath = device.getDeviceimg();

            // 删除旧头像
            File oldFile = new File(Constants.UPLOAD_PATH_PREFIX + oldImagePath);
            oldFile.delete();

            // 保存新头像
            String newImagePath = FileUtil.uploadImg(file, Constants.UPLOAD_PATH_DEVICE);
            device.setDeviceimg(newImagePath);
        }

        // 保存到数据库
        if (!deviceDAO.updateById(device)) {
            throw new CustomException("设备修改失败");
        }
    }

    @Override
    public Device get(String id) {
        // 参数非空校验
        if (StringUtils.isEmpty(id)) {
            throw new CustomException("参数不正确");
        }

        // 设备是否存在校验
        Device device = deviceDAO.selectById(id);
        if (device == null) {
            throw new CustomException("该设备不存在");
        }

        // 返回数据库查询结果
        return device;
    }

    @Override
    public void delete(String id) {
        // 参数非空校验
        if (StringUtils.isEmpty(id)) {
            throw new CustomException("参数不正确");
        }

        // 设备是否存在校验
        Device device = deviceDAO.selectById(id);
        if (device == null) {
            throw new CustomException("该设备不存在");
        }

        // 从数据库中删除
        if (!deviceDAO.deleteById(id)) {
            throw new CustomException("删除设备失败");
        }
    }

    @Override
    public Page<Device> listByPage(String userId, int pageIndex, int number) {

        Page<Device> page = new Page<>();
        page.setCurrent(pageIndex)
                .setSize(number)
                .setOrderByField("created")
                .setAsc(false);
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("user_id", userId);
        return deviceDAO.selectPage(page, wrapper);
    }
}
