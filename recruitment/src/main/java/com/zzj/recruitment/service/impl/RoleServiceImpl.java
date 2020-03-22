package com.zzj.recruitment.service.impl;

import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.dao.RoleMapper;
import com.zzj.recruitment.pojo.Role;
import com.zzj.recruitment.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-21 16:52:11
 */
@Service("roleService")
public class RoleServiceImpl implements IRoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    @Transactional
    public List<Role> getAllRolesByUserId(Integer userId) {
        List<Role> roles = (List<Role>) redisTemplate.opsForValue().get(Const.RIGHT_RESOURCE + userId);
        if (roles == null) {
            roles = roleMapper.selectRolesByUserId(userId);
            if (roles.size() > 0) {
                redisTemplate.opsForValue().set(Const.RIGHT_RESOURCE + userId, roles, Const.RedisCacheExtime.REDIS_SESSION_EXTIME, TimeUnit.SECONDS);
                return roles;
            }
        } else if (roles != null) {
            return roles;
        }
        return null;
    }
}
