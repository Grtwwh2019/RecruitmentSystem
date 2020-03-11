package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.Skillset;

public interface SkillsetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Skillset record);

    int insertSelective(Skillset record);

    Skillset selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Skillset record);

    int updateByPrimaryKey(Skillset record);
}