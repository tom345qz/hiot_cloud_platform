package com.huatec.hiot_cloud.core.autogenerator.service;

import com.huatec.hiot_cloud.core.autogenerator.entity.User;
import com.baomidou.mybatisplus.service.IService;

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
}
