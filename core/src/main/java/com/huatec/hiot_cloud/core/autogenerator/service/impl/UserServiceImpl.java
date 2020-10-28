package com.huatec.hiot_cloud.core.autogenerator.service.impl;

import com.huatec.hiot_cloud.core.autogenerator.entity.User;
import com.huatec.hiot_cloud.core.autogenerator.mapper.UserMapper;
import com.huatec.hiot_cloud.core.autogenerator.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author code generator
 * @since 2020-10-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findForLogin(String username, String password) {
        return userMapper.findForLogin(username, password);
    }

    @Override
    public void updateLastlogin(String id) {

        User user = new User();
        user.setId(id);
        user.setLastlogin(new Date());
        userMapper.updateLastlogin(user);
    }
}
