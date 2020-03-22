package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.RightRole;

public interface RightRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RightRole record);

    int insertSelective(RightRole record);

    RightRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RightRole record);

    int updateByPrimaryKey(RightRole record);
}