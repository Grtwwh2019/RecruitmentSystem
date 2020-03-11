package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.Employment;

public interface EmploymentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employment record);

    int insertSelective(Employment record);

    Employment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employment record);

    int updateByPrimaryKey(Employment record);
}