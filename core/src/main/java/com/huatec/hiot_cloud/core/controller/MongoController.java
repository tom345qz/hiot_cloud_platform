package com.huatec.hiot_cloud.core.controller;

import com.huatec.hiot_cloud.core.authorization.annotation.Authorization;
import com.huatec.hiot_cloud.core.authorization.annotation.Permissions;
import com.huatec.hiot_cloud.core.authorization.model.Role;
import com.huatec.hiot_cloud.core.bo.IMongoBO;
import com.huatec.hiot_cloud.core.config.Result;
import com.huatec.hiot_cloud.core.config.ResultStatus;
import com.huatec.hiot_cloud.mongodb.entity.base.UpdatastreamData;
import com.huatec.hiot_cloud.mongodb.manager.IMongoApiManager;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @ Created by liwenqiang  on 2017/5/16 0016 at 下午 5:53  for hiot
 * @ Description:  mondb API，调用mongodb模块处理设备→后台的向上通道数据传输与保存。
 */
@RestController
@RequestMapping("mongo")
@Api(value = "mongo", description = "上行通道数据", position = 14)
public class MongoController {
    @Autowired
    private IMongoApiManager mongoApiManager;
    @Autowired
    private IMongoBO mongoBO;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "保存设备数据", notes = "按向上通道ID保存设备数据,说明如下：<br/>" +
            "1、向上通道ID(updatastreamId)必填<br/>" +
            "2、deviceData：数据，" +
            "如果数据类型为3（地理位置型），则按\"longitude,latitude,elevation\"字符串形式，其中\",\"是英文逗号")
    public Result upload(@ApiParam(value = "向上通道ID") @RequestParam String updatastreamId,
                         @ApiParam(value = "data") @RequestParam() String deviceData) {
        Integer dataType = mongoBO.findDataTypeByUdsId(updatastreamId);
        if (dataType == null) {
            return Result.error(ResultStatus.DATASTREAM_NOT_FOUND);
        }
        UpdatastreamData updatastreamData = mongoBO.getUdsData(updatastreamId, dataType, deviceData);
        mongoApiManager.upload(dataType, updatastreamData);
        return Result.ok(ResultStatus.SAVE_SUCCESS);
    }

    @RequestMapping(value = "/download/{updatastreamId}/one", method = RequestMethod.GET)
    @ApiOperation(value = "查询某一设备某一通道最近一条数据", notes = "查询某一设备某一通道最近一条数据")
    /*请求权限校验*/
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, dataType = "string", paramType = "header"),
    })
    @Permissions(role = Role.DEVELOPER + Role.STAFF)
    public Result downloadLastedOneByUdsId(@ApiParam("向上通道ID") @PathVariable String updatastreamId) {
        Integer dataType = mongoBO.findDataTypeByUdsId(updatastreamId);
        UpdatastreamData updatastreamData = mongoApiManager.downloadLastedOneByUdsId(dataType, updatastreamId);
        return Result.ok(ResultStatus.SELECT_SUCCESS, updatastreamData);
    }

    @RequestMapping(value = "/download/{updatastreamId}/{skip}/{limit}/List", method = RequestMethod.GET)
    @ApiOperation(value = "查询某一设备某一通道指定位置指定数量数据", notes = "查询某一设备某一通道指定位置指定数量数据")
    /*请求权限校验*/
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, dataType = "string", paramType = "header"),
    })
    @Permissions(role = Role.DEVELOPER + Role.STAFF)
    public Result downloadListByUdsId(@ApiParam("向上通道ID") @PathVariable String updatastreamId,
                                      @ApiParam("起始条数") @PathVariable Integer skip, @ApiParam("总条数") @PathVariable Integer limit) {
        Integer dataType = mongoBO.findDataTypeByUdsId(updatastreamId);
        List<? extends UpdatastreamData> updatastreamDataList = mongoApiManager.downloadListByUdsId(dataType, updatastreamId, skip, limit);
        return Result.ok(ResultStatus.SELECT_SUCCESS, updatastreamDataList);
    }

    @RequestMapping(value = "/download/{updatastreamId}/period/List", method = RequestMethod.GET)
    @ApiOperation(value = "查询某一设备某一通道某一时间段的数据", notes = "查询某一设备某一通道某一时间段的数据")
    /*请求权限校验*/
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, dataType = "string", paramType = "header"),
    })
    @Permissions(role = Role.DEVELOPER + Role.STAFF)
    public Result downloadListByUdsIdWithPeriod(@ApiParam("向上通道ID") @PathVariable String updatastreamId,
                                                @ApiParam("起始时间,时间格式：yyyy-MM-dd HH:mm") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date begin,
                                                @ApiParam("结束时间,时间格式：yyyy-MM-dd HH:mm") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date end) {
        Integer dataType = mongoBO.findDataTypeByUdsId(updatastreamId);
        List<? extends UpdatastreamData> updatastreamDataList = mongoApiManager.downloadListByUdsIdWithPeriod(dataType, updatastreamId, begin, end);
        return Result.ok(ResultStatus.SELECT_SUCCESS, updatastreamDataList);
    }
}
