package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.Role;
import com.zzj.recruitment.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkRepetition(@Param("value") String value, @Param("type") String type);

    User selectByUsername(String username);

    List<Role> getUserRolesById(Integer id);

    int resetPasswordByUsername(@Param("username") String username, @Param("passwordNew") String passwordNew);
}