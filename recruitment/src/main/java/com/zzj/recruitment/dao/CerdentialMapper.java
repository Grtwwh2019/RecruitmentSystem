package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.Cerdential;

public interface CerdentialMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cerdential record);

    int insertSelective(Cerdential record);

    Cerdential selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cerdential record);

    int updateByPrimaryKey(Cerdential record);
}