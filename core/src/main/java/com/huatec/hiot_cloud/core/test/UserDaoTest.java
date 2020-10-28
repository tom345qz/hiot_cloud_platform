package com.huatec.hiot_cloud.core.test;

import com.huatec.hiot_cloud.core.autogenerator.entity.User;
import com.huatec.hiot_cloud.core.autogenerator.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 用户实体类
 *
 * @author WUWENBO
 * @since 2019/10/29
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserDaoTest {

    /**
     * 自动装载
     */
    @Autowired
    private UserMapper userDao;

    /**
     * 测试查找所有用户
     */
    @Test
    public void testFindAll() {
        System.out.println(userDao.findAll());
        System.out.println(userDao.findAll() == null ? "测试失败" : "测试成功");
    }

    @Test
    public void testSaveForRegister() {
        User user = new User();
        user.setId("abc12345");
        user.setPassword("12345");
        user.setUsername("test12345");
        user.setEmail("test12345@qq.com");
        userDao.saveForRegister(user);
    }

    @Test
    public void testUpdatePassword() {
        userDao.updatePassword("abc12345", "aaabbb");
    }

    @Test
    public void testDeleteById() {
        userDao.deleteById("abc12345");
    }

    @Test
    public void testUpdateEmail() {
        userDao.updateEmail("abc12345", "abc@test.com");
    }

    @Test
    public void testUpdateEmail2() {
        User user = new User();
        user.setId("abc12345");
        user.setEmail("123@qq.com");
        userDao.updateEmail2(user);
    }

    @Test
    public void testUpdateEmail3() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "abc12345");
        map.put("email", "test@test.com");
        userDao.updateEmail3(map);
    }

    @Test
    public void testUpdateImg() {
        User user = new User();
        user.setId("abc12345");
        user.setImg("/test/test.jpg");
        userDao.updateImg(user);
    }

    @Test
    public void testUpdateLastlogin() {
        User user = new User();
        user.setId("abc12345");
        user.setLastlogin(new Date());
        userDao.updateImg(user);
    }

    @Test
    public void testfindForLogin() {
        System.out.println(userDao.findForLogin("test123", "23456"));
    }

    @Test
    public void testFindUsername() {
        System.out.println(userDao.findUsername("test12345"));
    }

    @Test
    public void testFindEmail() {
        System.out.println(userDao.findEmail("testabc2020@qq.com"));
    }

    @Test
    public void testFindById() {
        System.out.println(userDao.findById("abc12345"));
    }


}
