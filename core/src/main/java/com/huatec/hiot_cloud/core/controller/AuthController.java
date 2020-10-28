package com.huatec.hiot_cloud.core.controller;

import com.huatec.hiot_cloud.core.autogenerator.entity.User;
import com.huatec.hiot_cloud.core.bo.IAuthBO;
import com.huatec.hiot_cloud.core.config.Constant;
import com.huatec.hiot_cloud.core.config.Result;
import com.huatec.hiot_cloud.core.config.ResultStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        if (!Constant.LOGIN_CODE_APP.equals(loginCode) && (!Constant.LOGIN_CODE_WEB.equals(loginCode))) {
            return Result.error(ResultStatus.INPUT_PARAM_ERROR, "登录代码有误");
        }

        // 校验用户名，密码是否正确
        User user = authBO.findForLogin(username, password);
        if (user == null) {
            return Result.error(ResultStatus.LOGIN_ERROR);
        }

        // 校验如果是普通用户，不允许用web登录
        if (user.getIsStaff() == Constant.STATUS_TRUE && Constant.LOGIN_CODE_WEB.equals(loginCode)) {
            return Result.error(ResultStatus.LOGIN_CODE_APP_ERROR);
        }

        // 校验如果是开发人员，不允许用app登录
        if (user.getIsDeveloper() == Constant.STATUS_TRUE && Constant.LOGIN_CODE_APP.equals(loginCode)) {
            return Result.error(ResultStatus.LOGIN_CODE_WEB_ERROR);
        }

        // 生成token，更新登录时间
        String token = createToken();
        authBO.updateLastlogin(user.getId());

        // 返回token
        return Result.ok(ResultStatus.SUCCESS, token);
    }

    @ApiOperation(value = "注销", notes = "用户注销")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Result logout() {
        return Result.ok(ResultStatus.SUCCESS);
    }

    /**
     * 创建token
     *
     * @return
     */
    private String createToken() {
        return "token test";
    }
}
