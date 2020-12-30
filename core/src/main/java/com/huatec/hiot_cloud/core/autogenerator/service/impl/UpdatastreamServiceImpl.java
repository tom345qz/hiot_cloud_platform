package com.huatec.hiot_cloud.core.autogenerator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.huatec.hiot_cloud.core.autogenerator.entity.Updatastream;
import com.huatec.hiot_cloud.core.autogenerator.mapper.UpdatastreamMapper;
import com.huatec.hiot_cloud.core.autogenerator.service.IUpdatastreamService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author code generator
 * @since 2020-12-15
 */
@Service
public class UpdatastreamServiceImpl extends ServiceImpl<UpdatastreamMapper, Updatastream> implements IUpdatastreamService {

    @Override
    public List<String> findAllIds() {
        Wrapper<Updatastream> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("id");
        List<Updatastream> updatastreamList = selectList(wrapper);
        if (updatastreamList == null || updatastreamList.isEmpty()) {
            return null;
        }
        List<String> resultList = new ArrayList<>();
        for (Updatastream bean : updatastreamList) {
            resultList.add(bean.getId());
        }
        return resultList;
    }
}
