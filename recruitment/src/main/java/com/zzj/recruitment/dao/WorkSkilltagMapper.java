package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.WorkSkilltag;

public interface WorkSkilltagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorkSkilltag record);

    int insertSelective(WorkSkilltag record);

    WorkSkilltag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorkSkilltag record);

    int updateByPrimaryKey(WorkSkilltag record);
}