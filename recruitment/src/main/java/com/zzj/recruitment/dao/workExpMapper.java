package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.workExp;

public interface workExpMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(workExp record);

    int insertSelective(workExp record);

    workExp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(workExp record);

    int updateByPrimaryKey(workExp record);
}