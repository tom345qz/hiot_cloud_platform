package com.huatec.hiot_cloud.core.controller;

import com.huatec.hiot_cloud.core.authorization.annotation.Authorization;
import com.huatec.hiot_cloud.core.authorization.annotation.CurrentUser;
import com.huatec.hiot_cloud.core.autogenerator.entity.User;
import com.huatec.hiot_cloud.core.bo.IUserBO;
import com.huatec.hiot_cloud.core.config.Result;
import com.huatec.hiot_cloud.core.config.ResultStatus;
import com.huatec.hiot_cloud.core.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 用户管理控制器层
 *
 * @author WUWENBO
 * @since 2020/10/28 10:01
 */
@Api(value = "user", description = "用户管理")
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserBO userBO;

    /**
     * 获取用户信息
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "登录", notes = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", required = true, dataType = "string", paramType = "header"),
    })
    @RequestMapping(value = "/one", method = RequestMethod.GET)
    @ResponseBody
    @Authorization
    public Result get(@CurrentUser @ApiIgnore User user) {
        // 参数校验
        if (user == null || StringUtils.isEmpty(user.getId())) {
            return Result.error(ResultStatus.INPUT_PARAM_ERROR);
        }

        // 获取用户信息
        UserDTO userInfo = userBO.get(user.getId());
        if (userInfo == null) {
            return Result.error(ResultStatus.USER_NOT_FOUND);
        }

        return Result.ok(ResultStatus.SELECT_SUCCESS, userInfo);
    }
}
