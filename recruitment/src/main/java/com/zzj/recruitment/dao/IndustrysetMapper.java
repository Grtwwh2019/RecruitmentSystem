package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.Industryset;

public interface IndustrysetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Industryset record);

    int insertSelective(Industryset record);

    Industryset selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Industryset record);

    int updateByPrimaryKey(Industryset record);
}