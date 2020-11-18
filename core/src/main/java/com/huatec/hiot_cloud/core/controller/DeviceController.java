package com.huatec.hiot_cloud.core.controller;

import com.huatec.hiot_cloud.core.authorization.annotation.Authorization;
import com.huatec.hiot_cloud.core.authorization.annotation.CurrentUser;
import com.huatec.hiot_cloud.core.authorization.annotation.Permissions;
import com.huatec.hiot_cloud.core.authorization.model.Role;
import com.huatec.hiot_cloud.core.autogenerator.entity.Device;
import com.huatec.hiot_cloud.core.autogenerator.entity.User;
import com.huatec.hiot_cloud.core.bo.IDeviceBO;
import com.huatec.hiot_cloud.core.config.Result;
import com.huatec.hiot_cloud.core.config.ResultStatus;
import com.huatec.hiot_cloud.core.utils.CustomException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 设备管理模块控制器层实现类
 *
 * @author WUWENBO
 * @since 2020/11/18 8:25
 */
@Api(value = "device", description = "设备管理")
@RequestMapping("/device")
@Controller
public class DeviceController {

    @Autowired
    private IDeviceBO deviceBO;

    private Logger logger = LoggerFactory.getLogger(DeviceController.class);

    /**
     * 添加设备
     *
     * @param user        用户对象
     * @param title       设备名称
     * @param devType     设备类型
     * @param mac         物理地址
     * @param description 设备描述
     * @param file        设备图片
     * @return 成功后返回设备id，失败返回错误信息
     */
    @ApiOperation(value = "添加设备", notes = "添加设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", required = true, dataType = "string", paramType = "header"),
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @Authorization
    @Permissions(role = Role.DEVELOPER)
    public Result add(@ApiIgnore @CurrentUser User user,
                      @RequestParam String title,
                      @RequestParam String devType,
                      @RequestParam(required = false) String mac,
                      @RequestParam(required = false) String description,
                      @RequestPart(required = false) MultipartFile file) {

        try {
            // 校验参数非空
            if (user == null || StringUtils.isEmpty(user.getId())
                    || StringUtils.isEmpty(title)
                    || StringUtils.isEmpty(devType)) {
                return Result.error(ResultStatus.INPUT_PARAM_ERROR);
            }

            // 校验参数合法性,pass

            // 调用业务层保存
            String deviceId = deviceBO.save(user.getId(), title, devType, mac, description, file);

            // 返回用户消息
            return Result.ok(ResultStatus.SAVE_SUCCESS, deviceId);
        } catch (CustomException ce) {
            logger.error("添加设备失败：" + ce.getMsg());
            return Result.error(ResultStatus.SAVE_ERROR, ce.getMsg());
        } catch (Exception e) {
            logger.error("添加设备发生异常：", e);
            return Result.error(ResultStatus.SAVE_ERROR);
        }
    }

    /**
     * 修改设备
     *
     * @param user        用户对象
     * @param id          设备id
     * @param title       设备名称
     * @param devType     设备列席
     * @param mac         物理地址
     * @param description 设备描述
     * @param file        设备图片
     * @return
     */
    @ApiOperation(value = "修改设备", notes = "修改设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", required = true, dataType = "string", paramType = "header"),
    })
    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    @ResponseBody
    @Authorization
    @Permissions(role = Role.DEVELOPER)
    public Result add(@ApiIgnore @CurrentUser User user,
                      @PathVariable String id,
                      @RequestParam String title,
                      @RequestParam String devType,
                      @RequestParam(required = false) String mac,
                      @RequestParam(required = false) String description,
                      @RequestPart(required = false) MultipartFile file) {
        try {
            // 校验参数非空
            if (user == null || StringUtils.isEmpty(user.getId())
                    || StringUtils.isEmpty(id)
                    || StringUtils.isEmpty(title)
                    || StringUtils.isEmpty(devType)) {
                return Result.error(ResultStatus.INPUT_PARAM_ERROR);
            }

            // 调用业务层修改
            deviceBO.update(id, user.getId(), title, devType, mac, description, file);

            // 返回用户信息
            return Result.ok(ResultStatus.UPDATE_SUCCESS);

        } catch (CustomException ce) {
            logger.error("修改设备失败：" + ce.getMsg());
            return Result.error(ResultStatus.UPDATE_ERROR, ce.getMsg());
        } catch (Exception e) {
            logger.error("修改设备发生异常", e);
            return Result.error(ResultStatus.UPDATE_ERROR);
        }
    }

    /**
     * 获取设备信息
     *
     * @param id 设备id
     * @return 设备对象
     */
    @ApiOperation(value = "查询设备信息", notes = "查询设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", required = true, dataType = "string", paramType = "header"),
    })
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    @Authorization
    @Permissions(role = Role.DEVELOPER)
    public Result get(@PathVariable String id) {
        try {
            // 参数非空校验
            if (StringUtils.isEmpty(id)) {
                return Result.error(ResultStatus.INPUT_PARAM_ERROR);
            }

            // 调用业务层查询
            Device device = deviceBO.get(id);

            // 返回用户信息
            return Result.ok(ResultStatus.SELECT_SUCCESS, device);

        } catch (CustomException ce) {
            logger.error("查询设备信息失败：" + ce.getMsg());
            return Result.error(ResultStatus.UPDATE_ERROR, ce.getMsg());
        } catch (Exception e) {
            logger.error("查询设备信息发生异常：", e);
            return Result.error(ResultStatus.UPDATE_ERROR);
        }
    }

    @ApiOperation(value = "删除设备信息", notes = "删除设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", required = true, dataType = "string", paramType = "header"),
    })
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @Authorization
    @Permissions(role = Role.DEVELOPER)
    public Result delete(@PathVariable String id) {
        try {
            // 参数非空校验
            if (StringUtils.isEmpty(id)) {
                return Result.error(ResultStatus.INPUT_PARAM_ERROR);
            }

            // 调用业务层删除
            deviceBO.delete(id);

            // 返回用户成功信息
            return Result.ok(ResultStatus.DELETE_SUCCESS);
        } catch (CustomException ce) {
            logger.error("删除设备失败：" + ce.getMsg());
            return Result.error(ResultStatus.UPDATE_ERROR, ce.getMsg());
        } catch (Exception e) {
            logger.error("删除设备发生异常：", e);
            return Result.error(ResultStatus.UPDATE_ERROR);
        }
    }
}
