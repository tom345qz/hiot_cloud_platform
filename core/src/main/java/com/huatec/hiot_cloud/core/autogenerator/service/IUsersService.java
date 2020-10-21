package com.huatec.hiot_cloud.core.autogenerator.service;

import com.huatec.hiot_cloud.core.autogenerator.entity.Users;
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
public interface IUsersService extends IService<Users> {

    /**
     * 根据用户名密码返回用户对象
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户对象
     */
    Users findForLogin(String username, String password);

    /**
     * 修改用户最近登陆时间
     *
     * @param id 用户id
     */
    void updateLastlogin(String id);
}
