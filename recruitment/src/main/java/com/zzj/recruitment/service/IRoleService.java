package com.zzj.recruitment.service;

import com.zzj.recruitment.pojo.Role;

import java.util.List;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-21 16:51:49
 */
public interface IRoleService {
    List<Role> getAllRolesByUserId(Integer userId);

    Role gethighestRole(Integer userId);
}
