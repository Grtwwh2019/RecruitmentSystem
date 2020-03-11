package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.EducationExp;

public interface EducationExpMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EducationExp record);

    int insertSelective(EducationExp record);

    EducationExp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EducationExp record);

    int updateByPrimaryKey(EducationExp record);
}