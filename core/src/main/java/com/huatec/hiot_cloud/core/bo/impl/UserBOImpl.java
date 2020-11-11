package com.huatec.hiot_cloud.core.bo.impl;

import com.huatec.hiot_cloud.core.autogenerator.entity.User;
import com.huatec.hiot_cloud.core.autogenerator.service.IUserService;
import com.huatec.hiot_cloud.core.bo.IUserBO;
import com.huatec.hiot_cloud.core.config.BooleanTypeEnum;
import com.huatec.hiot_cloud.core.config.Constants;
import com.huatec.hiot_cloud.core.dto.UserDTO;
import com.huatec.hiot_cloud.core.params.UserParams;
import com.huatec.hiot_cloud.core.utils.*;
import com.huatec.hiot_cloud.core.utils.MailUtil.MSUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * 用户模块业务层实现类
 *
 * @author WUWENBO
 * @since 2020/10/28 10:07
 */
@Service
@Transactional
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

    @Override
    public void resetPassword(String username, String email) throws IOException {
        // 校验用户名和邮箱是否正确
        String id1 = userDAO.findUsername(username);
        String id2 = userDAO.findEmail(email);
        if (StringUtils.isEmpty(id1) ||
                StringUtils.isEmpty(id2) ||
                !id1.equals(id2)) {
            throw new CustomException("用户名或邮箱不正确！");
        }

        // 构造新密码，并用MD5加密转换
        String newPassword = "A" + getStringRandom(Constants.PASSWORD_LENGTH);
        String md5Password = MD5Util.getMD5Str(newPassword);

        // 新密码更新到数据库
        userDAO.updatePassword(id1, md5Password);

        // 新密码发给用户邮箱
        MSUtil.sendEmail(newPassword, email);
    }

    @Override
    public void updateImage(User user, MultipartFile file) {
        // 获取用户头像路径
        String oldImagePath = user.getImg();

        // 删除旧头像
        File oldFile = new File(Constants.UPLOAD_PATH_PREFIX + oldImagePath);
        oldFile.delete();

        // 保存新头像
        String newImagePath = FileUtil.uploadImg(file, Constants.UPLOAD_PATH_USER);

        // 更新用户头像字典
        user.setImg(newImagePath);
        userDAO.updateImg(user);
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

    //生成随机数字和字母
    private String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
