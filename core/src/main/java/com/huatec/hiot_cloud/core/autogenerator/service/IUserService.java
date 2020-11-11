package com.huatec.hiot_cloud.core.autogenerator.service;

import com.huatec.hiot_cloud.core.autogenerator.entity.User;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author code generator
 * @since 2020-10-07
 */
public interface IUserService extends IService<User> {

    /**
     * 根据用户名密码返回用户对象
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户对象
     */
    User findForLogin(String username, String password);

    /**
     * 修改用户最近登陆时间
     *
     * @param id 用户id
     */
    void updateLastlogin(String id);

    /**
     * 根据用户id查询用户对象
     *
     * @param id 用户id
     * @return 用户对象
     */
    User findById(String id);

    /**
     * 校验用户名是否已存在
     *
     * @param username
     * @return
     */
    String findUsername(String username);

    /**
     * 校验邮箱是否已存在
     *
     * @param email
     * @return
     */
    String findEmail(String email);

    /**
     * 保存一个新用户
     *
     * @param user 用户对象
     */
    void saveForRegister(User user);

    /**
     * 修改用户密码
     *
     * @param id       用户id
     * @param password 用户密码
     */
    void updatePassword(String id, String password);

    /**
     * 修改用户邮箱地址
     *
     * @param id    用户id
     * @param email 邮箱地址
     */
    void updateEmail(String id, String email);

    /**
     * 修改用户头像
     *
     * @param user 用户对象
     */
    void updateImg(User user);
}
