package com.huatec.hiot_cloud.core.controller;

import com.huatec.hiot_cloud.core.authorization.annotation.Authorization;
import com.huatec.hiot_cloud.core.authorization.annotation.Permissions;
import com.huatec.hiot_cloud.core.authorization.model.Role;
import com.huatec.hiot_cloud.core.autogenerator.entity.Device;
import com.huatec.hiot_cloud.core.autogenerator.entity.Downdatastream;
import com.huatec.hiot_cloud.core.bo.IDataStreamBO;
import com.huatec.hiot_cloud.core.config.Constants;
import com.huatec.hiot_cloud.core.config.Result;
import com.huatec.hiot_cloud.core.config.ResultStatus;
import com.huatec.hiot_cloud.core.dto.DataStreamDTO;
import com.huatec.hiot_cloud.core.utils.CustomException;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通道模块控制器层类
 *
 * @author WUWENBO
 * @since 2020/12/16 9:07
 */
@Api(value = "datastream", description = "通道管理")
@Controller
@RequestMapping("/datastream")
public class DataStreamController {

    @Autowired
    private IDataStreamBO dataStreamBO;

    private Logger logger = LoggerFactory.getLogger(DataStreamController.class);

    @ApiOperation(value = "创建设备通道", notes = "创建设备通道")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", required = true, dataType = "string", paramType = "header"),
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @Authorization
    @Permissions(role = Role.DEVELOPER)
    public Result save(@ApiParam(value = "设备id") @RequestParam String deviceId,
                       @ApiParam(value = "通道名称") @RequestParam(required = false) String title,
                       @ApiParam(value = "通道类型，0：默认全部 1：数值2：开关 3 GPS 4：文本") @RequestParam int dataType,
                       @ApiParam(value = "通道方向，1：设备向上通道 2：设备向下通道 0：设备所有通道") @RequestParam int direction) {
        try {
            // 校验参数非空
            if (StringUtils.isEmpty(deviceId)) {
                return Result.error(ResultStatus.INPUT_PARAM_ERROR);
            }

            // 参数合规性校验
            if (dataType != Constants.DATA_STREAM_TYPE_VALUE
                    && dataType != Constants.DATA_STREAM_TYPE_SWITCH
                    && dataType != Constants.DATA_STREAM_TYPE_GPS
                    && dataType != Constants.DATA_STREAM_TYPE_TXT) {
                return Result.error(ResultStatus.INPUT_PARAM_ERROR, "通道类型不正确");
            }

            if (direction != Constants.DATA_STREAM_DIRECTION_ALL
                    && direction != Constants.DATA_STREAM_DIRECTION_UP
                    && direction != Constants.DATA_STREAM_DIRECTION_DOWN) {
                return Result.error(ResultStatus.INPUT_PARAM_ERROR, "通道方向不正确");
            }

            // 调用业务层修改
            dataStreamBO.save(deviceId, dataType, title, direction);

            // 返回用户信息
            return Result.ok(ResultStatus.SAVE_SUCCESS);

        } catch (CustomException ce) {
            logger.error("新增通道失败：" + ce.getMsg());
            return Result.error(ResultStatus.SAVE_ERROR, ce.getMsg());
        } catch (Exception e) {
            logger.error("新增通道发生异常", e);
            return Result.error(ResultStatus.SAVE_ERROR);
        }
    }

    @ApiOperation(value = "根据上行通道查询下行通道数据", notes = "根据上行通道查询下行通道数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", required = true, dataType = "string", paramType = "header"),
    })
    @RequestMapping(value = "{updatastreamId}/downdatastream", method = RequestMethod.GET)
    @ResponseBody
    @Authorization
    @Permissions(role = Role.DEVELOPER)
    public Result getDownDataStreamByUp(@ApiParam(value = "上行通道id") @PathVariable String updatastreamId) {
        try {
            // 参数非空校验
            if (StringUtils.isEmpty(updatastreamId)) {
                return Result.error(ResultStatus.INPUT_PARAM_ERROR);
            }

            // 调用业务层查询
            Downdatastream downdatastream = dataStreamBO.getDownDataStreamByUp(updatastreamId);

            // 返回用户信息
            return Result.ok(ResultStatus.SELECT_SUCCESS, downdatastream);

        } catch (CustomException ce) {
            logger.error("查询设备信息失败：" + ce.getMsg());
            return Result.error(ResultStatus.SELECT_ERROR, ce.getMsg());
        } catch (Exception e) {
            logger.error("查询设备信息发生异常：", e);
            return Result.error(ResultStatus.SELECT_ERROR);
        }
    }

    @ApiOperation(value = "根据条件查询通道列表", notes = "根据条件查询通道列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", required = true, dataType = "string", paramType = "header"),
    })
    @RequestMapping(value = "{deviceId}/datastream/{direction}/{dataType}", method = RequestMethod.GET)
    @ResponseBody
    @Authorization
    @Permissions(role = Role.DEVELOPER)
    public Result listDataStreamByDevice(@ApiParam(value = "设备id") @PathVariable String deviceId,
                                         @ApiParam(value = "通道方向，1：设备向上通道 2：设备向下通道 0：设备所有通道") @PathVariable int direction,
                                         @ApiParam(value = "通道类型，0：默认全部 1：数值2：开关 3 GPS 4：文本") @PathVariable int dataType) {
        try {
            // 参数非空校验
            if (StringUtils.isEmpty(deviceId)) {
                return Result.error(ResultStatus.INPUT_PARAM_ERROR);
            }

            // 参数合规性校验
            if (dataType != Constants.DATA_STREAM_TYPE_ALL
                    && dataType != Constants.DATA_STREAM_TYPE_VALUE
                    && dataType != Constants.DATA_STREAM_TYPE_SWITCH
                    && dataType != Constants.DATA_STREAM_TYPE_GPS
                    && dataType != Constants.DATA_STREAM_TYPE_TXT) {
                return Result.error(ResultStatus.INPUT_PARAM_ERROR, "通道类型不正确");
            }

            if (direction != Constants.DATA_STREAM_DIRECTION_ALL
                    && direction != Constants.DATA_STREAM_DIRECTION_UP
                    && direction != Constants.DATA_STREAM_DIRECTION_DOWN) {
                return Result.error(ResultStatus.INPUT_PARAM_ERROR, "通道方向不正确");
            }

            // 调用业务层查询
            List<DataStreamDTO> dataStreamDTOList = dataStreamBO.listDataStreamByDevice(deviceId, direction, dataType);

            // 返回用户信息
            return Result.ok(ResultStatus.SELECT_SUCCESS, dataStreamDTOList);

        } catch (CustomException ce) {
            logger.error("查询设备信息失败：" + ce.getMsg());
            return Result.error(ResultStatus.SELECT_ERROR, ce.getMsg());
        } catch (Exception e) {
            logger.error("查询设备信息发生异常：", e);
            return Result.error(ResultStatus.SELECT_ERROR);
        }
    }
}
