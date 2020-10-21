package com.huatec.hiot_cloud.core.autogenerator.service.impl;

import com.huatec.hiot_cloud.core.autogenerator.entity.Users;
import com.huatec.hiot_cloud.core.autogenerator.mapper.UsersMapper;
import com.huatec.hiot_cloud.core.autogenerator.service.IUsersService;
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
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Users findForLogin(String username, String password) {
        return usersMapper.findForLogin(username, password);
    }

    @Override
    public void updateLastlogin(String id) {

        Users user = new Users();
        user.setId(id);
        user.setLastlogin(new Date());
        usersMapper.updateLastlogin(user);
    }
}
