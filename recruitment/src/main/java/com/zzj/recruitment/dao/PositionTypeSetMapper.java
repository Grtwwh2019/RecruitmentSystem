package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.PositionTypeSet;

public interface PositionTypeSetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PositionTypeSet record);

    int insertSelective(PositionTypeSet record);

    PositionTypeSet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PositionTypeSet record);

    int updateByPrimaryKey(PositionTypeSet record);
}