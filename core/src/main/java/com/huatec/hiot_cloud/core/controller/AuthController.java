package com.huatec.hiot_cloud.core.controller;

import com.huatec.hiot_cloud.core.authorization.annotation.Authorization;
import com.huatec.hiot_cloud.core.authorization.annotation.CurrentUser;
import com.huatec.hiot_cloud.core.authorization.manager.TokenManager;
import com.huatec.hiot_cloud.core.authorization.model.TokenModel;
import com.huatec.hiot_cloud.core.autogenerator.entity.User;
import com.huatec.hiot_cloud.core.bo.IAuthBO;
import com.huatec.hiot_cloud.core.config.Constants;
import com.huatec.hiot_cloud.core.config.Result;
import com.huatec.hiot_cloud.core.config.ResultStatus;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 身份验证模块控制器层
 *
 * @author WUWENBO
 * @since 2020/10/21 9:23
 */
@Api(value = "auth", description = "用户认证")
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthBO authBO;

    @Autowired
    private TokenManager tokenManager;

    /**
     * 登录
     *
     * @param username  用户名
     * @param password  密码
     * @param loginCode 登录代码，app：移动端，web：网页端
     */
    @ApiOperation(value = "登录", notes = "用户登录，loginCode取值，app：移动端，web：网页端")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(@ApiParam("用户名") @RequestParam String username,
                        @ApiParam("密码") @RequestParam String password,
                        @ApiParam("登录代码") @RequestParam String loginCode) {

        // 校验参数为空
        if (StringUtils.isEmpty(username)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(loginCode)) {
            return Result.error(ResultStatus.INPUT_PARAM_ERROR);
        }

        // 校验loginCode正确性
        if (!Constants.LOGIN_CODE_APP.equals(loginCode) && (!Constants.LOGIN_CODE_WEB.equals(loginCode))) {
            return Result.error(ResultStatus.INPUT_PARAM_ERROR, "登录代码有误");
        }

        // 校验用户名，密码是否正确
        User user = authBO.findForLogin(username, password);
        if (user == null) {
            return Result.error(ResultStatus.LOGIN_ERROR);
        }

        // 校验如果是普通用户，不允许用web登录
        if (user.getIsStaff() == Constants.STATUS_TRUE && Constants.LOGIN_CODE_WEB.equals(loginCode)) {
            return Result.error(ResultStatus.LOGIN_CODE_APP_ERROR);
        }

        // 校验如果是开发人员，不允许用app登录
        if (user.getIsDeveloper() == Constants.STATUS_TRUE && Constants.LOGIN_CODE_APP.equals(loginCode)) {
            return Result.error(ResultStatus.LOGIN_CODE_WEB_ERROR);
        }

        // 生成token，更新登录时间
        TokenModel token = tokenManager.createToken(Constants.AUTHORIZATION_USER, user.getId());
        authBO.updateLastlogin(user.getId());

        // 返回token
        return Result.ok(ResultStatus.SUCCESS, token);
    }

    @ApiOperation(value = "注销", notes = "用户注销")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", required = true, dataType = "string", paramType = "header"),
    })
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    @Authorization
    public Result logout(@CurrentUser @ApiIgnore User user) {
        tokenManager.deleteToken(user.getId());
        return Result.ok(ResultStatus.SUCCESS);
    }

    @ApiOperation(value = "测试", notes = "测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", required = true, dataType = "string", paramType = "header"),
    })
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    @Authorization
    public Result test() {

        return Result.ok(ResultStatus.SUCCESS);
    }

}
