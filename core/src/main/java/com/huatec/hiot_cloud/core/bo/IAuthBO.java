package com.huatec.hiot_cloud.core.bo;

import com.huatec.hiot_cloud.core.autogenerator.entity.User;

/**
 * 用户认证业务层接口
 *
 * @author WUWENBO
 * @since 2020/10/21 10:28
 */
public interface IAuthBO {
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
}
