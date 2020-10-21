package com.huatec.hiot_cloud.core.autogenerator.mapper;

import com.huatec.hiot_cloud.core.autogenerator.entity.Users;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author code generator
 * @since 2020-10-07
 */
public interface UsersMapper extends BaseMapper<Users> {
    /**
     * 返回所有用户
     *
     * @return
     */
    List<Users> findAll();

    /**
     * 保存一个新用户
     *
     * @param user 用户对象
     */
    void saveForRegister(Users user);

    /**
     * 修改用户密码
     *
     * @param id       用户id
     * @param password 用户密码
     */
    void updatePassword(@Param("id") String id, @Param("password") String password);

    /**
     * 根据id删除用户
     *
     * @param id 用户id
     */
    void deleteById(String id);

    /**
     * 修改用户邮箱地址
     *
     * @param id    用户id
     * @param email 邮箱地址
     */
    void updateEmail(@Param("id") String id, @Param("email") String email);

    /**
     * 修改用户邮箱地址
     *
     * @param user 用户对象
     */
    void updateEmail2(Users user);

    /**
     * 修改用户邮箱地址
     *
     * @param map map对象
     */
    void updateEmail3(Map<String, String> map);

    /**
     * 修改用户头像
     *
     * @param user 用户对象
     */
    void updateImg(Users user);

    /**
     * 修改用户最近登陆时间
     *
     * @param user 用户对象
     */
    void updateLastlogin(Users user);

    /**
     * 根据用户名密码返回用户对象
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户对象
     */
    Users findForLogin(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户id查询用户对象
     *
     * @param id 用户id
     * @return 用户对象
     */
    Users findById(String id);

    /**
     * 校验用户名是否已存在
     *
     * @param username
     * @return
     */
    String findUsername(String username);

    /**
     * 校验邮箱是否已存在
     *
     * @param email
     * @return
     */
    String findEmail(String email);
}
