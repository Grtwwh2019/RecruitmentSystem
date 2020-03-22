package com.zzj.recruitment.service.impl;

import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.dao.RightMapper;
import com.zzj.recruitment.pojo.Right;
import com.zzj.recruitment.pojo.Role;
import com.zzj.recruitment.service.IRigthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-13 15:20:16
 */
@Service("rigthService")
public class RigthServiceImpl implements IRigthService {

    @Autowired
    RightMapper rightMapper;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 查出来的权限资源是一个一对多的关系：一个权限资源对应多个角色
     * @return
     */
    @Override
    public List<Right> getAllRights() {
        List<Right> list = (List<Right>) redisTemplate.opsForValue().get(Const.RIGHT_RESOURCE);
        if (list == null) {
            // 将权限资源存到缓存中，避免每次都需要查找数据库
            list = rightMapper.getAllRights();
            redisTemplate.opsForValue().set(Const.RIGHT_RESOURCE, list);
        }
        return list;
    }

}
