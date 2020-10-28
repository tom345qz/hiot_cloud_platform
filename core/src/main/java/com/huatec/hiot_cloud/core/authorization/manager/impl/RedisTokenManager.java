package com.huatec.hiot_cloud.core.authorization.manager.impl;

/**
 * @ Created by liwenqiang on 2017/5/10 0010.
 * @ Description:
 */

import com.huatec.hiot_cloud.core.authorization.manager.TokenManager;
import com.huatec.hiot_cloud.core.authorization.model.TokenModel;
import com.huatec.hiot_cloud.core.config.Constants;
import com.huatec.hiot_cloud.core.config.Result;
import com.huatec.hiot_cloud.core.config.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ Created by liwenqiang on 2017/5/10 0010.
 * 通过Redis存储和验证token的实现类（用于前端、APP与设备端token验证）
 */
@Component
public class RedisTokenManager implements TokenManager {

    private StringRedisTemplate redis;

    @Autowired
    public void setRedis(StringRedisTemplate redis) {
        this.redis = redis;

        //泛型设置成Long,String后必须更改对应的序列化方案
        redis.setKeySerializer(new StringRedisSerializer());
    }

    public TokenModel createToken(String typeCode, String UUId) {
        //使用uuid作为源token，拼接UUId+typeCode;格式：格式："UUId_tokenValue_typecode";

        String type = typeCode;
        String tokenValue = UUId + "_" + UUID.randomUUID().toString().replace("-", "") + "_" + type;
        TokenModel model = new TokenModel(UUId, type, tokenValue);

        //存储到redis并设置过期时间
        redis.boundValueOps(UUId).set(tokenValue, Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);

        return model;
    }

    public TokenModel getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String[] param = authentication.split("_");
        if (param.length != 3) {
            return null;
        }
        //使用UUId和源token和token类型码简单拼接成的token，可以额外增加加密措施
        String UUId = param[0];
        String typeCode = param[2];
        //String tokenValue = param[1];
        return new TokenModel(UUId, typeCode, authentication);
    }

    public Result checkToken(TokenModel model) {
        //Result result = null;
        if (model == null) {
            return Result.error(ResultStatus.USER_AUTH_ERROR);
        }
        String tokenValue = redis.boundValueOps(model.getUUId()).get();
        if (tokenValue == null) {
            return Result.error(ResultStatus.USER_NOT_LOGIN);
        } else if (!tokenValue.equals(model.getTokenValue())) {
            return Result.error(ResultStatus.TOKEN_INVALID);
        }

        //如果验证成功，说明此用户（设备）进行了一次有效操作，延长token的过期时间
        redis.boundValueOps(model.getUUId()).expire(Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
//        if(model.getTypeCode().equalsIgnoreCase(Constants.AUTHORIZATION_USER)){
//            redis.boundValueOps(model.getUUId()).expire(Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
//        }else if(model.getTypeCode().equalsIgnoreCase(Constants.AUTHORIZATION_DEVICE)){
//            redis.boundValueOps(model.getUUId()).expire(Constants.TOKEN_EXPIRES_DAY, TimeUnit.DAYS);
//        }

        return Result.ok(ResultStatus.SUCCESS);
    }

    public void deleteToken(String UUId) {
        redis.delete(UUId);
    }

}