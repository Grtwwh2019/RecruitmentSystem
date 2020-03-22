package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.ResumeCertificate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResumeCertificateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResumeCertificate record);

    int insertSelective(ResumeCertificate record);

    ResumeCertificate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResumeCertificate record);

    int updateByPrimaryKey(ResumeCertificate record);

    Integer deleteByResumeId(Integer resumeId);

    Integer batchInsertByRidCid(@Param("resumeId") Integer resumeId, @Param("list") List<Integer> certificationIdList);

}