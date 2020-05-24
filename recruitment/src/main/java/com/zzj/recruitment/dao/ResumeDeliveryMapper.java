package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.ResumeDelivery;
import com.zzj.recruitment.vo.PosisitonListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResumeDeliveryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResumeDelivery record);

    int insertSelective(ResumeDelivery record);

    ResumeDelivery selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResumeDelivery record);

    int updateByPrimaryKey(ResumeDelivery record);

    Integer selectDeliveredByResumeIdEmpId(@Param("resumeId") Integer resumeId, @Param("employmentId") Integer employmentId);

    List<PosisitonListVo> selectResumeDeliverListByResumeId(Integer resumeId);

    int deleteByEmploymentId(Integer eid);
}