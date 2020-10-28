package com.huatec.hiot_cloud.core.authorization.manager;

import com.huatec.hiot_cloud.core.authorization.model.TokenModel;
import com.huatec.hiot_cloud.core.config.Result;

/**
 * @ Created by liwenqiang on 2017/5/10 0010.
 * @ Description:对token进行操作的接口（用于前端,APP与设备端token验证）
 */
public interface TokenManager {
    /**
     * 创建一个token关联上指定用户（设备）
     *
     * @param UUId 指定用户（设备）的id
     * @return 生成的token
     */
    TokenModel createToken(String typeCode, String UUId);

    /**
     * 检查token是否有效
     *
     * @param token
     * @return 是否有效
     */
    Result checkToken(TokenModel token);

    /**
     * 从字符串中解析token
     *
     * @param authentication 加密后的字符串
     * @return
     */
    TokenModel getToken(String authentication);

    /**
     * 清除token
     *
     * @param UUId 登录用户（设备）的id
     */
    void deleteToken(String UUId);

}

