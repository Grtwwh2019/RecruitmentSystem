package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.WorkSkilltag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkSkilltagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorkSkilltag record);

    int insertSelective(WorkSkilltag record);

    WorkSkilltag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorkSkilltag record);

    int updateByPrimaryKey(WorkSkilltag record);

    Integer deleteByWid(Integer wid);

    Integer batchInsert(@Param("skillTags") List<Integer> skillTags, @Param("wid") Integer wid);
}