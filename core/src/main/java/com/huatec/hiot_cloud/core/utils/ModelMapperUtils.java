package com.huatec.hiot_cloud.core.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

/**
 * ModelMapper工具类
 *
 * @author
 * @since
 */
public class ModelMapperUtils {

    private static ModelMapper modelMapper = new ModelMapper();

    static {
        // 严格匹配策略，源对象和目标对象属性要完全匹配，才能转化
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static <T> T map(Object source, Class<T> destinationType) {

        if (source == null) {
            return null;
        }

        return (T) modelMapper.map(source, destinationType);
    }

    public static void map(Object source, Object destination) {

        if (source == null) {
            return;
        }

        modelMapper.map(source, destination);
    }
}
