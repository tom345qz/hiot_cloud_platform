package com.huatec.hiot_cloud.core.bo.impl;

import com.huatec.hiot_cloud.core.autogenerator.entity.User;
import com.huatec.hiot_cloud.core.autogenerator.service.IUserService;
import com.huatec.hiot_cloud.core.bo.IAuthBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author WUWENBO
 * @since 2020/10/21 10:30
 */
@Service
@Transactional
public class AuthBOImpl implements IAuthBO {

    @Autowired
    private IUserService userDAO;

    @Override
    public User findForLogin(String username, String password) {
        return userDAO.findForLogin(username, password);
    }

    @Override
    public void updateLastlogin(String id) {
        userDAO.updateLastlogin(id);
    }
}
