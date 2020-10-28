package com.huatec.hiot_cloud.core.bo;

import com.huatec.hiot_cloud.core.autogenerator.entity.User;
import com.huatec.hiot_cloud.core.dto.UserDTO;

/**
 * 用户模块业务层接口
 *
 * @author WUWENBO
 * @since 2020/10/28 10:06
 */
public interface IUserBO {

    /**
     * 根据用户id查询用户对象
     *
     * @param id 用户id
     * @return 用户对象
     */
    UserDTO get(String id);
}
