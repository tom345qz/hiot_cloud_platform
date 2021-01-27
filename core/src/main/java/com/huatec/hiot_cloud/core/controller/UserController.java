package com.huatec.hiot_cloud.core.controller;

import com.huatec.hiot_cloud.core.authorization.annotation.Authorization;
import com.huatec.hiot_cloud.core.authorization.annotation.CurrentUser;
import com.huatec.hiot_cloud.core.autogenerator.entity.User;
import com.huatec.hiot_cloud.core.bo.IUserBO;
import com.huatec.hiot_cloud.core.config.Constants;
import com.huatec.hiot_cloud.core.config.Result;
import com.huatec.hiot_cloud.core.config.ResultStatus;
import com.huatec.hiot_cloud.core.dto.UserDTO;
import com.huatec.hiot_cloud.core.params.UserParams;
import com.huatec.hiot_cloud.core.utils.CustomException;
import com.huatec.hiot_cloud.core.utils.ValidateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    // 用户对象
    @Autowired
    IUserBO userBO;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value = "注册", notes = "注册，userType： 用户类型，0：开发者，1：普通用户")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public Result register(@RequestBody UserParams userParams) {

        try {
            // 参数非空校验
            if (userParams == null
                    || StringUtils.isEmpty(userParams.getEmail())
                    || StringUtils.isEmpty(userParams.getUsername())
                    || StringUtils.isEmpty(userParams.getPassword())) {
                return Result.error(ResultStatus.INPUT_PARAM_ERROR);
            }

            // 用户类型校验
            if (userParams.getUserType() != Constants.USER_TYPE_DEVELOPER
                    && userParams.getUserType() != Constants.USER_TYPE_STAFF) {
                return Result.error(ResultStatus.INPUT_PARAM_ERROR, "用户类型不正确");
            }

            // 参数合规性校验，用正则表达式校验用户名，邮箱，密码
            if (!ValidateUtils.validateEmail(userParams.getEmail())) {
                return Result.error(ResultStatus.EMAIL_FORMAT_ERROR);
            }
            if (!ValidateUtils.validateUserName((userParams.getUsername()))) {
                return Result.error(ResultStatus.USER_NAME_FORMAT_ERROR);
            }
            if (!ValidateUtils.validatePassword(userParams.getPassword())) {
                return Result.error(ResultStatus.PASSWORD_FORMAT_ERROR);
            }

            // 调用业务层做保存操作
            userBO.save(userParams);

            // 返回
            logger.info("注册成功");
            return Result.ok(ResultStatus.SAVE_SUCCESS);
        } catch (CustomException ce) {
            logger.error("注册失败：" + ce.getMsg());
            return Result.error(ResultStatus.SAVE_ERROR, ce.getMsg());
        } catch (Exception e) {
            logger.error("注册发生异常：", e);
            return Result.error(ResultStatus.SAVE_ERROR, "注册用户失败");
        }
    }

    /**
     * 获取用户信息
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", required = true, dataType = "string", paramType = "header"),
    })
    @RequestMapping(value = "/one", method = RequestMethod.GET)
    @ResponseBody
    @Authorization
    public Result get(@ApiIgnore @CurrentUser User user) {
        try {

            // 参数校验
            if (user == null || StringUtils.isEmpty(user.getId())) {
                return Result.error(ResultStatus.INPUT_PARAM_ERROR);
            }

            // 获取用户信息
            UserDTO userInfo = userBO.get(user.getId());
            if (userInfo == null) {
                return Result.error(ResultStatus.USER_NOT_FOUND);
            }

            logger.info("获取用户成功");
            return Result.ok(ResultStatus.SELECT_SUCCESS, userInfo);
        } catch (Exception e) {
            logger.error("获取用户发生异常", e);
            return Result.error(ResultStatus.SELECT_ERROR);
        }
    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", required = true, dataType = "string", paramType = "header"),
    })
    @RequestMapping(value = "password", method = RequestMethod.PUT)
    @ResponseBody
    @Authorization
    public Result updatePassword(@ApiIgnore @CurrentUser User user,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword) {
        try {
            // 参数非空校验
            if (user == null
                    || StringUtils.isEmpty(user.getId())
                    || StringUtils.isEmpty(oldPassword)
                    || StringUtils.isEmpty(newPassword)) {
                return Result.error(ResultStatus.INPUT_PARAM_ERROR);
            }

            // 参数合规校验
            if (!ValidateUtils.validatePassword(newPassword)) {
                return Result.error(ResultStatus.PASSWORD_FORMAT_ERROR);
            }

            // 校验旧密码
            if (!oldPassword.equals(user.getPassword())) {
                return Result.error(ResultStatus.USER_PASSWORD_ERROR);
            }

            // 修改密码
            userBO.updatePassword(user.getId(), newPassword);

            // 返回
            logger.info("修改密码成功");
            return Result.ok(ResultStatus.UPDATE_SUCCESS);
        } catch (Exception e) {
            logger.error("修改密码发生异常", e);
            return Result.error(ResultStatus.UPDATE_ERROR);
        }
    }

    @ApiOperation(value = "修改邮箱", notes = "修改邮箱")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", required = true, dataType = "string", paramType = "header"),
    })
    @RequestMapping(value = "email", method = RequestMethod.PUT)
    @ResponseBody
    @Authorization
    public Result updateEmail(@ApiIgnore @CurrentUser User user,
                              @RequestParam String newEmail) {
        try {
            // 参数非空校验
            if (user == null
                    || StringUtils.isEmpty(user.getId())
                    || StringUtils.isEmpty(newEmail)) {
                return Result.error(ResultStatus.INPUT_PARAM_ERROR);
            }

            // 参数合规校验
            if (!ValidateUtils.validateEmail(newEmail)) {
                return Result.error(ResultStatus.EMAIL_FORMAT_ERROR);
            }

            // 修改邮箱
            userBO.updateEmail(user.getId(), newEmail);

            // 返回
            logger.info("修改邮箱成功");
            return Result.ok(ResultStatus.UPDATE_SUCCESS);
        } catch (Exception e) {
            logger.error("修改邮箱发生异常", e);
            return Result.error(ResultStatus.UPDATE_ERROR);
        }

    }

    /**
     * 重置用户密码
     *
     * @param username 用户名
     * @param email    邮箱
     * @return
     */
    @ApiOperation(value = "重置密码", notes = "重置密码")
    @RequestMapping(value = "reset-password/{email}/{username}", method = RequestMethod.PUT)
    @ResponseBody
    public Result resetPassword(@PathVariable String username,
                                @PathVariable String email) {
        try {
            // 校验参数非空
            if (StringUtils.isEmpty(username)
                    || StringUtils.isEmpty(email)) {
                return Result.ok(ResultStatus.INPUT_PARAM_ERROR);
            }

            // 调用BO层方法做重置密码
            userBO.resetPassword(username, email);

            // 返回
            return Result.ok(ResultStatus.UPDATE_SUCCESS);
        } catch (CustomException ce) {
            logger.error("重置密码错误：" + ce.getMsg());
            return Result.error(ResultStatus.RESET_PASSWORD_ERROR, ce.getMsg());
        } catch (Exception e) {
            logger.error("重置密码失败：", e);
            return Result.error(ResultStatus.RESET_PASSWORD_ERROR);
        }
    }

    /**
     * 修改用户头像
     *
     * @param user 用户信息
     * @param file 头像文件
     * @return
     */
    @ApiOperation(value = "修改用户头像", notes = "修改用户头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", required = true, dataType = "string", paramType = "header"),
    })
    @ResponseBody
    @RequestMapping(value = "img", method = RequestMethod.POST)
    @Authorization
    public Result updateImage(@ApiIgnore @CurrentUser User user,
                              @RequestPart MultipartFile file) {
        try {
            // 校验参数非空
            if (user == null ||
                    file == null) {
                return Result.ok(ResultStatus.INPUT_PARAM_ERROR);
            }

            // 调用用业务层方法修改头像
            userBO.updateImage(user, file);

            // 返回成功信息
            return Result.ok(ResultStatus.UPDATE_SUCCESS);

        } catch (Exception e) {
            logger.error("修改头像失败：", e);
            return Result.error(ResultStatus.UPDATE_ERROR);

        }
    }
}
