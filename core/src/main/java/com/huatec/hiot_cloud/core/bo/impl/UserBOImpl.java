package com.huatec.hiot_cloud.core.bo.impl;

import com.huatec.hiot_cloud.core.autogenerator.entity.User;
import com.huatec.hiot_cloud.core.autogenerator.service.IUserService;
import com.huatec.hiot_cloud.core.bo.IUserBO;
import com.huatec.hiot_cloud.core.config.BooleanTypeEnum;
import com.huatec.hiot_cloud.core.config.Constants;
import com.huatec.hiot_cloud.core.dto.UserDTO;
import com.huatec.hiot_cloud.core.params.UserParams;
import com.huatec.hiot_cloud.core.utils.CustomException;
import com.huatec.hiot_cloud.core.utils.ModelMapperUtils;
import com.huatec.hiot_cloud.core.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public void save(UserParams userParams) {

        // 校验用户名是否已存在
        if (StringUtils.isNotEmpty(userDAO.findUsername(userParams.getUsername()))) {
            // 返回错误信息
            throw new CustomException("用户名已存在");
        }

        // 校验邮箱是否已存在
        if (StringUtils.isNotEmpty(userDAO.findEmail(userParams.getEmail()))) {
            // 返回错误信息
            throw new CustomException("邮箱已存在");
        }

        // 字段赋初始值
        User user = ModelMapperUtils.map(userParams, User.class);
        user.setId(UUIDUtil.getUUID());
        user.setIsActive(BooleanTypeEnum.FALSE.getNumber());
        user.setIsSuperuser(BooleanTypeEnum.FALSE.getNumber());

        // 设置用户类型
        if (userParams.getUserType() == Constants.USER_TYPE_DEVELOPER) {
            user.setIsDeveloper(BooleanTypeEnum.TRUE.getNumber());
            user.setIsStaff(BooleanTypeEnum.FALSE.getNumber());
        }
        if (userParams.getUserType() == Constants.USER_TYPE_STAFF) {
            user.setIsDeveloper(BooleanTypeEnum.FALSE.getNumber());
            user.setIsStaff(BooleanTypeEnum.TRUE.getNumber());

        }

        // 保存
        userDAO.saveForRegister(user);
    }

    @Override
    public void updatePassword(String id, String password) {
        userDAO.updatePassword(id, password);
    }

    @Override
    public void updateEmail(String id, String email) {
        userDAO.updateEmail(id, email);
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
