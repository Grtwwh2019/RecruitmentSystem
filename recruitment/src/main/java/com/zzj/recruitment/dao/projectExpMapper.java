package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.projectExp;
import org.apache.ibatis.annotations.Param;

public interface projectExpMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(projectExp record);

    int insertSelective(projectExp record);

    projectExp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(projectExp record);

    int updateByPrimaryKey(projectExp record);

    Integer selectCountByPidRid(@Param("projectExpId") Integer projectExpId, @Param("resumeId") Integer resumeId);
}