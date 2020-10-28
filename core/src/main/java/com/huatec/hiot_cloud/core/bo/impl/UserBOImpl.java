package com.huatec.hiot_cloud.core.bo.impl;

import com.huatec.hiot_cloud.core.autogenerator.entity.User;
import com.huatec.hiot_cloud.core.autogenerator.service.IUserService;
import com.huatec.hiot_cloud.core.bo.IUserBO;
import com.huatec.hiot_cloud.core.dto.UserDTO;
import com.huatec.hiot_cloud.core.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户模块业务层实现类
 *
 * @author WUWENBO
 * @since 2020/10/28 10:07
 */
@Service
public class UserBOImpl implements IUserBO {

    @Autowired
    private IUserService userDAO;

    @Override
    public UserDTO get(String id) {
        return toDTO(userDAO.findById(id));
    }

    /**
     * 转换用户对象为DTO
     *
     * @param user 用户对象
     * @return DTO对象
     */
    private UserDTO toDTO(User user) {
        UserDTO userDTO = ModelMapperUtils.map(user, UserDTO.class);
        userDTO.setActive(user.getIsActive());
        userDTO.setStaff(user.getIsStaff());
        userDTO.setDeveloper(user.getIsDeveloper());
        userDTO.setSuperuser(user.getIsSuperuser());
        return userDTO;
    }


}
