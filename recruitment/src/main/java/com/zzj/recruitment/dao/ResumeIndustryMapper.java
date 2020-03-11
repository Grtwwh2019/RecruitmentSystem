package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.ResumeIndustry;

public interface ResumeIndustryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResumeIndustry record);

    int insertSelective(ResumeIndustry record);

    ResumeIndustry selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResumeIndustry record);

    int updateByPrimaryKey(ResumeIndustry record);
}