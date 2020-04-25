package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.workExp;
import org.apache.ibatis.annotations.Param;

public interface workExpMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(workExp record);

    int insertSelective(workExp record);

    workExp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(workExp record);

    int updateByPrimaryKey(workExp record);

    Integer selectCountByWidRid(@Param("workExpId") Integer workExpId, @Param("resumeId") Integer resumeId);
}