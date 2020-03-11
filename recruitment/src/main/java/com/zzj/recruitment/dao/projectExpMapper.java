package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.projectExp;

public interface projectExpMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(projectExp record);

    int insertSelective(projectExp record);

    projectExp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(projectExp record);

    int updateByPrimaryKey(projectExp record);
}