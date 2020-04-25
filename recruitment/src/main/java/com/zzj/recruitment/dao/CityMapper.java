package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.City;
import com.zzj.recruitment.vo.IndustryOrPositionSetListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CityMapper {
    int deleteByPrimaryKey(String id);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);

    List<IndustryOrPositionSetListVo> getAllCity();

}