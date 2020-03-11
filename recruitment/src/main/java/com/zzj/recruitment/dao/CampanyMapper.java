package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.Campany;

public interface CampanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Campany record);

    int insertSelective(Campany record);

    Campany selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Campany record);

    int updateByPrimaryKey(Campany record);
}