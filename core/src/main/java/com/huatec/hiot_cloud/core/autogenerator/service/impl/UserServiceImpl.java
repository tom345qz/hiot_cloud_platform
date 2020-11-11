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

    @Override
    public User findById(String id) {
        return userMapper.findById(id);
    }

    @Override
    public String findUsername(String username) {
        return userMapper.findUsername(username);
    }

    @Override
    public String findEmail(String email) {
        return userMapper.findEmail(email);
    }

    @Override
    public void saveForRegister(User user) {
        userMapper.saveForRegister(user);
    }

    @Override
    public void updatePassword(String id, String password) {
        userMapper.updatePassword(id, password);
    }

    @Override
    public void updateEmail(String id, String email) {
        userMapper.updateEmail(id, email);
    }

    @Override
    public void updateImg(User user) {
        userMapper.updateImg(user);
    }
}
