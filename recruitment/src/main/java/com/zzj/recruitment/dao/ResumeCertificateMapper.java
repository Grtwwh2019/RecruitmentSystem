package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.ResumeCertificate;

public interface ResumeCertificateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResumeCertificate record);

    int insertSelective(ResumeCertificate record);

    ResumeCertificate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResumeCertificate record);

    int updateByPrimaryKey(ResumeCertificate record);
}