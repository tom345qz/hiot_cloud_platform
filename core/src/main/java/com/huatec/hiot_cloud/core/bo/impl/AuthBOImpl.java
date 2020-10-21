package com.huatec.hiot_cloud.core.bo.impl;

import com.huatec.hiot_cloud.core.autogenerator.entity.Users;
import com.huatec.hiot_cloud.core.autogenerator.service.IUsersService;
import com.huatec.hiot_cloud.core.bo.IAuthBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WUWENBO
 * @since 2020/10/21 10:30
 */
@Service
public class AuthBOImpl implements IAuthBO {

    @Autowired
    private IUsersService userDAO;

    @Override
    public Users findForLogin(String username, String password) {
        return userDAO.findForLogin(username, password);
    }

    @Override
    public void updateLastlogin(String id) {
        userDAO.updateLastlogin(id);
    }
}
