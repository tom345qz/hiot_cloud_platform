package com.huatec.hiot_cloud.core.bo;

import com.huatec.hiot_cloud.core.autogenerator.entity.User;
import com.huatec.hiot_cloud.core.dto.UserDTO;
import com.huatec.hiot_cloud.core.params.UserParams;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    /**
     * 保存用户
     *
     * @param userParams
     */
    void save(UserParams userParams);

    /**
     * 修改用户密码
     *
     * @param id       用户id
     * @param password 用户密码
     */
    void updatePassword(String id, String password);

    /**
     * 修改用户邮箱地址
     *
     * @param id    用户id
     * @param email 邮箱地址
     */
    void updateEmail(String id, String email);

    /**
     * 重置密码
     *
     * @param username 用户名
     * @param email    邮箱
     */
    void resetPassword(String username, String email) throws IOException;

    /**
     * 修改用户头像
     *
     * @param user 用户对象
     * @param file 头像文件
     */
    void updateImage(User user, MultipartFile file);
}
