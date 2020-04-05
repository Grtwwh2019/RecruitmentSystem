package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.UserRole;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    Integer insertNewRole(@Param("entUserId") Integer entUserId, @Param("role") int role);

    Integer deleteByUidRid(@Param("entUserId")Integer entUserId, @Param("role")int role);
}